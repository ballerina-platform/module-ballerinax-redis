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

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testSet() returns error? {
    string result = check redis->set("Name", "Manuri");
    test:assertEquals(result, "OK");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testAppend() returns error? {
    int result = check redis->append("AppendTestKey", "SomeValue");
    test:assertEquals(result, 24);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testBitCount() returns error? {
    int result = check redis->bitCount("BitCountTestKey");
    test:assertEquals(result, 69);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testBitOpAnd() returns error? {
    string[] keys = ["{StringTag}testBitOpKey1", "{StringTag}testBitOpKey2"];
    int result = check redis->bitOpAnd("{StringTag}tesBitOpAndDest", keys);
    test:assertEquals(result, 3);

    string? getResult = check redis->get("{StringTag}tesBitOpAndDest");
    test:assertEquals(getResult, "100");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testBitOpOr() returns error? {
    string[] keys = ["{StringTag}testBitOpKey1", "{StringTag}testBitOpKey2"];
    int result = check redis->bitOpOr("{StringTag}tesBitOpOrDest", keys);
    test:assertEquals(result, 3);

    string? getResult = check redis->get("{StringTag}tesBitOpOrDest");
    test:assertEquals(getResult, "101");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testBitOpNot() returns error? {
    int result = check redis->bitOpNot("{StringTag}tesBitOpNotDest", "{StringTag}testBitOpKey1");
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testBitOpXor() returns error? {
    int result = check redis->bitOpXor("{StringTag}tesBitOpXorDest", ["{StringTag}testBitOpKey1", "{StringTag}testBitOpKey2"]);
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testDecr() returns error? {
    var result = check redis->decr("testDecrKey");
    test:assertEquals(result, 7);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testDecrBy() returns error? {
    int result = check redis->decrBy("testDecrByKey", 3);
    test:assertEquals(result, 7);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testGet() returns error? {
    string? result = check redis->get("GetTestKey");
    if result is string {
        test:assertEquals(result, "GetTestValue");
    } else {
        test:assertFail("Key not found");
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testGetBit() returns error? {
    int result = check redis->getBit("testGetBitKey", 4);
    test:assertEquals(result, 0);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testGetRange() returns error? {
    string result = check redis->getRange("testGetRangekey", 2, 5);
    test:assertEquals(result, "3456");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testGetSet() returns error? {
    string? result = check redis->getSet("testGetSetKey", "testGetSetNewValue");
    test:assertEquals(result, "testGetSetValue");

    string? getResult = check redis->get("testGetSetKey");
    test:assertEquals(getResult, "testGetSetNewValue");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testIncr() returns error? {
    int result = check redis->incr("testIncrKey");
    test:assertEquals(result, 7);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testIncrBy() returns error? {
    int result = check redis->incrBy("testIncrByKey", 2);
    test:assertEquals(result, 7);
}

@test:Config {
    dependsOn: [testIncrBy]
}
public function testIncrByFloat() returns error? {
    float result = check redis->incrByFloat("testIncrByKey", 0.2);
    test:assertEquals(result, 7.200000002980232);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testMGet() returns error? {
    string[] result = check redis->mGet(["testMGetKey1", "testMGetKey2"]);
    test:assertEquals(result.length(), 2);

    string? getResult1 = check redis->get("testMGetKey1");
    test:assertEquals(getResult1, "testMGetValue1");
    string? getResult2 = check redis->get("testMGetKey2");
    test:assertEquals(getResult2, "testMGetValue2");
    string? getResult3 = check redis->get("testMGetKey3");
    test:assertEquals(getResult3, ());
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testMSet() returns error? {
    map<any> keyValueMap = {
        testMSetKey1: "testMSetValue1",
        testMSetKey2: "testMSetValue2",
        testMSetKey3: "testMSetValue3"
    };

    string result = check redis->mSet(keyValueMap);
    test:assertEquals(result, "OK");

    string? getResult1 = check redis->get("testMSetKey1");
    test:assertEquals(getResult1, "testMSetValue1");
    string? getResult2 = check redis->get("testMSetKey2");
    test:assertEquals(getResult2, "testMSetValue2");
    string? getResult3 = check redis->get("testMSetKey3");
    test:assertEquals(getResult3, "testMSetValue3");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testMSetNx() returns error? {
    map<any> keyValueMap = {
        testMSetNxKey101: "testMSetNxNewValue1",
        testMSetNxKey102: "testMSetNxValue2",
        testMSetNxKey103: "testMSetNxValue3"
    };

    boolean result = check redis->mSetNx(keyValueMap);
    test:assertTrue(result);

    string? getResult1 = check redis->get("testMSetNxKey101");
    test:assertEquals(getResult1, "testMSetNxNewValue1");
    string? getResult2 = check redis->get("testMSetNxKey102");
    test:assertEquals(getResult2, "testMSetNxValue2");
    string? getResult3 = check redis->get("testMSetNxKey103");
    test:assertEquals(getResult3, "testMSetNxValue3");
}

@test:Config {
    groups: ["standalone", "cluster"]
}

public function testPSetEx() returns error? {
    string result = check redis->pSetEx("testPSetExKey", "testPSetExNewValue", 5000);
    test:assertEquals(result, "OK");

    string? getResult = check redis->get("testPSetExKey");
    test:assertEquals(getResult, "testPSetExNewValue");
    runtime:sleep(6);
    string? getResult2 = check redis->get("testPSetExKey");
    test:assertEquals(getResult2, ());
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testSetBit() returns error? {
    int result = check redis->setBit("testSetBitKey", 1, 2);
    test:assertEquals(result, 1);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testSetEx() returns error? {
    string result = check redis->setEx("testSetExKey", "testSetExNewValue", 5);
    test:assertEquals(result, "OK");

    string? getResult = check redis->get("testSetExKey");
    test:assertEquals(getResult, "testSetExNewValue");
    runtime:sleep(6);
    string? getResult2 = check redis->get("testSetExKey");
    test:assertEquals(getResult2, ());
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testSetNx() returns error? {
    boolean result = check redis->setNx("testSetNxKey", "testSetNxValue");
    test:assertEquals(result, true);

    string? getResult = check redis->get("testSetNxKey");
    test:assertEquals(getResult, "testSetNxValue");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testSetRange() returns error? {
    int result = check redis->setRange("testSetRangeKey", 2, "!!!");
    test:assertEquals(result, 17);

    string? getResult = check redis->get("testSetRangeKey");
    test:assertEquals(getResult, "te!!!etRangeValue");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
public function testStrLen() returns error? {
    int result = check redis->strLen("testStrLenKey");
    test:assertEquals(result, 15);
}
