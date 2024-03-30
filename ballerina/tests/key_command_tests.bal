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
function testDel() returns error? {
    int result = check redis->del(["testDelKey1", "testDelKey2", "testDelKey3"]);
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testExists() returns error? {
    int result1 = check redis->exists(["testExistsKey"]);
    int result2 = check redis->exists(["nonExistentKey"]);
    test:assertEquals(result1, 1);
    test:assertEquals(result2, 0);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testKeys() returns error? {
    string[] result = check redis->keys("testKeysKey*");

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
function testExpire() returns error? {
    boolean expireResult = check redis->expire("testExpireKey", 2);
    test:assertTrue(expireResult);

    int ttl = check redis->ttl("testExpireKey");
    test:assertTrue(ttl <= 2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testPExpire() returns error? {
    boolean expireResult = check redis->pExpire("testPExpireKey", 2000);
    test:assertTrue(expireResult);

    int pTtl = check redis->pTtl("testPExpireKey");
    test:assertTrue(pTtl <= 2000);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testTtl() returns error? {
    boolean expireResult = check redis->expire("testTtlKey", 10);
    test:assertTrue(expireResult);

    int ttl1 = check redis->ttl("testTtlKey");
    int ttl2 = check redis->ttl("testTtlKey");
    test:assertTrue(ttl1 <= 10);
    test:assertTrue(ttl2 <= ttl1);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testPTtl() returns error? {
    boolean expireResult = check redis->pExpire("testPTtlKey", 10000);
    test:assertTrue(expireResult);

    int pTtl1 = check redis->pTtl("testPTtlKey");
    int pTtl2 = check redis->pTtl("testPTtlKey");
    test:assertTrue(pTtl1 <= 10000);
    test:assertTrue(pTtl2 <= pTtl1);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRandomKey() returns error? {
    string? result = check redis->randomKey();
    if result is string {
        test:assertNotEquals(result, "");
    } else {
        test:assertFail("Key not found");
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
function testSort() returns error? {
    string[] result = check redis->sort("testSortKey");

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
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testType() returns error? {
    string result = check redis->redisType("testTypeKey");
    test:assertEquals(result, "string");
}
