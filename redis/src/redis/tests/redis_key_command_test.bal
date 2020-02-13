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
function testDel() {
   var result = conn->del(["testDelKey1", "testDelKey2", "testDelKey3"]);
   if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testExists() {
    var result = conn->exists(["testExistsKey"]);
    var result2 = conn->exists(["nonExistentKey"]);
    if (result is int) {
        test:assertEquals(result, 1);  
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
    if (result is int) {
        test:assertEquals(result2, 0); 
    }
}

@test:Config {
}
function testExpire() {
    var result = conn->expire("testExpireKey", 3);
    if (result is boolean) {
        test:assertTrue(result);  
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testKeys() {
    var result = conn->keys("testKeysKey*");
    if (result is string[]) {
        test:assertEquals(result.length(), 3);   
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testMove() {
    var result = conn->move("testMoveKey", 1);
    if (result is boolean) {
        test:assertTrue(result);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testPersist() {
    var result = conn->persist("testPersistKey");
    if (result is boolean) {
        test:assertFalse(result);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testPExpire() {
    var result = conn->pExpire("testPExpireKey", 3000);
    if (result is boolean) {
        test:assertTrue(result);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
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
    if (result is string) {
        test:assertEquals(result, "OK");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

@test:Config {
}
function testRenameNx() {
    var result = conn->renameNx("testRenameNxKey", "testRenameNxKeyRenamed");
    var result2 = conn->renameNx("testRenameNxKey1", "testRenameNxKeyExisting");
    if (result is boolean) {
        test:assertTrue(result);
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
    if (result2 is boolean) {
        test:assertFalse(result2); 
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result2.reason(), <string> result2.detail()["message"]));
    }
}

@test:Config {
}
function testSort() {
    var result = conn->sort("testSortKey");
    if (result is string[]) {
        test:assertEquals(result.length(), 6);
        test:assertEquals(result[0], "0");
        test:assertEquals(result[1], "1");
        test:assertEquals(result[2], "2");
        test:assertEquals(result[3], "3");
        test:assertEquals(result[4], "4");
        test:assertEquals(result[5], "8");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
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
    if (result is string) {
        test:assertEquals(result, "string");
    } else {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(), <string> result.detail()["message"]));
    }
}

function setupRedisKeyDatabase() = @java:Method{
    name: "setupKeyDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;