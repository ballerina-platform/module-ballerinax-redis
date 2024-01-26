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
function testZAdd() {
    map<any> valueScoreMap = {testZAddValue2: 2.0};
    var result = redis->zAdd("testZAddKey", valueScoreMap);
    if (result is int) {
        test:assertEquals(result, 1);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZCard() {
    var result = redis->zCard("testZCardKey");
    if (result is int) {
        test:assertEquals(result, 2);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZCount() {
    var result = redis->zCount("testZCountKey", 2.0, 5.0);
    if (result is int) {
        test:assertEquals(result, 3);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZIncrBy() {
    var result = redis->zIncrBy("testZIncrByKey", 1.0, "testZIncrByValue1");
    if (result is float) {
        test:assertEquals(result, 7.0);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZInterStore() {
    var result = redis->zInterStore("{SortedSetTag}testZInterStoreDestKey", ["{SortedSetTag}testZInterStoreKey1", "{SortedSetTag}testZInterStoreKey2"]);
    if (result is int) {
        test:assertEquals(result, 2);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZLexCount() {
    var result = redis->zLexCount("testZLexCountKey", "b", "f");
    if (result is int) {
        test:assertEquals(result, 5);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRange() {
    var result = redis->zRange("testZRangeKey", 1, 3);
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
        test:assertEquals(result[0], "d");
        test:assertEquals(result[1], "b");
        test:assertEquals(result[2], "e");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRangeByLex() {
    var result = redis->zRangeByLex("testZRangeByLexKey", "aaa", "g");
    if (result is string[]) {
        test:assertEquals(result.length(), 6);
        test:assertEquals(result[0], "b");
        test:assertEquals(result[1], "c");
        test:assertEquals(result[2], "d");
        test:assertEquals(result[3], "e");
        test:assertEquals(result[4], "f");
        test:assertEquals(result[5], "g");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRevRangeByLex() {
    var result = redis->zRevRangeByLex("testZRangeByLexKey", "aaaa", "g");
    if (result is string[]) {
        test:assertEquals(result.length(), 6);
        test:assertEquals(result[0], "g");
        test:assertEquals(result[1], "f");
        test:assertEquals(result[2], "e");
        test:assertEquals(result[3], "d");
        test:assertEquals(result[4], "c");
        test:assertEquals(result[5], "b");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRangeByScore() {
    var result = redis->zRangeByScore("testZRangeKey", 2.0, 4.0);
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
        test:assertEquals(result[0], "b");
        test:assertEquals(result[1], "e");
        test:assertEquals(result[2], "f");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRank() {
    var result = redis->zRank("testZRankKey", "a");
    if (result is int) {
        test:assertEquals(result, 0);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRem() {
    var result = redis->zRem("testZRemKey", ["a", "b"]);
    if (result is int) {
        test:assertEquals(result, 2);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRemRangeByLex() {
    var result = redis->zRemRangeByLex("testZRemRangeByLexKey", "alpha", "omega");
    if (result is int) {
        test:assertEquals(result, 6);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRemRangeByRank() {
    var result = redis->zRemRangeByRank("testZRemRangeByRankKey", 1, 4);
    if (result is int) {
        test:assertEquals(result, 4);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRemRangeByScore() {
    var result = redis->zRemRangeByScore("testZRemRangeByScoreKey", 1, 4);
    if (result is int) {
        test:assertEquals(result, 4);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRevRange() {
    var result = redis->zRevRange("testZRangeKey", 1, 3);
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
        boolean allRangeRetrieved = true;
        string[] memberArray = ["f", "e", "c"];
        foreach var m in memberArray {
            boolean memberExists = false;
            foreach var r in result {
                if (m == r) {
                    memberExists = true;
                    break;
                }
            }
            if (!memberExists) {
                allRangeRetrieved = false;
                break;
            }
        }
        test:assertTrue(allRangeRetrieved);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRevRangeByScore() {
    var result = redis->zRangeByScore("testZRangeKey", 2.0, 4.0);
    if (result is string[]) {
        test:assertEquals(result.length(), 3);
        test:assertEquals(result[0], "b");
        test:assertEquals(result[1], "e");
        test:assertEquals(result[2], "f");
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRevRank() {
    var result = redis->zRevRank("testZRankKey", "c");
    if (result is int) {
        test:assertEquals(result, 0);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZScore() {
    var result = redis->zScore("testZScoreKey", "a");
    if (result is float) {
        test:assertEquals(result, 1.2);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZUnionStore() {
    var result = redis->zUnionStore("{SortedSetTag}testZUnionStoreDestKey", ["{SortedSetTag}testZUnionStoreKey1", "{SortedSetTag}testZUnionStoreKey2"]);
    if (result is int) {
        test:assertEquals(result, 6);
    } else {
        test:assertFail("error from Connector: " + result.message());
    }
}
