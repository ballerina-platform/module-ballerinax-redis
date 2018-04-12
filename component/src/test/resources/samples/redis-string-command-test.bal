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

function testSet () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> setVal("Name", "Manuri");
    _ = conn -> quit();
    return result;
}

function testGet () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> get("GetTestKey");
    _ = conn -> quit();
    return result;
}

function testAppend () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> append("AppendTestKey", "SomeValue");
    _ = conn -> quit();
    return result;
}

function testBitCount () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> bitCount("BitCountTestKey");
    _ = conn -> quit();
    return result;
}

function tesBitOpAnd () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    string[] keys = ["testBitOpKey1", "testBitOpKey2"];
    var result = conn -> bitOpAnd("tesBitOpAndDest", keys);
    _ = conn -> quit();
    return result;
}

function tesBitOpOr () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    string[] keys = ["testBitOpKey1", "testBitOpKey2"];
    var result = conn -> bitOpOr("tesBitOpOrDest", keys);
    _ = conn -> quit();
    return result;
}

function testSetBit () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> setBit("testSetBitKey", 1, 2);
    _ = conn -> quit();
    return result;
}

function tesBitOpNot () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> bitOpNot("tesBitOpNotDest", "testBitOpKey1");
    _ = conn -> quit();
    return result;
}

function tesBitOpXor () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> bitOpXor("tesBitOpXorDest", ["testBitOpKey1", "testBitOpKey2"]);
    _ = conn -> quit();
    return result;
}

function testDecr () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> decr("testDecrKey");
    _ = conn -> quit();
    return result;
}

function testDecrBy () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> decrBy("testDecrByKey", 3);
    _ = conn -> quit();
    return result;
}

function testGetBit () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> getBit("testGetBitKey", 4);
    _ = conn -> quit();
    return result;
}

function testGetRange () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> getRange("testGetRangekey", 2, 5);
    _ = conn -> quit();
    return result;
}

function testGetSet () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> getSet("testGetSetKey", "testGetSetNewValue");
    _ = conn -> quit();
    return result;
}

function testIncr () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> incr("testIncrKey");
    _ = conn -> quit();
    return result;
}


function testIncrBy () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> incrBy("testIncrByKey", 2);
    _ = conn -> quit();
    return result;
}

function testIncrByFloat () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> incrByFloat("testIncrByKey", 0.2f);
    _ = conn -> quit();
    return result;
}

function testMGet () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> mGet(["testMGetKey1", "testMGetKey2", "testMGetKey3"]);
    _ = conn -> quit();
    return result;
}

function testMSet () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    map keyValueMap = {testMSetKey1:"testMSetValue1", testMSetKey2:"testMSetValue2", testMSetKey3:"testMSetValue3"};
    var result = conn -> mSet(keyValueMap);
    _ = conn -> quit();
    return result;
}

function testMSetNx () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    map keyValueMap = {testMSetNxKey1:"testMSetNxNewValue1", testMSetNxKey2:"testMSetNxValue2", testMSetNxKey3:
                                                                                                "testMSetNxValue3"};
    var result = conn -> mSetNx(keyValueMap);
    _ = conn -> quit();
    return result;
}

function testPSetEx () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> pSetEx("testPSetExKey", "testPSetExNewValue", 5000);
    _ = conn -> quit();
    return result;
}

function testSetEx () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> setEx("testSetExKey", "testSetExNewValue", 5);
    _ = conn -> quit();
    return result;
}

function testSetNx () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> setNx("testSetNxKey", "testSetNxValue");
    _ = conn -> quit();
    return result;
}

function testSetRange () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> setRange("testSetRangeKey", 2, "!!!");
    _ = conn -> quit();
    return result;
}

function testStrln () returns (any) {
    endpoint redis:Client conn {
        host:REDIS_HOST,
        password:"",
        options:{}
    };
    var result = conn -> strln("testStrlnKey");
    _ = conn -> quit();
    return result;
}



