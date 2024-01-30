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
import ballerina/lang.runtime;
import ballerina/test;

@test:Config {
    groups: ["standalone", "cluster"]
}
function testDel() {
    var result = redis->del(["testDelKey1", "testDelKey2", "testDelKey3"]);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testExists() {
    var result1 = redis->exists(["testExistsKey"]);
    var result2 = redis->exists(["nonExistentKey"]);
    if (result1 is int) {
        test:assertEquals(result1, 1);
    } else {
        test:assertFail("error from Connector: " + result1.message());
    }
    if (result2 is int) {
        test:assertEquals(result2, 0);
    } else {
        test:assertFail("error from Connector: " + result2.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testExpire() returns error? {
    boolean result = check redis->expire("testExpireKey", 3);
    test:assertTrue(result);

    runtime:sleep(3);
    int existsResult = check redis->exists(["testExpireKey"]);
    test:assertEquals(existsResult, 0);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testKeys() {
    var result = redis->keys("testKeysKey*");
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
        boolean allMatchingKeysRetrieved = true;
        string[] keys = ["testKeysKey1", "testKeysKey2", "testKeysKey3"];
        foreach var k in keys {
            boolean keyExists = false;
            foreach var r in result {
                if (k == r) {
                    keyExists = true;
                    break;
                }
            }
            if (!keyExists) {
                allMatchingKeysRetrieved = false;
                break;
            }
        }
        test:assertTrue(allMatchingKeysRetrieved);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone"]
}
function testMove() returns error? {
    // TODO: remove this hack along with the test groups based approach, once https://github.com/ballerina-platform/ballerina-lang/issues/42028 is fixed
    if clusterMode {
        return;
    }

    boolean moveResult = check redis->move("testMoveKey", 1);
    test:assertTrue(moveResult);

    int existsResult = check redis->exists(["testMoveKey"]);
    test:assertEquals(existsResult, 0);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testPersist() returns error? {
    boolean persistResult = check redis->persist("testPersistKey");
    runtime:sleep(3);
    test:assertFalse(persistResult);

    int existsResult = check redis->exists(["testPersistKey"]);
    test:assertEquals(existsResult, 1);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testPExpire() returns error? {
    boolean expireResult = check redis->pExpire("testPExpireKey", 3000);
    test:assertTrue(expireResult);

    runtime:sleep(3.5);
    int existsResult = check redis->exists(["testPExpireKey"]);
    test:assertEquals(existsResult, 0);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testPTtl() returns error? {
    boolean _ = check redis->pExpire("testPTtlKey", 10000);
    int result = check redis->pTtl("testPTtlKey");
    runtime:sleep(5);
    int ttl = check redis->pTtl("testPTtlKey");
    test:assertTrue(result >= ttl && result <= 10000);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRandomKey() {
    var result = redis->randomKey();
    if (result is string) {
        test:assertNotEquals(result, "");
    } else if (result is ()) {
        test:assertFail("Key not found");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRename() returns error? {
    string result = check redis->rename("{KeyTag}testRenameKey", "{KeyTag}testRenameKey1");
    test:assertEquals(result, "OK");

    int existsResult = check redis->exists(["{KeyTag}testRenameKey"]);
    test:assertEquals(existsResult, 0);
    int existsResult1 = check redis->exists(["{KeyTag}testRenameKey1"]);
    test:assertEquals(existsResult1, 1);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRenameNx() returns error? {
    boolean renameResult = check redis->renameNx("{KeyTag}testRenameNxKey", "{KeyTag}testRenameNxKeyRenamed");
    test:assertTrue(renameResult);

    int existResult = check redis->exists(["{KeyTag}testRenameNxKey"]);
    test:assertEquals(existResult, 0);
    int existResult2 = check redis->exists(["{KeyTag}testRenameNxKeyRenamed"]);
    test:assertEquals(existResult2, 1);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testSort() {
    var result = redis->sort("testSortKey");
    if (result is string[]) {
        test:assertEquals(result.length(), 6);
        boolean elementsInOrder = true;
        string[] values = ["0", "1", "2", "3", "4", "8"];
        int count = 0;
        foreach var v in values {
            if (v != values[count]) {
                elementsInOrder = false;
                break;
            }
            count += 1;
        }
        test:assertTrue(elementsInOrder);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testTtl() returns error? {
    _ = check redis->pExpire("testTtlKey", 10);
    int result = check redis->ttl("testTtlKey");
    int ttl = check redis->ttl("testTtlKey");
    test:assertTrue(result >= ttl && result <= 10000);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testType() {
    var result = redis->redisType("testTypeKey");
    if (result is string) {
        test:assertEquals(result, "string");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}
