// Copyright (c) 2024 WSO2 LLC. (http://www.wso2.org)
//
// WSO2 LLC. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/http;
import ballerina/lang.runtime;
import ballerina/test;

// Helper to create a toxiproxy proxy, returns the http client for further API calls.
// Deletes any existing proxy with the same name first to avoid 409 conflicts.
function createToxiProxy(string proxyName, string listenAddr, string upstream)
        returns http:Client|error {
    http:Client toxiproxyClient = check new ("http://localhost:8474");
    // Clean up any stale proxy from a previous run
    http:Response|error existingRes = toxiproxyClient->delete(string `/proxies/${proxyName}`);
    json proxyPayload = {
        "name": proxyName,
        "listen": listenAddr,
        "upstream": upstream
    };
    http:Response createRes = check toxiproxyClient->post("/proxies", proxyPayload);
    if createRes.statusCode != 201 && createRes.statusCode != 200 {
        return error(string `Failed to create toxiproxy proxy '${proxyName}': ${createRes.statusCode}`);
    }
    return toxiproxyClient;
}

// Helper to add a "timeout" toxic that blackholes all traffic (simulates silent connection drop).
function addBlackholdToxic(http:Client toxiproxyClient, string proxyName, string toxicName) returns error? {
    json toxicPayload = {
        "name": toxicName,
        "type": "timeout",
        "attributes": {
            "timeout": 0
        },
        "toxicity": 1.0
    };
    http:Response res = check toxiproxyClient->post(string `/proxies/${proxyName}/toxics`, toxicPayload);
    if res.statusCode != 200 {
        return error(string `Failed to add toxic '${toxicName}': ${res.statusCode}`);
    }
}

// Helper to remove a toxic from a proxy.
function removeToxic(http:Client toxiproxyClient, string proxyName, string toxicName) returns error? {
    http:Response res = check toxiproxyClient->delete(string `/proxies/${proxyName}/toxics/${toxicName}`);
    if res.statusCode != 204 && res.statusCode != 200 {
        return error(string `Failed to remove toxic '${toxicName}': ${res.statusCode}`);
    }
}

// Helper to clean up a toxiproxy proxy (best-effort, ignores errors)
function cleanupToxiProxy(http:Client toxiproxyClient, string proxyName) {
    http:Response|error deleteRes = toxiproxyClient->delete(string `/proxies/${proxyName}`);
}

// Reproduces the stale connection issue WITHOUT the fix (keepAliveInterval=0).
// Uses a toxiproxy "timeout" toxic to blackhole traffic, simulating a silent connection drop
// (no RST sent). Without keep-alive probes and TimeoutOptions, the client cannot detect
// the dead connection and commands should fail with a timeout error.
@test:Config {
    groups: ["standalone"]
}
function testStaleConnectionFailsWithoutKeepAlive() returns error? {
    string proxyName = "redis-proxy-no-keepalive";
    string toxicName = "blackhole";
    http:Client toxiproxyClient = check createToxiProxy(proxyName, "0.0.0.0:6381", "redis-standalone:6379");

    // Connect with keep-alive DISABLED and a short command timeout.
    // The command should timeout after 3 seconds on a stale connection.
    Client redisClient = check new (connection = {
        host: "localhost",
        port: 6381,
        options: {
            connectionTimeout: 3,
            keepAliveInterval: 0
        }
    });

    // Verify connection works before the disruption
    _ = check redisClient->set("stale_conn_test_key", "value1");
    string? val1 = check redisClient->get("stale_conn_test_key");
    test:assertEquals(val1, "value1");

    // Add a blackhole toxic — silently drops all traffic without sending RST.
    // The client still thinks the TCP connection is alive.
    check addBlackholdToxic(toxiproxyClient, proxyName, toxicName);

    // Wait for existing in-flight data to drain
    runtime:sleep(2);

    // Without keep-alive, the client has no way to detect the silently dead connection.
    // The command should fail with a timeout error.
    string?|Error val2 = redisClient->get("stale_conn_test_key");
    test:assertTrue(val2 is Error, "Expected an error due to stale connection without keep-alive");

    // Cleanup
    http:Response|error removeToxicRes = toxiproxyClient->delete(string `/proxies/${proxyName}/toxics/${toxicName}`);
    Error? closeErr = redisClient.close();
    cleanupToxiProxy(toxiproxyClient, proxyName);
}

// Verifies the fix: with keepAliveInterval=5, TCP keep-alive probes
// detect dead connections and TimeoutOptions ensures commands timeout properly,
// allowing Lettuce's auto-reconnect to establish a new connection.
// Uses a toxiproxy "timeout" toxic to simulate a silent drop, then removes it to allow
// recovery. The GET command after recovery should succeed.
@test:Config {
    groups: ["standalone"],
    dependsOn: [testStaleConnectionFailsWithoutKeepAlive]
}
function testConnectionRecoveryAfterSilentDrop() returns error? {
    string proxyName = "redis-proxy-with-keepalive";
    string toxicName = "blackhole";
    http:Client toxiproxyClient = check createToxiProxy(proxyName, "0.0.0.0:6381", "redis-standalone:6379");

    // Connect with keep-alive ENABLED (keepAliveInterval=5)
    Client redisClient = check new (connection = {
        host: "localhost",
        port: 6381,
        options: {
            connectionTimeout: 10,
            keepAliveInterval: 5
        }
    });

    // Verify connection works before the disruption
    _ = check redisClient->set("resilience_test_key", "value1");
    string? val1 = check redisClient->get("resilience_test_key");
    test:assertEquals(val1, "value1");

    // Add a blackhole toxic — silently drops all traffic without sending RST.
    check addBlackholdToxic(toxiproxyClient, proxyName, toxicName);

    // Wait long enough for keep-alive probes to detect the dead connection.
    // With idle=5s, interval=5s, count=3: detection takes ~20s.
    runtime:sleep(25);

    // Remove the toxic to restore connectivity — new connections can now be established.
    check removeToxic(toxiproxyClient, proxyName, toxicName);

    // Wait for Lettuce auto-reconnect to establish a new connection
    runtime:sleep(5);

    // With keep-alive + TimeoutOptions enabled, the client detected the dead connection
    // and auto-reconnected. This GET should succeed.
    string? val2 = check redisClient->get("resilience_test_key");
    test:assertEquals(val2, "value1");

    // Cleanup
    check redisClient.close();
    cleanupToxiProxy(toxiproxyClient, proxyName);
}
