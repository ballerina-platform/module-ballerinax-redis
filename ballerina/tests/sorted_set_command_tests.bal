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
function testZAdd() returns error? {
    map<any> valueScoreMap = {testZAddValue2: 2.0};
    int result = check redis->zAdd("testZAddKey", valueScoreMap);
    test:assertEquals(result, 1);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZCard() returns error? {
    int result = check redis->zCard("testZCardKey");
    test:assertEquals(result, 2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZCount() returns error? {
    int result = check redis->zCount("testZCountKey", 2.0, 5.0);
    test:assertEquals(result, 3);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZIncrBy() returns error? {
    float result = check redis->zIncrBy("testZIncrByKey", 1.0, "testZIncrByValue1");
    test:assertEquals(result, 7.0);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZInterStore() returns error? {
    int result = check redis->zInterStore("{SortedSetTag}testZInterStoreDestKey", ["{SortedSetTag}testZInterStoreKey1", "{SortedSetTag}testZInterStoreKey2"]);
    test:assertEquals(result, 2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZLexCount() returns error? {
    int result = check redis->zLexCount("testZLexCountKey", "b", "f");
    test:assertEquals(result, 5);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRange() returns error? {
    string[] result = check redis->zRange("testZRangeKey", 1, 3);

    test:assertEquals(result.length(), 3);
    test:assertEquals(result[0], "d");
    test:assertEquals(result[1], "b");
    test:assertEquals(result[2], "e");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRangeByLex() returns error? {
    string[] result = check redis->zRangeByLex("testZRangeByLexKey", "aaa", "g");

    test:assertEquals(result.length(), 6);
    test:assertEquals(result[0], "b");
    test:assertEquals(result[1], "c");
    test:assertEquals(result[2], "d");
    test:assertEquals(result[3], "e");
    test:assertEquals(result[4], "f");
    test:assertEquals(result[5], "g");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRevRangeByLex() returns error? {
    string[] result = check redis->zRevRangeByLex("testZRangeByLexKey", "aaaa", "g");

    test:assertEquals(result.length(), 6);
    test:assertEquals(result[0], "g");
    test:assertEquals(result[1], "f");
    test:assertEquals(result[2], "e");
    test:assertEquals(result[3], "d");
    test:assertEquals(result[4], "c");
    test:assertEquals(result[5], "b");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRangeByScore() returns error? {
    string[] result = check redis->zRangeByScore("testZRangeKey", 2.0, 4.0);

    test:assertEquals(result.length(), 3);
    test:assertEquals(result[0], "b");
    test:assertEquals(result[1], "e");
    test:assertEquals(result[2], "f");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRank() returns error? {
    int result = check redis->zRank("testZRankKey", "a");
    test:assertEquals(result, 0);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRem() returns error? {
    int result = check redis->zRem("testZRemKey", ["a", "b"]);
    test:assertEquals(result, 2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRemRangeByLex() returns error? {
    int result = check redis->zRemRangeByLex("testZRemRangeByLexKey", "alpha", "omega");
    test:assertEquals(result, 6);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRemRangeByRank() returns error? {
    int result = check redis->zRemRangeByRank("testZRemRangeByRankKey", 1, 4);
    test:assertEquals(result, 4);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRemRangeByScore() returns error? {
    int result = check redis->zRemRangeByScore("testZRemRangeByScoreKey", 1, 4);
    test:assertEquals(result, 4);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRevRange() returns error? {
    string[] result = check redis->zRevRange("testZRangeKey", 1, 3);

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
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRevRangeByScore() returns error? {
    string[] result = check redis->zRevRangeByScore("testZRangeKey", 2.0, 4.0);
    test:assertEquals(result.length(), 3);
    test:assertEquals(result[0], "f");
    test:assertEquals(result[1], "e");
    test:assertEquals(result[2], "b");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZRevRank() returns error? {
    int result = check redis->zRevRank("testZRankKey", "c");
    test:assertEquals(result, 0);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZScore() returns error? {
    float result = check redis->zScore("testZScoreKey", "a");
    test:assertEquals(result, 1.2);
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testZUnionStore() returns error? {
    int result = check redis->zUnionStore("{SortedSetTag}testZUnionStoreDestKey", ["{SortedSetTag}testZUnionStoreKey1", "{SortedSetTag}testZUnionStoreKey2"]);
    test:assertEquals(result, 6);
}
