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
import ballerina/test;

@test:Config {
    groups: ["standalone", "cluster"]
}
function testDefaultConfigs() returns error? {
    Client redis = check new (); // Empty configs will use default Redis server configs ("localhost:6379") to connect
    string ping = check redis->ping();
    test:assertEquals(ping, "PONG");

    check redis.close();
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testInvalidSchemaInRedisUri() {
    Client|Error initResult = new (connection = "invalidscheme://localhost:6379");
    test:assertTrue(initResult is Error);
    if initResult is Error {
        test:assertEquals(initResult.message(), "Error while initializing the redis client: Scheme invalidscheme not supported");
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testInvalidHostInRedisUriWithPooling() {
    Client|Error initResult = new (connection = "redis://invalidhost:6379", connectionPooling = true);
    test:assertTrue(initResult is Error);
    if initResult is Error {
        test:assertEquals(initResult.message(), "Error while initializing the redis client: Unable to connect to invalidhost/<unresolved>:6379");
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testInvalidPortInRedisUriOnClusterMode() {
    Client|Error initResult = new (connection = "redis://localhost:1", connectionPooling = true, isClusterConnection = true);
    test:assertTrue(initResult is Error);
    if initResult is Error {
        test:assertEquals(initResult.message(), "Error while initializing the redis client: Unable to establish a connection to Redis Cluster");
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testConcurrentOperationsBeyondPoolSize() returns error? {
    // More concurrent callers than the default pool size (8), so some must wait for a connection to be returned
    Client pooledClient = check new (clusterMode ? getClusterConfigs() : getStandaloneConfigs());
    string[] keys = [];
    future<string|error>[] results = [];
    foreach int i in 0 ..< 20 {
        string key = string `{pool-test}:key-${i}`;
        keys.push(key);
        future<string|error> result = start setAndGet(pooledClient, key, i.toString());
        results.push(result);
    }
    foreach int i in 0 ..< results.length() {
        string value = check wait results[i];
        test:assertEquals(value, i.toString());
    }
    _ = check pooledClient->del(keys);
    check pooledClient.close();
}

isolated function setAndGet(Client redisClient, string key, string value) returns string|error {
    _ = check redisClient->set(key, value);
    string? stored = check redisClient->get(key);
    return stored ?: "";
}
