// Copyright (c) 2024 WSO2 LLC. (http://www.wso2.org)
//
// WSO2 LLC. licenses this file to you under the Apache License,
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
    groups: ["standalone", "cluster"],
    enable: false
}
function testMSetNxForExistingKey() returns error? {
    map<any> keyValueMap = {
        "testMSetNxKey1": "testMSetNxNewValue1",
        "testMSetNxKey2": "testMSetNxValue2",
        "testMSetNxKey3": "testMSetNxValue3"
    };

    boolean result = check redis->mSetNx(keyValueMap);
    test:assertFalse(result);

    string? getResult1 = check redis->get("testMSetNxKey1");
    test:assertEquals(getResult1, "testMSetNxValue1");
    string? getResult2 = check redis->get("testMSetNxKey2");
    test:assertEquals(getResult2, ());
    string? getResult3 = check redis->get("testMSetNxKey3");
    test:assertEquals(getResult3, ());
}

@test:Config {
    groups: ["standalone", "cluster"],
    enable: false
}
function testRenameNxForExistingKey() returns error? {
    boolean renameResult2 = check redis->renameNx("testRenameNxKey1", "testRenameNxKeyExisting");
    test:assertFalse(renameResult2);
    int existResult2 = check redis->exists(["testRenameNxKey1"]);
    test:assertEquals(existResult2, 1);
}

@test:Config {
    groups: ["cluster"],
    enable: false
}
function testMoveInClusterMode() returns error? {
    boolean|error moveResult = check redis->move("testMoveKey", 1);
    test:assertTrue(moveResult is error);
}
