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

function testBLPop() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->bLPop(1, ["testBLPopKey"]);
    conn.stop();
    return result;
}

function testBLPopNilOutput() returns (map?|error) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->bLPop(1, ["testBLPopKeyNonExistent"]);
    conn.stop();
    match result {
        () => return ();
        map m => return m;
        error e => return e;
    }
}

function testBRPop() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->bRPop(1, ["testBRPopKey"]);
    conn.stop();
    return result;
}

function testBRPopNilOutput() returns (map?|error) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->bRPop(1, ["testBRPopKeyNonExistent"]);
    conn.stop();
    match result {
        () => return ();
        map m => return m;
        error e => return e;
    }
}

function testLIndex() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lIndex("testLIndexKey", 0);
    conn.stop();
    return result;
}

function testLInsert() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lInsert("testLInsertKey", true, "pivotValue", "beforePivotValue");
    conn.stop();
    return result;
}

function testLLen() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lLen("testLLenKey");
    conn.stop();
    return result;
}

function testLPop() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lPop("testLPopKey");
    conn.stop();
    return result;
}

function testLPopNilOutput() returns (string?|error) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lPop("testLPopKeyNonExistent");
    conn.stop();
    match result {
        () => return ();
        string s => return s;
        error e => return e;
    }
}

function testLPush() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lPush("testLPushKey", ["testLPushValue2", "testLPushValue3"]);
    conn.stop();
    return result;
}

function testLPushX() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lPushX("testLPushXKey", ["testLPushXValue2", "testLPushXValue3"]);
    conn.stop();
    return result;
}

function testLRange() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lRange("testLRangeKey", 1, 3);
    conn.stop();
    return result;
}

function testLRem() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lRem("testLRemKey", 0, "toBeRemovedValue");
    conn.stop();
    return result;
}

function testLSet() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lSet("testLSetKey", 1, "testLSetValue2New");
    conn.stop();
    return result;

}

function testLTrim() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->lTrim("testLTrimKey", 1, -1);
    conn.stop();
    return result;
}

function testRPop() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->rPop("testRPopKey");
    conn.stop();
    return result;
}

function testRPopNilOutput() returns (string?|error) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->rPop("testRPopKeyNonExistent");
    conn.stop();
    match result {
        () => return ();
        string s => return s;
        error e => return e;
    }
}

function testRPopLPush() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->rPopLPush("testRPopLPushKey1", "testRPopLPushKey2");
    conn.stop();
    return result;
}

function testRPush() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->rPush("testRPushKey", ["testRPushValue2", "testRPushValue3"]);
    conn.stop();
    return result;
}

function testRPushX() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->rPushX("testRPushXKey", ["testRPushXValue2", "testRPushXValue3"]);
    conn.stop();
    return result;

}
