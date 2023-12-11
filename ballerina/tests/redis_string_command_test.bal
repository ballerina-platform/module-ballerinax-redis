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

import ballerina/lang.runtime;
import ballerina/test;

@test:Config {}
public function testSet() {
    var result = redis->set("Name", "Manuri");
    if (result is string) {
        test:assertEquals(result, "OK");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testAppend() {
    var result = redis->append("AppendTestKey", "SomeValue");
    if (result is int) {
        test:assertEquals(result, 24);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testBitCount() {
    var result = redis->bitCount("BitCountTestKey");
    if (result is int) {
        test:assertEquals(result, 69);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testBitOpAnd() {
    do {
        string[] keys = ["testBitOpKey1", "testBitOpKey2"];
        int result = check redis->bitOpAnd("tesBitOpAndDest", keys);
        test:assertEquals(result, 3);

        string? getResult = check redis->get("tesBitOpAndDest");
        test:assertEquals(getResult, "100");
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
public function tesBitOpOr() {
    do {
        string[] keys = ["testBitOpKey1", "testBitOpKey2"];
        int result = check redis->bitOpOr("tesBitOpOrDest", keys);
        test:assertEquals(result, 3);

        string? getResult = check redis->get("tesBitOpOrDest");
        test:assertEquals(getResult, "101");
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
public function tesBitOpNot() {
    var result = redis->bitOpNot("tesBitOpNotDest", "testBitOpKey1");
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function tesBitOpXor() {
    var result = redis->bitOpXor("tesBitOpXorDest", ["testBitOpKey1", "testBitOpKey2"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testDecr() {
    var result = redis->decr("testDecrKey");
    if (result is int) {
        test:assertEquals(result, 7);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testDecrBy() {
    var result = redis->decrBy("testDecrByKey", 3);
    if (result is int) {
        test:assertEquals(result, 7);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testGet() {
    var result = redis->get("GetTestKey");
    if (result is string) {
        test:assertEquals(result, "GetTestValue");
    } else if (result is ()) {
        test:assertFail("Key not found");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testGetBit() {
    var result = redis->getBit("testGetBitKey", 4);
    if (result is int) {
        test:assertEquals(result, 0);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testGetRange() {
    var result = redis->getRange("testGetRangekey", 2, 5);
    if (result is string) {
        test:assertEquals(result, "3456");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testGetSet() {
    do {
        string? result = check redis->getSet("testGetSetKey", "testGetSetNewValue");
        test:assertEquals(result, "testGetSetValue");

        string? getResult = check redis->get("testGetSetKey");
        test:assertEquals(getResult, "testGetSetNewValue");
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
public function testIncr() {
    var result = redis->incr("testIncrKey");
    if (result is int) {
        test:assertEquals(result, 7);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testIncrBy() {
    var result = redis->incrBy("testIncrByKey", 2);
    if (result is int) {
        test:assertEquals(result, 7);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    dependsOn: [testIncrBy]
}
public function testIncrByFloat() {
    var result = redis->incrByFloat("testIncrByKey", 0.2);
    if (result is float) {
        test:assertEquals(result, 7.200000002980232);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testMGet() {
    do {
        string[] result = check redis->mGet(["testMGetKey1", "testMGetKey2"]);
        test:assertEquals(result.length(), 2);

        string? getResult1 = check redis->get("testMGetKey1");
        test:assertEquals(getResult1, "testMGetValue1");
        string? getResult2 = check redis->get("testMGetKey2");
        test:assertEquals(getResult2, "testMGetValue2");
        string? getResult3 = check redis->get("testMGetKey3");
        test:assertEquals(getResult3, ());
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
function testMSet() {
    map<any> keyValueMap = {
        testMSetKey1: "testMSetValue1",
        testMSetKey2: "testMSetValue2",
        testMSetKey3: "testMSetValue3"
    };
    do {
        string result = check redis->mSet(keyValueMap);
        test:assertEquals(result, "OK");

        string? getResult1 = check redis->get("testMSetKey1");
        test:assertEquals(getResult1, "testMSetValue1");
        string? getResult2 = check redis->get("testMSetKey2");
        test:assertEquals(getResult2, "testMSetValue2");
        string? getResult3 = check redis->get("testMSetKey3");
        test:assertEquals(getResult3, "testMSetValue3");
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
function testMSetNx() {
    map<any> keyValueMap = {
        testMSetNxKey1: "testMSetNxNewValue1",
        testMSetNxKey2: "testMSetNxValue2",
        testMSetNxKey3: "testMSetNxValue3"
    };

    do {
        boolean result = check redis->mSetNx(keyValueMap);
        test:assertFalse(result);

        string? getResult1 = check redis->get("testMSetNxKey1");
        test:assertEquals(getResult1, "testMSetNxValue1");
        string? getResult2 = check redis->get("testMSetNxKey2");
        test:assertEquals(getResult2, ());
        string? getResult3 = check redis->get("testMSetNxKey3");
        test:assertEquals(getResult3, ());
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}

public function testPSetEx() {
    do {
        string result = check redis->pSetEx("testPSetExKey", "testPSetExNewValue", 5000);
        test:assertEquals(result, "OK");

        string? getResult = check redis->get("testPSetExKey");
        test:assertEquals(getResult, "testPSetExNewValue");
        runtime:sleep(6);
        string? getResult2 = check redis->get("testPSetExKey");
        test:assertEquals(getResult2, ());
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
public function testSetBit() {
    var result = redis->setBit("testSetBitKey", 1, 2);
    if (result is int) {
        test:assertEquals(result, 1);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
public function testSetEx() {
    do {
        string result = check redis->setEx("testSetExKey", "testSetExNewValue", 5);
        test:assertEquals(result, "OK");

        string? getResult = check redis->get("testSetExKey");
        test:assertEquals(getResult, "testSetExNewValue");
        runtime:sleep(6);
        string? getResult2 = check redis->get("testSetExKey");
        test:assertEquals(getResult2, ());
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
public function testSetNx() {
    do {
        boolean result = check redis->setNx("testSetNxKey", "testSetNxValue");
        test:assertEquals(result, true);

        string? getResult = check redis->get("testSetNxKey");
        test:assertEquals(getResult, "testSetNxValue");
    } on fail error e {
        test:assertFail("error from Connector: " + e.message());
    }
}

@test:Config {}
public function testSetRange() {
    do {
        int result = check redis->setRange("testSetRangeKey", 2, "!!!");
        test:assertEquals(result, 17);

        string? getResult = check redis->get("testSetRangeKey");
        test:assertEquals(getResult, "te!!!etRangeValue");
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
public function testStrln() {
    var result = redis->strln("testStrlnKey");
    if (result is int) {
        test:assertEquals(result, 14);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}
