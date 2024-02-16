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

@test:Config {}
function testInvalidRedisUrl() {
    Client|Error initResult = new (connection = {uri: "invalidscheme://localhost:6379"});
    test:assertTrue(initResult is Error);
    if (initResult is Error) {
        test:assertEquals(initResult.message(), "Error while initializing the redis client: Scheme invalidscheme not supported");
    }
}

@test:Config {}
function testInvalidHost() {
    Client|Error initResult = new (connection = {uri: "redis://invalidhost:6379"});
    test:assertTrue(initResult is Error);
    if (initResult is Error) {
        test:assertEquals(initResult.message(), "Error while initializing the redis client: Unable to connect to invalidhost/<unresolved>:6379");
    }
}

@test:Config {}
function testInvalidPort() {
    Client|Error initResult = new (connection = {uri: "redis://localhost:6380"});
    test:assertTrue(initResult is Error);
    if (initResult is Error) {
        test:assertEquals(initResult.message(), "Error while initializing the redis client: Unable to connect to localhost/<unresolved>:6380'");
    }
}
