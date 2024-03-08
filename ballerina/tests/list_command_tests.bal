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

@test:Config {
    groups: ["standalone", "cluster"]
}
function testBLPop() returns error? {
    map<any> result = check redis->bLPop(1, ["testBLPopKey"]);
    test:assertEquals(<anydata|error>result.get("testBLPopKey"), "testBLPopValue2");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testBRPop() returns error? {
    map<any> result = check redis->bRPop(1, ["testBRPopKey"]);
    test:assertEquals(<anydata|error>result.get("testBRPopKey"), "testBRPopValue1");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLPush() returns error? {
    int result = check redis->lPush("testLPushKey", ["testLPushValue2", "testLPushValue3"]);
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLPop() returns error? {
    string? result = check redis->lPop("testLPopKey");
    if result is string {
        test:assertEquals(result, "testLPopValue2");
    } else {
        test:assertFail("Key not found");
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLPushX() returns error? {
    int result = check redis->lPushX("testLPushXKey", ["testLPushXValue2", "testLPushXValue3"]);
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLIndex() returns error? {
    string? result = check redis->lIndex("testLIndexKey", 0);
    if result is string {
        test:assertEquals(result, "testLIndexValue2");
    } else {
        test:assertFail("Key not found");
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLInsert() returns error? {
    int result = check redis->lInsert("testLInsertKey", true, "pivotValue", "beforePivotValue");
    test:assertEquals(result, 5);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLLen() returns error? {
    int result = check redis->lLen("testLLenKey");
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLRange() returns error? {
    string[] result = check redis->lRange("testLRangeKey", 1, 3);
    test:assertEquals(result.length(), 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLRem() returns error? {
    int result = check redis->lRem("testLRemKey", 0, "toBeRemovedValue");
    test:assertEquals(result, 2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLSet() returns error? {
    string result = check redis->lSet("testLSetKey", 1, "testLSetValue2New");
    test:assertEquals(result, "OK");

    string|() lIndexResult = check redis->lIndex("testLSetKey", 1);
    test:assertEquals(lIndexResult, "testLSetValue2New");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLTrim() returns error? {
    string result = check redis->lTrim("testLTrimKey", 1, -1);
    test:assertEquals(result, "OK");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRPop() returns error? {
    string? result = check redis->rPop("testRPopKey");
    if result is string {
        test:assertEquals(result, "testRPopValue1");
    } else {
        test:assertFail("Key not found");
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRPopLPush() returns error? {
    string result = check redis->rPopLPush("{ListTag}testRPopLPushKey1", "{ListTag}testRPopLPushKey2");
    test:assertEquals(result, "One");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRPush() returns error? {
    int result = check redis->rPush("testRPushKey", ["testRPushValue2", "testRPushValue3"]);
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRPushX() returns error? {
    int result = check redis->rPushX("testRPushXKey", ["testRPushXValue2", "testRPushXValue3"]);
    test:assertEquals(result, 3);
}
