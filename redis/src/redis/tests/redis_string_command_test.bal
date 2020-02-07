// Copyright (c) 2020 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
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
import ballerinax/java;

ClientEndpointConfiguration redisConfig = {
        host: "localhost",
        password: "",
        options: { connectionPooling: true, isClusterConnection: false, ssl: false,
            startTls: false, verifyPeer: false, connectionTimeout: 500 }
};

var result = initRedisServer();
Client redisClient =  check new (redisConfig);

@test:Config {
}
public function testSet() {
    var result1 = redisClient->set("mykey", "hello");
    var result2 = redisClient->set("Project", "Ballerina");
}

@test:Config {
    dependsOn: ["testSet"]
}
public function testGet() {
    var result = redisClient->get("mykey");
    test:assertEquals(result, "hello");
}

@test:Config {
    dependsOn: ["testSet"]
}
public function testAppend() {
    var appendedStringLength = redisClient->append("Project", "-Redis-Module");
    var appendedString = redisClient->get("Project");
    test:assertEquals(appendedString, "Ballerina-Redis-Module");
}

@test:Config {
    dependsOn: ["testSet"]
}
public function testBitCount() {
    var bitCount = redisClient->bitCount("Project");
    test:assertEquals(bitCount, 33);
}

function initRedisServer() returns error? = @java:Method{
    name: "initServer",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;