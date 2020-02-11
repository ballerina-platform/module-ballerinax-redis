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
function testLPop() {
    var result = conn->lPop("testLPopKey");
}

@test:Config {
}
function testLPushX() {
    var result = conn->lPushX("testLPushXKey", ["testLPushXValue2", "testLPushXValue3"]);
    test:assertEquals(result, 3);
}

@test:Config {
}
function testLIndex() {
    var result = conn->lIndex("testLIndexKey", 0);
    test:assertEquals(result, "testLIndexValue2");
}

@test:Config {
}
function testLInsert() {
    var result = conn->lInsert("testLInsertKey", true, "pivotValue", "beforePivotValue");
    test:assertEquals(result, 5);
}

@test:Config {
}
function testLLen() {
    var result = conn->lLen("testLLenKey");
    test:assertEquals(result, 3);
}

@test:Config {
}
function testLRem() {
    var result = conn->lRem("testLRemKey", 0, "toBeRemovedValue");
    test:assertEquals(result, 2);
}

@test:Config {
}
function testLSet() {
    var result = conn->lSet("testLSetKey", 1, "testLSetValue2New");
    test:assertEquals(result, "OK");
}

@test:Config {
}
function testLTrim() {
    var result = conn->lTrim("testLTrimKey", 1, -1);
    test:assertEquals(result, "OK");
}

@test:Config {
}
function testRPop() {
    var result = conn->rPop("testRPopKey");
    test:assertEquals(result, "testRPopValue1");
}

@test:Config {
}
function testRPopLPush() {
    var result = conn->rPopLPush("testRPopLPushKey1", "testRPopLPushKey2");
    test:assertEquals(result, "One");
}

@test:Config {
}
function testRPush() {
    var result = conn->rPush("testRPushKey", ["testRPushValue2", "testRPushValue3"]);
    test:assertEquals(result, 3);
}

@test:Config {
}
function testRPushX() {
    var result = conn->rPushX("testRPushXKey", ["testRPushXValue2", "testRPushXValue3"]);
    test:assertEquals(result, 3);
}

function setupRedisListDatabase() = @java:Method{
    name: "setupListDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;
