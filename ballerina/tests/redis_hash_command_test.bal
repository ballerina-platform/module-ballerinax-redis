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

@test:Config {}
function testHDel() {
    do {
        int result = check redis->hDel("testHDelKey", ["testHDelField1", "testHDelField2", "testHDelField3"]);
        test:assertEquals(result, 3);

        boolean hExistsResult = check redis->hExists("testHDelKey", "testHDelField1");
        boolean hExistsResult2 = check redis->hExists("testHDelKey", "testHDelField2");
        boolean hExistsResult3 = check redis->hExists("testHDelKey", "testHDelField3");
        test:assertTrue(!hExistsResult && !hExistsResult2 && !hExistsResult3);
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
function testHExists() {
    var result = redis->hExists("testHExistsKey", "testHExistsField1");
    if (result is boolean) {
        test:assertEquals(result, true);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHGet() {
    var result = redis->hGet("testHGetKey", "testHGetField1");
    if (result is string) {
        test:assertEquals(result, "testHGetValue1");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHGetAll() {
    var result = redis->hGetAll("testHGetAllKey");
    if (result is map<any>) {
        test:assertEquals(result.length(), 2);
        test:assertTrue(result.get("testHGetAllField1").toString() == "testHGetAllValue1" &&
            result.get("testHGetAllField2").toString() == "testHGetAllValue2");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHIncrByFloat() {
    var result = redis->hIncrByFloat("testHIncrByFloatKey", "testHIncrByFloatField1", 0.2);
    if (result is float) {
        test:assertEquals(result, 7.2f);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHIncrBy() {
    var result = redis->hIncrBy("testHIncrByKey", "testHIncrByField1", 2);
    if (result is int) {
        test:assertEquals(result, 8);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHLen() {
    var result = redis->hLen("testHLenKey");
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHMGet() {
    var result = redis->hMGet("testHMGetKey", ["testHMGetField1", "testHMGetField2", "testHMGetField3"]);
    if (result is map<any>) {
        test:assertEquals(result.length(), 3);
        test:assertTrue(result.get("testHMGetField1").toString() == "testHMGetValue1" &&
            result.get("testHMGetField2").toString() == "testHMGetValue2" &&
            result.get("testHMGetField3").toString() == "testHMGetValue3");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHMSet() {
    do {
        map<any> fieldValueMap = {testHMSetField1: "testHMSetValue1", testHMSetField2: "testHMSetValue2"};
        string result = check redis->hMSet("testHMSetKey", fieldValueMap);
        test:assertEquals(result, "OK");

        boolean hExistsResult = check redis->hExists("testHMSetKey", "testHMSetField1");
        string hGetResult = check redis->hGet("testHMSetKey", "testHMSetField1");
        boolean hExistsResult2 = check redis->hExists("testHMSetKey", "testHMSetField2");
        string hGetResult2 = check redis->hGet("testHMSetKey", "testHMSetField2");
        test:assertTrue(hExistsResult && hGetResult == "testHMSetValue1" && hExistsResult2 && hGetResult2 == "testHMSetValue2");
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
function testHKeys() {
    var result = redis->hKeys("testHKeysKey");
    if (result is string[]) {
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
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHSet() {
    do {
        boolean hSetResult = check redis->hSet("testHSetKey", "testHSetField1", "testHSetValue1");
        test:assertTrue(hSetResult);

        boolean hExistsResult = check redis->hExists("testHSetKey", "testHSetField1");
        string hGetResult = check redis->hGet("testHSetKey", "testHSetField1");
        test:assertTrue(hExistsResult && hGetResult == "testHSetValue1");
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
function testHSetNx() {
    do {
        boolean hSetResult = check redis->hSet("testHSetNxKey", "testHSetNxField1", "testHSetNxValue1");
        test:assertTrue(hSetResult);

        boolean hExistsResult = check redis->hExists("testHSetNxKey", "testHSetNxField1");
        string hGetResult = check redis->hGet("testHSetNxKey", "testHSetNxField1");
        test:assertTrue(hExistsResult && hGetResult == "testHSetNxValue1");
    } on fail error e {
        test:assertFail("error from connector: " + e.message());
    }
}

@test:Config {}
function testHStrln() {
    var result = redis->hStrln("testHStrlnKey", "testHStrlnField1");
    if (result is int) {
        test:assertEquals(result, 16);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {}
function testHVals() {
    var result = redis->hVals("testHValsKey");
    if (result is string[]) {
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
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}
