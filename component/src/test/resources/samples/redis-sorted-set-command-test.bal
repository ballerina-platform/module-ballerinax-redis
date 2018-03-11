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

import ballerina.data.redis;

const string REDIS_HOST = "localhost";

function testZAdd () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    map valueScoreMap = {testZAddValue2:2f};
    int result = conn.zAdd("testZAddKey", valueScoreMap);
    string _ = conn.quit();
    return result;
}

function testZCard () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zCard("testZCardKey");
    string _ = conn.quit();
    return result;
}

function testZCount () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zCount("testZCountKey", 2f, 5f);
    string _ = conn.quit();
    return result;
}

function testZIncrBy () (float) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    float result = conn.zIncrBy("testZIncrByKey", 1f, "testZIncrByValue1");
    string _ = conn.quit();
    return result;
}

function testZInterStore () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zInterStore("testZInterStoreDestKey", ["testZInterStoreKey1", "testZInterStoreKey2"]);
    string _ = conn.quit();
    return result;
}

function testZLexCount () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zLexCount("testZLexCountKey", "b", "f");
    string _ = conn.quit();
    return result;
}

function testZRange () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.zRange("testZRangeKey", 1, 3);
    string _ = conn.quit();
    return result;
}

function testZRangeByLex () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.zRangeByLex("testZRangeByLexKey", "aaa", "g");
    string _ = conn.quit();
    return result;
}

function testZRangeByScore () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.zRangeByScore("testZRangeKey", 2, 4);
    string _ = conn.quit();
    return result;
}

function testZRank () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zRank("testZRankKey", "a");
    string _ = conn.quit();
    return result;
}

function testZRem () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zRem("testZRemKey", ["a", "b"]);
    string _ = conn.quit();
    return result;
}

function testZRemRangeByLex () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zRemRangeByLex("testZRemRangeByLexKey", "alpha", "omega");
    string _ = conn.quit();
    return result;
}

function testZRemRangeByRank () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zRemRangeByRank("testZRemRangeByRankKey", 1, 4);
    string _ = conn.quit();
    return result;
}

function testZRemRangeByScore () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zRemRangeByScore("testZRemRangeByScoreKey", 2, 5);
    string _ = conn.quit();
    return result;
}

function testZRevRange () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.zRevRange("testZRangeKey", 1, 3);
    string _ = conn.quit();
    return result;
}

function testZRevRangeByLex () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.zRevRangeByLex("testZRangeByLexKey", "aaaa", "g");
    string _ = conn.quit();
    return result;
}

function testZRevRangeByScore () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.zRangeByScore("testZRangeKey", 2, 4);
    string _ = conn.quit();
    return result;
}

function testZRevRank () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zRevRank("testZRankKey", "c");
    string _ = conn.quit();
    return result;
}

function testZScore () (float) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    float result = conn.zScore("testZScoreKey", "a");
    string _ = conn.quit();
    return result;
}

function testZUnionStore () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.zUnionStore("testZUnionStoreDestKey", ["testZUnionStoreKey1", "testZUnionStoreKey2"]);
    string _ = conn.quit();
    return result;
}
