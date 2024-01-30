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
function testBLPop() {
    var result = redis->bLPop(1, ["testBLPopKey"]);
    if (result is map<any>) {
        test:assertEquals(<anydata|error>result.get("testBLPopKey"), "testBLPopValue2");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testBRPop() {
    var result = redis->bRPop(1, ["testBRPopKey"]);
    if (result is map<any>) {
        test:assertEquals(<anydata|error>result.get("testBRPopKey"), "testBRPopValue1");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLPush() {
    var result = redis->lPush("testLPushKey", ["testLPushValue2", "testLPushValue3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLPop() {
    var result = redis->lPop("testLPopKey");
    if (result is string) {
        test:assertEquals(result, "testLPopValue2");
    } else if (result is ()) {
        test:assertFail("Key not found");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLPushX() {
    var result = redis->lPushX("testLPushXKey", ["testLPushXValue2", "testLPushXValue3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLIndex() {
    var result = redis->lIndex("testLIndexKey", 0);
    if (result is string) {
        test:assertEquals(result, "testLIndexValue2");
    } else if (result is ()) {
        test:assertFail("Key not found");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLInsert() {
    var result = redis->lInsert("testLInsertKey", true, "pivotValue", "beforePivotValue");
    if (result is int) {
        test:assertEquals(result, 5);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLLen() {
    var result = redis->lLen("testLLenKey");
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLRange() {
    var result = redis->lRange("testLRangeKey", 1, 3);
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testLRem() {
    var result = redis->lRem("testLRemKey", 0, "toBeRemovedValue");
    if (result is int) {
        test:assertEquals(result, 2);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
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
function testLTrim() {
    var result = redis->lTrim("testLTrimKey", 1, -1);
    if (result is string) {
        test:assertEquals(result, "OK");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRPop() {
    var result = redis->rPop("testRPopKey");
    if (result is string) {
        test:assertEquals(result, "testRPopValue1");
    } else if (result is ()) {
        test:assertFail("Key not found");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRPopLPush() {
    var result = redis->rPopLPush("{ListTag}testRPopLPushKey1", "{ListTag}testRPopLPushKey2");
    if (result is string) {
        test:assertEquals(result, "One");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRPush() {
    var result = redis->rPush("testRPushKey", ["testRPushValue2", "testRPushValue3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRPushX() {
    var result = redis->rPushX("testRPushXKey", ["testRPushXValue2", "testRPushXValue3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}
