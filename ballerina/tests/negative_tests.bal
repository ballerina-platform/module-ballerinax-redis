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
    groups: ["standalone", "cluster"]
}
function testMSetNxForExistingKey() returns error? {
    map<any> keyValueMap = {
        "{StringTag}testMSetNxKey1": "testMSetNxNewValue1",
        "{StringTag}testMSetNxKey2": "testMSetNxValue2",
        "{StringTag}testMSetNxKey3": "testMSetNxValue3"
    };

    boolean result = check redis->mSetNx(keyValueMap);
    test:assertFalse(result);

    string? getResult1 = check redis->get("{StringTag}testMSetNxKey1");
    test:assertEquals(getResult1, "testMSetNxValue1");
    string? getResult2 = check redis->get("{StringTag}testMSetNxKey2");
    test:assertEquals(getResult2, ());
    string? getResult3 = check redis->get("{StringTag}testMSetNxKey3");
    test:assertEquals(getResult3, ());
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testRenameNxForExistingKey() returns error? {
    boolean renameResult2 = check redis->renameNx("{KeyTag}testRenameNxKey1", "{KeyTag}testRenameNxKeyExisting");
    test:assertFalse(renameResult2);
    int existResult2 = check redis->exists(["{KeyTag}testRenameNxKey1"]);
    test:assertEquals(existResult2, 1);
}

@test:Config {
    groups: ["cluster"]
}
function testMoveInClusterMode() returns error? {
    // TODO: remove this hack along with the test groups based approach, once https://github.com/ballerina-platform/ballerina-lang/issues/42028 is fixed   
    if !clusterMode {
        return ();
    }

    boolean|error moveResult = redis->move("testMoveKey", 1);
    if (moveResult !is error) {
        test:assertFail("move operation should not be supported in cluster mode");
    }

    test:assertEquals(moveResult.message(), "Redis server error: ERR MOVE is not allowed in cluster mode");
}

@test:Config {
    groups: ["standalone"]
}
function testClusterInfoInStandaloneMode() returns error? {
    if clusterMode {
        return ();
    }

    string[]|error clusterInfoResult = redis->clusterInfo();
    if (clusterInfoResult !is Error) {
        test:assertFail("clusterInfo operation should not be supported in standalone mode");
    }
    test:assertTrue(clusterInfoResult.message() == "Cannot execute cluster info command on a non-cluster connection");
}
