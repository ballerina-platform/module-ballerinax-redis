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

@test:Config {
}
function testZAdd() {
    map<any> valueScoreMap = { testZAddValue2: 2.0 };
    var result = conn->zAdd("testZAddKey", valueScoreMap);
    test:assertEquals(result, 1);
}

@test:Config {
}
function testZCard() {
    var result = conn->zCard("testZCardKey");
    test:assertEquals(result, 2);
}

@test:Config {
}
function testZCount() {
    var result = conn->zCount("testZCountKey", 2.0, 5.0);
    test:assertEquals(result, 3);    
}

@test:Config {
}
function testZIncrBy() {
    var result = conn->zIncrBy("testZIncrByKey", 1.0, "testZIncrByValue1");
    test:assertEquals(result, 7.0);  
}

@test:Config {
}
function testZInterStore() {
    var result = conn->zInterStore("testZInterStoreDestKey", ["testZInterStoreKey1", "testZInterStoreKey2"]);
    test:assertEquals(result, 2);  
}

@test:Config {
}
function testZLexCount() {
    var result = conn->zLexCount("testZLexCountKey", "b", "f");
    test:assertEquals(result, 5);  
}

@test:Config {
}
function testZRange() {
    var result = conn->zRange("testZRangeKey", 1, 3);
    test:assertEquals(result.length(), 3); 
    test:assertEquals(result[0], "d"); 
    test:assertEquals(result[1], "b"); 
    test:assertEquals(result[2], "e");
}

@test:Config {
}
function testZRangeByLex() {
    var result = conn->zRangeByLex("testZRangeByLexKey", "aaa", "g");
    test:assertEquals(result.length(), 6); 
    test:assertEquals(result[0], "b"); 
    test:assertEquals(result[1], "c"); 
    test:assertEquals(result[2], "d");
    test:assertEquals(result[3], "e"); 
    test:assertEquals(result[4], "f"); 
    test:assertEquals(result[5], "g");
}

@test:Config {
}
function testZRevRangeByLex() {
    var result = conn->zRevRangeByLex("testZRangeByLexKey", "aaaa", "g");
    test:assertEquals(result.length(), 6); 
    test:assertEquals(result[0], "g"); 
    test:assertEquals(result[1], "f"); 
    test:assertEquals(result[2], "e");
    test:assertEquals(result[3], "d"); 
    test:assertEquals(result[4], "c"); 
    test:assertEquals(result[5], "b");
}

@test:Config {
}
function testZRangeByScore() {
    var result = conn->zRangeByScore("testZRangeKey", 2.0, 4.0);
    test:assertEquals(result.length(), 3); 
    test:assertEquals(result[0], "b"); 
    test:assertEquals(result[1], "e"); 
    test:assertEquals(result[2], "f");
}

@test:Config {
}
function testZRank() {
    var result = conn->zRank("testZRankKey", "a");
    test:assertEquals(result, 0); 
}

@test:Config {
}
function testZRem() {
    var result = conn->zRem("testZRemKey", ["a", "b"]);
    test:assertEquals(result, 2);
}

@test:Config {
}
function testZRemRangeByLex() {
    var result = conn->zRemRangeByLex("testZRemRangeByLexKey", "alpha", "omega");
    test:assertEquals(result, 6);
}

@test:Config {
}
function testZRemRangeByRank() {
    var result = conn->zRemRangeByRank("testZRemRangeByRankKey", 1, 4);
    test:assertEquals(result, 4);
}

@test:Config {
}
function testZRemRangeByScore() {
    var result = conn->zRemRangeByScore("testZRemRangeByScoreKey", 1, 4);
    test:assertEquals(result, 4);
}

@test:Config {
}
function testZRevRange() {
    var result = conn->zRevRange("testZRangeKey", 1, 3);
    test:assertEquals(result.length(), 3); 
}

@test:Config {
}
function testZRevRangeByScore() {
    var result = conn->zRangeByScore("testZRangeKey", 2.0, 4.0);
    test:assertEquals(result.length(), 3); 
    test:assertEquals(result[0], "b"); 
    test:assertEquals(result[1], "e"); 
    test:assertEquals(result[2], "f");
}

@test:Config {
}
function testZRevRank() {
    var result = conn->zRevRank("testZRankKey", "c");
    test:assertEquals(result, 0); 
}

@test:Config {
}
function testZScore() {
    var result = conn->zScore("testZScoreKey", "a");
    test:assertEquals(result, 1.2); 
}

@test:Config {
}
function testZUnionStore() {
    var result = conn->zUnionStore("testZUnionStoreDestKey", ["testZUnionStoreKey1", "testZUnionStoreKey2"]);
    test:assertEquals(result, 6); 
}

function setupRedisSortedSetDatabase() = @java:Method{
    name: "setupSortedSetDatabase",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;
