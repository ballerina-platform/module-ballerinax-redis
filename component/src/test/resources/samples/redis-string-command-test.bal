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

import wso2/redis;

@final string REDIS_HOST = "localhost";

function testSet() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->setVal("Name", "Manuri");
    conn.stop();
    return result;
}

function testGet() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->get("GetTestKey");
    conn.stop();
    return result;
}

function testGetNilOutput() returns (string?|error) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->get("GetTestKeyNonExistent");
    conn.stop();
    match result {
        () => return ();
        string s => return s;
        error e => return e;
    }
}

function testAppend() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->append("AppendTestKey", "SomeValue");
    conn.stop();
    return result;
}

function testBitCount() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->bitCount("BitCountTestKey");
    conn.stop();
    return result;
}

function tesBitOpAnd() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    string[] keys = ["testBitOpKey1", "testBitOpKey2"];
    var result = conn->bitOpAnd("tesBitOpAndDest", keys);
    conn.stop();
    return result;
}

function tesBitOpOr() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    string[] keys = ["testBitOpKey1", "testBitOpKey2"];
    var result = conn->bitOpOr("tesBitOpOrDest", keys);
    conn.stop();
    return result;
}

function testSetBit() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->setBit("testSetBitKey", 1, 2);
    conn.stop();
    return result;
}

function tesBitOpNot() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->bitOpNot("tesBitOpNotDest", "testBitOpKey1");
    conn.stop();
    return result;
}

function tesBitOpXor() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->bitOpXor("tesBitOpXorDest", ["testBitOpKey1", "testBitOpKey2"]);
    conn.stop();
    return result;
}

function testDecr() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->decr("testDecrKey");
    conn.stop();
    return result;
}

function testDecrBy() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->decrBy("testDecrByKey", 3);
    conn.stop();
    return result;
}

function testGetBit() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->getBit("testGetBitKey", 4);
    conn.stop();
    return result;
}

function testGetRange() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->getRange("testGetRangekey", 2, 5);
    conn.stop();
    return result;
}

function testGetSet() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->getSet("testGetSetKey", "testGetSetNewValue");
    conn.stop();
    return result;
}

function testGetSetNilOutput() returns (string?|error) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->getSet("testGetSetKeyNonExistent", "testGetSetNewValue");
    conn.stop();
    match result {
        () => return ();
        string s => return s;
        error e => return e;
    }
}

function testIncr() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->incr("testIncrKey");
    conn.stop();
    return result;
}


function testIncrBy() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->incrBy("testIncrByKey", 2);
    conn.stop();
    return result;
}

function testIncrByFloat() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->incrByFloat("testIncrByKey", 0.2);
    conn.stop();
    return result;
}

function testMGet() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->mGet(["testMGetKey1", "testMGetKey2", "testMGetKey3"]);
    conn.stop();
    return result;
}

function testMSet() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    map keyValueMap = { testMSetKey1: "testMSetValue1", testMSetKey2: "testMSetValue2", testMSetKey3: "testMSetValue3" }
    ;
    var result = conn->mSet(keyValueMap);
    conn.stop();
    return result;
}

function testMSetNx() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    map keyValueMap = { testMSetNxKey1: "testMSetNxNewValue1", testMSetNxKey2: "testMSetNxValue2", testMSetNxKey3:
    "testMSetNxValue3" };
    var result = conn->mSetNx(keyValueMap);
    conn.stop();
    return result;
}

function testPSetEx() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->pSetEx("testPSetExKey", "testPSetExNewValue", 5000);
    conn.stop();
    return result;
}

function testSetEx() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->setEx("testSetExKey", "testSetExNewValue", 5);
    conn.stop();
    return result;
}

function testSetNx() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->setNx("testSetNxKey", "testSetNxValue");
    conn.stop();
    return result;
}

function testSetRange() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->setRange("testSetRangeKey", 2, "!!!");
    conn.stop();
    return result;
}

function testStrln() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->strln("testStrlnKey");
    conn.stop();
    return result;
}



