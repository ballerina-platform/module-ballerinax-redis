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

import ballerina/io;
import ballerina/test;
import ballerina/java;

@test:Config {
}
function testHDel() {
    var result = conn->hDel("testHDelKey", ["testHDelField1", "testHDelField2", "testHDelField3"]);
    if (result is int) {
        test:assertEquals(result, 3);
        test:assertTrue(!hexists(java:fromString("testHDelKey"), java:fromString("testHDelField1")) &&
        !hexists(java:fromString("testHDelKey"), java:fromString("testHDelField2")) &&
        !hexists(java:fromString("testHDelKey"), java:fromString("testHDelField3")));
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHExists() {
    var result = conn->hExists("testHExistsKey", "testHExistsField1");
    if (result is boolean) {
        test:assertEquals(result, true);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHGet() {
    var result = conn->hGet("testHGetKey", "testHGetField1");
    if (result is string) {
        test:assertEquals(result, "testHGetValue1");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHGetAll() {
    var result = conn->hGetAll("testHGetAllKey");
    if (result is map<any>) {
        test:assertEquals(result.length(), 2);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHIncrByFloat() {
    var result = conn->hIncrByFloat("testHIncrByFloatKey", "testHIncrByFloatField1", 0.2);
    if (result is float) {
        test:assertEquals(result, 7.2f);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHIncrBy() {
    var result = conn->hIncrBy("testHIncrByKey", "testHIncrByField1", 2);
    if (result is int) {
        test:assertEquals(result, 8);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHLen() {
    var result = conn->hLen("testHLenKey");
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHMGet() {
    var result = conn->hMGet("testHMGetKey", ["testHMGetField1", "testHMGetField2", "testHMGetField3"]);
    if (result is map<any>) {
        test:assertEquals(result.length(), 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHMSet() {
    map<any> fieldValueMap = {testHMSetField1: "testHMSetValue1", testHMSetField2: "testHMSetValue2"};
    var result = conn->hMSet("testHMSetKey", fieldValueMap);
    if (result is string) {
        test:assertEquals(result, "OK");
        test:assertTrue(hexists(java:fromString("testHMSetKey"), java:fromString("testHMSetField1")) &&
        hget(java:fromString("testHMSetKey"), java:fromString("testHMSetField1")).toString() == "testHMSetValue1" &&
        hexists(java:fromString("testHMSetKey"), java:fromString("testHMSetField2")) &&
        hget(java:fromString("testHMSetKey"), java:fromString("testHMSetField2")).toString() == "testHMSetValue2");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHKeys() {
    var result = conn->hKeys("testHKeysKey");
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHSet() {
    var result = conn->hSet("testHSetKey", "testHSetField1", "testHSetValue1");
    if (result is boolean) {
        test:assertTrue(result);
        test:assertTrue(hexists(java:fromString("testHSetKey"), java:fromString("testHSetField1")) &&
        hget(java:fromString("testHSetKey"), java:fromString("testHSetField1")).toString() == "testHSetValue1");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHSetNx() {
    var result = conn->hSet("testHSetNxKey", "testHSetNxField1", "testHSetNxValue1");
    if (result is boolean) {
        test:assertTrue(result);
        test:assertTrue(hexists(java:fromString("testHSetNxKey"), java:fromString("testHSetNxField1")) &&
        hget(java:fromString("testHSetNxKey"), java:fromString("testHSetNxField1")).toString() == "testHSetNxValue1");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHStrln() {
    var result = conn->hStrln("testHStrlnKey", "testHStrlnField1");
    if (result is int) {
        test:assertEquals(result, 16);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

@test:Config {
}
function testHVals() {
    var result = conn->hVals("testHValsKey");
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

function setupRedisHashDatabase() = @java:Method {
    name: "setupHashDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;
