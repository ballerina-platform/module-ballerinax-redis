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

function testSet () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.set("Name", "Manuri");
    string _ = conn.quit();
    return result;
}

function testGet () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.get("GetTestKey");
    string _ = conn.quit();
    return result;
}

function testAppend () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.append("AppendTestKey", "SomeValue");
    string _ = conn.quit();
    return result;
}

function testBitCount () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.bitCount("BitCountTestKey");
    string _ = conn.quit();
    return result;
}

function tesBitOpAnd () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] keys = ["testBitOpKey1", "testBitOpKey2"];
    int result = conn.bitOpAnd("tesBitOpAndDest", keys);
    string _ = conn.quit();
    return result;
}

function tesBitOpOr () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] keys = ["testBitOpKey1", "testBitOpKey2"];
    int result = conn.bitOpOr("tesBitOpOrDest", keys);
    string _ = conn.quit();
    return result;
}

function testSetBit () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.setBit("testSetBitKey", 1, 2);
    string _ = conn.quit();
    return result;
}

function tesBitOpNot () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.bitOpNot("tesBitOpNotDest", "testBitOpKey1");
    string _ = conn.quit();
    return result;
}

function tesBitOpXor () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.bitOpXor("tesBitOpXorDest", ["testBitOpKey1", "testBitOpKey2"]);
    string _ = conn.quit();
    return result;
}

function testDecr () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.decr("testDecrKey");
    string _ = conn.quit();
    return result;
}

function testDecrBy () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.decrBy("testDecrByKey", 3);
    string _ = conn.quit();
    return result;
}

function testGetBit () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.getBit("testGetBitKey", 4);
    string _ = conn.quit();
    return result;
}

function testGetRange () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.getRange("testGetRangekey", 2, 5);
    string _ = conn.quit();
    return result;
}

function testGetSet () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.getSet("testGetSetKey", "testGetSetNewValue");
    string _ = conn.quit();
    return result;
}

function testIncr () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.incr("testIncrKey");
    string _ = conn.quit();
    return result;
}


function testIncrBy () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.incrBy("testIncrByKey", 2);
    string _ = conn.quit();
    return result;
}

function testIncrByFloat () (float) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    float result = conn.incrByFloat("testIncrByKey", 0.2f);
    string _ = conn.quit();
    return result;
}

function testMGet () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.mGet(["testMGetKey1", "testMGetKey2", "testMGetKey3"]);
    string _ = conn.quit();
    return result;
}

function testMSet () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    map keyValueMap = {testMSetKey1:"testMSetValue1", testMSetKey2:"testMSetValue2", testMSetKey3:"testMSetValue3"};
    string result = conn.mSet(keyValueMap);
    string _ = conn.quit();
    return result;
}

function testMSetNx () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    map keyValueMap = {testMSetNxKey1:"testMSetNxNewValue1", testMSetNxKey2:"testMSetNxValue2", testMSetNxKey3:
                                                                                                "testMSetNxValue3"};
    boolean result = conn.mSetNx(keyValueMap);
    string _ = conn.quit();
    return result;
}

function testPSetEx () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.pSetEx("testPSetExKey", "testPSetExNewValue", 5000);
    string _ = conn.quit();
    return result;
}

function testSetEx () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.setEx("testSetExKey", "testSetExNewValue", 5);
    string _ = conn.quit();
    return result;
}

function testSetNx () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.setNx("testSetNxKey", "testSetNxValue");
    string _ = conn.quit();
    return result;
}

function testSetRange () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.setRange("testSetRangeKey", 2, "!!!");
    string _ = conn.quit();
    return result;
}

function testStrln () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.strln("testStrlnKey");
    string _ = conn.quit();
    return result;
}



