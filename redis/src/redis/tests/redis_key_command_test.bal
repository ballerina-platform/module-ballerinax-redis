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
function testDel() {
   var result = conn->del(["testDelKey1", "testDelKey2", "testDelKey3"]);
   test:assertEquals(result, 3);
}

@test:Config {
}
function testExists() {
    var result = conn->exists(["testExistsKey"]);
    var result2 = conn->exists(["nonExistentKey"]);
    test:assertEquals(result, 1);
    test:assertEquals(result2, 0);
}

@test:Config {
}
function testExpire() {
    var result = conn->expire("testExpireKey", 3);
    test:assertTrue(result);
}

@test:Config {
}
function testKeys() {
    var result = conn->keys("testKeysKey*");
    test:assertEquals(result.length(), 3);
}

@test:Config {
}
function testMove() {
    var result = conn->move("testMoveKey", 1);
    test:assertTrue(result);
}

@test:Config {
}
function testPersist() {
    var result = conn->persist("testPersistKey");
    test:assertFalse(result);
}

@test:Config {
}
function testPExpire() {
    var result = conn->pExpire("testPExpireKey", 3000);
    test:assertTrue(result);
}

@test:Config {
}
function testPTtl() {
    var result = conn->pTtl("testPTtlKey");
}

@test:Config {
}
function testRandomKey() {
    var result = conn->randomKey();
}

@test:Config {
}
function testRename() {
    var result = conn->rename("testRenameKey", "testRenameKey1");
    test:assertEquals(result, "OK");
}

@test:Config {
}
function testRenameNx() {
    var result = conn->renameNx("testRenameNxKey", "testRenameNxKeyRenamed");
    var result2 = conn->renameNx("testRenameNxKey1", "testRenameNxKeyExisting");
    test:assertTrue(result);
    test:assertFalse(result2);
}

@test:Config {
}
function testSort() {
    var result = conn->sort("testSortKey");
    test:assertEquals(result.length(), 6);
    test:assertEquals(result[0], "0");
    test:assertEquals(result[1], "1");
    test:assertEquals(result[2], "2");
    test:assertEquals(result[3], "3");
    test:assertEquals(result[4], "4");
    test:assertEquals(result[5], "8");
}

@test:Config {
}
function testTtl() {
    var result = conn->pTtl("testTtlKey");
}

@test:Config {
}
function testType() {
    var result = conn->redisType("testTypeKey");
    test:assertEquals(result, "string");
}






function setupRedisKeyDatabase() = @java:Method{
    name: "setupKeyDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;