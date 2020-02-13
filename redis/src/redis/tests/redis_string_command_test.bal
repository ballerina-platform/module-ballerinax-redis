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
public function testSet() {
    var result = conn->set("Name", "Manuri");
    test:assertEquals(result, "OK");
}

@test:Config {
}
public function testAppend() {
    var result = conn->append("AppendTestKey", "SomeValue");
    if (result is int) {
        test:assertEquals(result, 24);   
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testBitCount() {
    var result = conn->bitCount("BitCountTestKey");
    if (result is int) {
        test:assertEquals(result, 69); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function tesBitOpAnd() {
   string[] keys = ["testBitOpKey1", "testBitOpKey2"];
   var result = conn->bitOpAnd("tesBitOpAndDest", keys);
   if (result is int) {
        test:assertEquals(result, 3); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function tesBitOpOr() {
   string[] keys = ["testBitOpKey1", "testBitOpKey2"];
   var result = conn->bitOpOr("tesBitOpOrDest", keys);
   if (result is int) {
        test:assertEquals(result, 3); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function tesBitOpNot() {
   var result = conn->bitOpNot("tesBitOpNotDest", "testBitOpKey1");
   if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function tesBitOpXor() {
   var result = conn->bitOpXor("tesBitOpXorDest", ["testBitOpKey1", "testBitOpKey2"]);
   if (result is int) {
        test:assertEquals(result, 3); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testDecr() {
    var result = conn->decr("testDecrKey");
    if (result is int) {
        test:assertEquals(result, 7); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testDecrBy() {
    var result = conn->decrBy("testDecrByKey", 3);
    if (result is int) {
        test:assertEquals(result, 7); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testGet() {
    var result = conn->get("GetTestKey");
    if (result is string) {
        test:assertEquals(result, "GetTestValue");  
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testGetBit() {
    var result = conn->getBit("testGetBitKey", 4);
    if (result is int) {
        test:assertEquals(result, 0);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testGetRange() {
    var result = conn->getRange("testGetRangekey", 2, 5);
    if (result is string) {
        test:assertEquals(result, "3456"); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testGetSet() {
    var result = conn->getSet("testGetSetKey", "testGetSetNewValue");
    if (result is string) {
        test:assertEquals(result, "testGetSetValue");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testIncr()  {
    var result = conn->incr("testIncrKey");
    if (result is int) {
        test:assertEquals(result, 7); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testIncrBy() {
    var result = conn->incrBy("testIncrByKey", 2);
    if (result is int) {
        test:assertEquals(result, 7);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
    dependsOn: ["testIncrBy"]
}
public function testIncrByFloat() {
    var result = conn->incrByFloat("testIncrByKey", 0.2);
    if (result is float) {
        test:assertEquals(result, 7.200000002980232); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testMGet() {
   var result = conn->mGet(["testMGetKey1", "testMGetKey2", "testMGetKey3"]);
   if (result is string[]) {
        test:assertEquals(result.length(), 3); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testMSet() {
   map<any> keyValueMap = { testMSetKey1: "testMSetValue1", testMSetKey2: "testMSetValue2", testMSetKey3: "testMSetValue3" };
   var result = conn->mSet(keyValueMap);
   if (result is string) {
        test:assertEquals(result, "OK");  
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testMSetNx() {
   map<any> keyValueMap = { testMSetNxKey1: "testMSetNxNewValue1", testMSetNxKey2: "testMSetNxValue2", testMSetNxKey3:
   "testMSetNxValue3" };
   var result = conn->mSetNx(keyValueMap);
   if (result is boolean) {
        test:assertFalse(result); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testPSetEx() {
    var result = conn->pSetEx("testPSetExKey", "testPSetExNewValue", 5000);
    if (result is string) {
        test:assertEquals(result, "OK");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testSetBit() {
    var result = conn->setBit("testSetBitKey", 1, 2);
    if (result is int) {
        test:assertEquals(result, 1); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testSetEx() {
    var result = conn->setEx("testSetExKey", "testSetExNewValue", 5);
    if (result is string) {
        test:assertEquals(result, "OK"); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testSetNx() {
    var result = conn->setNx("testSetNxKey", "testSetNxValue");
    if (result is boolean) {
        test:assertEquals(result, true); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testSetRange() {
    var result = conn->setRange("testSetRangeKey", 2, "!!!");
    if (result is int) {
        test:assertEquals(result, 17); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
public function testStrln() {
    var result = conn->strln("testStrlnKey");
    if (result is int) {
        test:assertEquals(result, 14);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

function setupRedisStringDatabase() = @java:Method{
    name: "setupStringDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;
