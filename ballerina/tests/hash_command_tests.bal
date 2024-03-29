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
function testHDel() returns error? {
    int result = check redis->hDel("testHDelKey", ["testHDelField1", "testHDelField2", "testHDelField3"]);
    test:assertEquals(result, 3);

    boolean hExistsResult = check redis->hExists("testHDelKey", "testHDelField1");
    boolean hExistsResult2 = check redis->hExists("testHDelKey", "testHDelField2");
    boolean hExistsResult3 = check redis->hExists("testHDelKey", "testHDelField3");
    test:assertTrue(!hExistsResult && !hExistsResult2 && !hExistsResult3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHExists() returns error? {
    boolean result = check redis->hExists("testHExistsKey", "testHExistsField1");
    test:assertTrue(result);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHGet() returns error? {
    string result = check redis->hGet("testHGetKey", "testHGetField1");
    test:assertEquals(result, "testHGetValue1");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHGetAll() returns error? {
    map<any> result = check redis->hGetAll("testHGetAllKey");
    test:assertEquals(result.length(), 2);
    test:assertTrue(result.get("testHGetAllField1").toString() == "testHGetAllValue1");
    test:assertTrue(result.get("testHGetAllField2").toString() == "testHGetAllValue2");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHIncrByFloat() returns error? {
    float result = check redis->hIncrByFloat("testHIncrByFloatKey", "testHIncrByFloatField1", 0.2);
    test:assertEquals(result, 7.2f);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHIncrBy() returns error? {
    int result = check redis->hIncrBy("testHIncrByKey", "testHIncrByField1", 2);
    test:assertEquals(result, 8);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHLen() returns error? {
    int result = check redis->hLen("testHLenKey");
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHMGet() returns error? {
    map<any> result = check redis->hMGet("testHMGetKey", ["testHMGetField1", "testHMGetField2", "testHMGetField3"]);
    test:assertEquals(result.length(), 3);
    test:assertTrue(result.get("testHMGetField1").toString() == "testHMGetValue1");
    test:assertTrue(result.get("testHMGetField2").toString() == "testHMGetValue2");
    test:assertTrue(result.get("testHMGetField3").toString() == "testHMGetValue3");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHMSet() returns error? {
    map<any> fieldValueMap = {testHMSetField1: "testHMSetValue1", testHMSetField2: "testHMSetValue2"};
    string result = check redis->hMSet("testHMSetKey", fieldValueMap);
    test:assertEquals(result, "OK");

    boolean hExistsResult = check redis->hExists("testHMSetKey", "testHMSetField1");
    string hGetResult = check redis->hGet("testHMSetKey", "testHMSetField1");
    boolean hExistsResult2 = check redis->hExists("testHMSetKey", "testHMSetField2");
    string hGetResult2 = check redis->hGet("testHMSetKey", "testHMSetField2");
    test:assertTrue(hExistsResult && hGetResult == "testHMSetValue1" && hExistsResult2 && hGetResult2 == "testHMSetValue2");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHKeys() returns error? {
    string[] result = check redis->hKeys("testHKeysKey");

    test:assertEquals(result.length(), 3);
    boolean allFieldsRetrieved = true;
    string[] hKeys = ["testHKeysField1", "testHKeysField2", "testHKeysField3"];
    foreach var k in hKeys {
        boolean keyExists = false;
        foreach var r in result {
            if (k == r) {
                keyExists = true;
                break;
            }
        }
        if (!keyExists) {
            allFieldsRetrieved = false;
            break;
        }
    }
    test:assertTrue(allFieldsRetrieved);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHSet() returns error? {
    boolean hSetResult = check redis->hSet("testHSetKey", "testHSetField1", "testHSetValue1");
    test:assertTrue(hSetResult);

    boolean hExistsResult = check redis->hExists("testHSetKey", "testHSetField1");
    string hGetResult = check redis->hGet("testHSetKey", "testHSetField1");
    test:assertTrue(hExistsResult && hGetResult == "testHSetValue1");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHSetNx() returns error? {
    boolean hSetResult = check redis->hSetNx("testHSetNxKey", "testHSetNxField1", "testHSetNxValue1");
    test:assertTrue(hSetResult);

    boolean hExistsResult = check redis->hExists("testHSetNxKey", "testHSetNxField1");
    string hGetResult = check redis->hGet("testHSetNxKey", "testHSetNxField1");
    test:assertTrue(hExistsResult && hGetResult == "testHSetNxValue1");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHStrLen() returns error? {
    int result = check redis->hStrLen("testHStrLenKey", "testHStrLenField1");
    test:assertEquals(result, 17);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testHVals() returns error? {
    string[] result = check redis->hVals("testHValsKey");

    test:assertEquals(result.length(), 3);
    boolean allValuesRetrieved = true;
    string[] hVals = ["testHValsValue1", "testHValsValue1", "testHValsValue1"];
    foreach var v in hVals {
        boolean keyExists = false;
        foreach var r in result {
            if (v == r) {
                keyExists = true;
                break;
            }
        }
        if (!keyExists) {
            allValuesRetrieved = false;
            break;
        }
    }
    test:assertTrue(allValuesRetrieved);
}
