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

@test:Config {
}
function testHDel() {
    var result = conn->hDel("testHDelKey", ["testHDelField1", "testHDelField2", "testHDelField3"]);
    test:assertEquals(result, 3);
}

@test:Config {
}
function testHExists() {
    var result = conn->hExists("testHExistsKey", "testHExistsField1");
    test:assertEquals(result, true);
}

@test:Config {
}
function testHGet() {
    var result = conn->hGet("testHGetKey", "testHGetField1");
    test:assertEquals(result, "testHGetValue1");
}

// @test:Config {
// }
// function testHGetAll() {
//     var result = conn->hGetAll("testHGetAllKey");
//     test:assertEquals(result.length(), 3);
// }

@test:Config {
}
function testHIncrByFloat() {
    var result = conn->hIncrByFloat("testHIncrByFloatKey", "testHIncrByFloatField1", 0.2);
    test:assertEquals(result, 7.2f);
}

@test:Config {
}
function testHIncrBy() {
    var result = conn->hIncrBy("testHIncrByKey", "testHIncrByField1", 2);
    test:assertEquals(result, 8);
}

@test:Config {
}
function testHLen() {
    var result = conn->hLen("testHLenKey");
    test:assertEquals(result, 3);
}

@test:Config {
}
function testHKeys() {
    var result = conn->hKeys("testHKeysKey");
    test:assertEquals(result.length(), 3);
}

@test:Config {
}
function testHSet() {
    var result = conn->hSet("testHSetKey", "testHSetField1", "testHSetValue1");
    test:assertTrue(result);
}

function setupRedisHashDatabase() = @java:Method{
    name: "setupHashDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;
