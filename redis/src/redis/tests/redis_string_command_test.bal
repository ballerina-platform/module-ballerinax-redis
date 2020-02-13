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
public function testSet() {
    var result = conn->set("Name", "Manuri");
    test:assertEquals(result, "OK");
}

@test:Config {
}
public function testAppend() {
    var result = conn->append("AppendTestKey", "SomeValue");
    test:assertEquals(result, 24);
}

@test:Config {
}
public function testBitCount() {
    var result = conn->bitCount("BitCountTestKey");
    test:assertEquals(result, 69);
}

@test:Config {
}
public function tesBitOpAnd() {
   string[] keys = ["testBitOpKey1", "testBitOpKey2"];
   var result = conn->bitOpAnd("tesBitOpAndDest", keys);
   test:assertEquals(result, 3);
}

@test:Config {
}
public function tesBitOpOr() {
   string[] keys = ["testBitOpKey1", "testBitOpKey2"];
   var result = conn->bitOpOr("tesBitOpOrDest", keys);
   test:assertEquals(result, 3);
}

@test:Config {
}
public function tesBitOpNot() {
   var result = conn->bitOpNot("tesBitOpNotDest", "testBitOpKey1");
   test:assertEquals(result, 3);
}

@test:Config {
}
function tesBitOpXor() {
   var result = conn->bitOpXor("tesBitOpXorDest", ["testBitOpKey1", "testBitOpKey2"]);
   test:assertEquals(result, 3);
}

@test:Config {
}
public function testDecr() {
    var result = conn->decr("testDecrKey");
    test:assertEquals(result, 7);
}

@test:Config {
}
function testDecrBy() {
    var result = conn->decrBy("testDecrByKey", 3);
    test:assertEquals(result, 7);
}

@test:Config {
}
public function testGet() {
    var result = conn->get("GetTestKey");
    test:assertEquals(result, "GetTestValue");
}

@test:Config {
}
public function testGetBit() {
    var result = conn->getBit("testGetBitKey", 4);
    test:assertEquals(result, 0);
}

@test:Config {
}
public function testGetRange() {
    var result = conn->getRange("testGetRangekey", 2, 5);
    test:assertEquals(result, "3456");
}

@test:Config {
}
public function testGetSet() {
    var result = conn->getSet("testGetSetKey", "testGetSetNewValue");
    test:assertEquals(result, "testGetSetValue");
}

@test:Config {
}
public function testIncr()  {
    var result = conn->incr("testIncrKey");
    test:assertEquals(result, 7);
}

@test:Config {
}
public function testIncrBy() {
    var result = conn->incrBy("testIncrByKey", 2);
    test:assertEquals(result, 7);
}

@test:Config {
    dependsOn: ["testIncrBy"]
}
public function testIncrByFloat() {
    var result = conn->incrByFloat("testIncrByKey", 0.2);
    test:assertEquals(result, 7.200000002980232);
}

@test:Config {
}
function testMGet() {
   var result = conn->mGet(["testMGetKey1", "testMGetKey2", "testMGetKey3"]);
   test:assertEquals(result.length(), 3);
}

@test:Config {
}
function testMSet() {
   map<any> keyValueMap = { testMSetKey1: "testMSetValue1", testMSetKey2: "testMSetValue2", testMSetKey3: "testMSetValue3" }
   ;
   var result = conn->mSet(keyValueMap);
   test:assertEquals(result, "OK");
}

@test:Config {
}
function testMSetNx() {
   map<any> keyValueMap = { testMSetNxKey1: "testMSetNxNewValue1", testMSetNxKey2: "testMSetNxValue2", testMSetNxKey3:
   "testMSetNxValue3" };
   var result = conn->mSetNx(keyValueMap);
   test:assertFalse(result);
}

@test:Config {
}
public function testPSetEx() {
    var result = conn->pSetEx("testPSetExKey", "testPSetExNewValue", 5000);
    test:assertEquals(result, "OK");
}

@test:Config {
}
public function testSetBit() {
    var result = conn->setBit("testSetBitKey", 1, 2);
    test:assertEquals(result, 1);
}

@test:Config {
}
public function testSetEx() {
    var result = conn->setEx("testSetExKey", "testSetExNewValue", 5);
    test:assertEquals(result, "OK");
}

@test:Config {
}
public function testSetNx() {
    var result = conn->setNx("testSetNxKey", "testSetNxValue");
    test:assertEquals(result, true);
}

@test:Config {
}
public function testSetRange() {
    var result = conn->setRange("testSetRangeKey", 2, "!!!");
    test:assertEquals(result, 17);
}

@test:Config {
}
public function testStrln() {
    var result = conn->strln("testStrlnKey");
    test:assertEquals(result, 14);
}

function setupRedisStringDatabase() = @java:Method{
    name: "setupStringDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;

//function get(handle key) returns handle = @java:Method{
//    name: "get",
//    class: "org.ballerinalang.redis.utils.RedisDbUtils"
//} external;
