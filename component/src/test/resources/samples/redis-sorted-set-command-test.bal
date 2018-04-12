// Copyright (c) 2018 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import ballerina/redis;

@final string REDIS_HOST = "localhost";

function testZAdd () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    map valueScoreMap = {testZAddValue2:2f};
    var result = conn -> zAdd("testZAddKey", valueScoreMap);
    _ = conn -> quit();
    return result;
}

function testZCard () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zCard("testZCardKey");
    _ = conn -> quit();
    return result;
}

function testZCount () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zCount("testZCountKey", 2f, 5f);
    _ = conn -> quit();
    return result;
}

function testZIncrBy () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zIncrBy("testZIncrByKey", 1f, "testZIncrByValue1");
    _ = conn -> quit();
    return result;
}

function testZInterStore () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zInterStore("testZInterStoreDestKey", ["testZInterStoreKey1", "testZInterStoreKey2"]);
    _ = conn -> quit();
    return result;
}

function testZLexCount () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zLexCount("testZLexCountKey", "b", "f");
    _ = conn -> quit();
    return result;
}

function testZRange () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRange("testZRangeKey", 1, 3);
    _ = conn -> quit();
    return result;
}

function testZRangeByLex () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRangeByLex("testZRangeByLexKey", "aaa", "g");
    _ = conn -> quit();
    return result;
}

function testZRangeByScore () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRangeByScore("testZRangeKey", 2, 4);
    _ = conn -> quit();
    return result;
}

function testZRank () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRank("testZRankKey", "a");
    _ = conn -> quit();
    return result;
}

function testZRem () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRem("testZRemKey", ["a", "b"]);
    _ = conn -> quit();
    return result;
}

function testZRemRangeByLex () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRemRangeByLex("testZRemRangeByLexKey", "alpha", "omega");
    _ = conn -> quit();
    return result;
}

function testZRemRangeByRank () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRemRangeByRank("testZRemRangeByRankKey", 1, 4);
    _ = conn -> quit();
    return result;
}

function testZRemRangeByScore () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRemRangeByScore("testZRemRangeByScoreKey", 2, 5);
    _ = conn -> quit();
    return result;
}

function testZRevRange () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRevRange("testZRangeKey", 1, 3);
    _ = conn -> quit();
    return result;
}

function testZRevRangeByLex () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRevRangeByLex("testZRangeByLexKey", "aaaa", "g");
    _ = conn -> quit();
    return result;
}

function testZRevRangeByScore () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRangeByScore("testZRangeKey", 2, 4);
    _ = conn -> quit();
    return result;
}

function testZRevRank () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zRevRank("testZRankKey", "c");
    _ = conn -> quit();
    return result;
}

function testZScore () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zScore("testZScoreKey", "a");
    _ = conn -> quit();
    return result;
}

function testZUnionStore () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> zUnionStore("testZUnionStoreDestKey", ["testZUnionStoreKey1", "testZUnionStoreKey2"]);
    _ = conn -> quit();
    return result;
}
