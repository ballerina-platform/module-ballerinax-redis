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
    groups: ["standalone"]
}
function testSecureSocketWithoutCert() returns error? {
    if clusterMode {
        return;
    }

    Client rediss = check new (
        connection = {
            host: "localhost",
            port: 6380
        },
        secureSocket = {
            verifyMode: NONE
        }
    );

    string ping = check rediss->ping();
    test:assertEquals(ping, "PONG");

    check rediss.close();
}

@test:Config {
    groups: ["standalone"]
}
function testSecureSocketWithCert() returns error? {
    if clusterMode {
        return;
    }

    Client rediss = check new (
        connection = {
            host: "localhost",
            port: 6380
        },
        secureSocket = {
            cert: "./tests/resources/docker/secrets/server.crt"
        }
    );

    string ping = check rediss->ping();
    test:assertEquals(ping, "PONG");

    check rediss.close();
}

@test:Config {
    groups: ["standalone"]
}
function testSecureSocketWithTrustStore() returns error? {
    if clusterMode {
        return;
    }

    Client rediss = check new (
        connection = {
            host: "localhost",
            port: 6380
        },
        secureSocket = {
            cert: {
                path: "./tests/resources/docker/secrets/clientTrustStore.p12",
                password: "password"
            }
        }
    );

    string ping = check rediss->ping();
    test:assertEquals(ping, "PONG");

    check rediss.close();
}

@test:Config {
    groups: ["standalone"]
}
function testMutualSecureSocketWithTrustStoreKeyStore() returns error? {
    if clusterMode {
        return;
    }

    Client rediss = check new (
        connection = {
            host: "localhost",
            port: 6380
        },
        secureSocket = {
            cert: {
                path: "./tests/resources/docker/secrets/clientTrustStore.p12",
                password: "password"
            },
            key: {
                path: "./tests/resources/docker/secrets/clientKeyStore.p12",
                password: "password"
            }
        }
    );

    string ping = check rediss->ping();
    test:assertEquals(ping, "PONG");

    check rediss.close();
}

@test:Config {
    groups: ["standalone"]
}
function testMutualSecureSocketWithServerCertAndClientCertKey() returns error? {
    if clusterMode {
        return;
    }

    Client rediss = check new (
        connection = {
            host: "localhost",
            port: 6380
        },
        secureSocket = {
            cert: "./tests/resources/docker/secrets/server.crt",
            key: {
                certFile: "./tests/resources/docker/secrets/client.crt",
                keyFile: "./tests/resources/docker/secrets/client.key"
            }
        }
    );

    string ping = check rediss->ping();
    test:assertEquals(ping, "PONG");

    check rediss.close();
}
