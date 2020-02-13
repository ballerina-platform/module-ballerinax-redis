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
import ballerina/io;

@test:Config {
}
function testBLPop() {
    var result = conn->bLPop(1, ["testBLPopKey"]);
    if (result is map<any>) {
        // test:assertEquals(result.get("testBLPopKey"), "testBLPopValue2");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testBRPop() {
    var result = conn->bRPop(1, ["testBRPopKey"]);
    if (result is map<any>) {
        // test:assertEquals(result.get("testBLPopKey"), "testBLPopValue2");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLPush() {
    var result = conn->lPush("testLPushKey", ["testLPushValue2", "testLPushValue3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLPop() {
    var result = conn->lPop("testLPopKey");
    if (result is string) {
        test:assertEquals(result, "testLPopValue2");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLPushX() {
    var result = conn->lPushX("testLPushXKey", ["testLPushXValue2", "testLPushXValue3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLIndex() {
    var result = conn->lIndex("testLIndexKey", 0);
    if (result is string) {
        test:assertEquals(result, "testLIndexValue2");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLInsert() {
    var result = conn->lInsert("testLInsertKey", true, "pivotValue", "beforePivotValue");
    if (result is int) {
        test:assertEquals(result, 5);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLLen() {
    var result = conn->lLen("testLLenKey");
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLRange() {
    var result = conn->lRange("testLRangeKey", 1, 3);
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLRem() {
    var result = conn->lRem("testLRemKey", 0, "toBeRemovedValue");
    if (result is int) {
        test:assertEquals(result, 2);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLSet() {
    var result = conn->lSet("testLSetKey", 1, "testLSetValue2New");
    if (result is string) {
        test:assertEquals(result, "OK");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testLTrim() {
    var result = conn->lTrim("testLTrimKey", 1, -1);
    if (result is string) {
        test:assertEquals(result, "OK");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testRPop() {
    var result = conn->rPop("testRPopKey");
    if (result is string) {
        test:assertEquals(result, "testRPopValue1");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testRPopLPush() {
    var result = conn->rPopLPush("testRPopLPushKey1", "testRPopLPushKey2");
    if (result is string) {
        test:assertEquals(result, "One");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testRPush() {
    var result = conn->rPush("testRPushKey", ["testRPushValue2", "testRPushValue3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testRPushX() {
    var result = conn->rPushX("testRPushXKey", ["testRPushXValue2", "testRPushXValue3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

function setupRedisListDatabase() = @java:Method{
    name: "setupListDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;
