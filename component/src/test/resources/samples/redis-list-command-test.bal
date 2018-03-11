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

function testBLPop () (map) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    map result = conn.bLPop(1, ["testBLPopKey"]);
    string _ = conn.quit();
    return result;
}

function testBRPop () (map) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    map result = conn.bRPop(1, ["testBRPopKey"]);
    string _ = conn.quit();
    return result;
}

function testLIndex () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.lIndex("testLIndexKey", 0);
    string _ = conn.quit();
    return result;
}

function testLInsert () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.lInsert("testLInsertKey", true, "pivotValue", "beforePivotValue");
    string _ = conn.quit();
    return result;
}

function testLLen () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.lLen("testLLenKey");
    string _ = conn.quit();
    return result;
}

function testLPop () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.lPop("testLPopKey");
    string _ = conn.quit();
    return result;
}

function testLPush () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.lPush("testLPushKey", ["testLPushValue2", "testLPushValue3"]);
    string _ = conn.quit();
    return result;
}

function testLPushX () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.lPushX("testLPushXKey", ["testLPushXValue2", "testLPushXValue3"]);
    string _ = conn.quit();
    return result;
}

function testLRange () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.lRange("testLRangeKey", 1, 3);
    string _ = conn.quit();
    return result;
}

function testLRem () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.lRem("testLRemKey", 0, "toBeRemovedValue");
    string _ = conn.quit();
    return result;
}

function testLSet () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.lSet("testLSetKey", 1, "testLSetValue2New");
    string _ = conn.quit();
    return result;

}

function testLTrim () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.lTrim("testLTrimKey", 1, -1);
    string _ = conn.quit();
    return result;
}

function testRPop () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.rPop("testRPopKey");
    string _ = conn.quit();
    return result;
}

function testRPopLPush () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.rPopLPush("testRPopLPushKey1", "testRPopLPushKey2");
    string _ = conn.quit();
    return result;
}

function testRPush () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.rPush("testRPushKey", ["testRPushValue2", "testRPushValue3"]);
    string _ = conn.quit();
    return result;
}

function testRPushX () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.rPushX("testRPushXKey", ["testRPushXValue2", "testRPushXValue3"]);
    string _ = conn.quit();
    return result;

}
