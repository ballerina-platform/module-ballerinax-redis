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

function testHDel () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.hDel("testHDelKey", ["testHDelField1", "testHDelField2", "testHDelField3"]);
    string _ = conn.quit();
    return result;
}

function testHGet () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.hGet("testHGetKey", "testHGetField1");
    string _ = conn.quit();
    return result;
}

function testHExists () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.hExists("testHExistsKey", "testHExistsField1");
    string _ = conn.quit();
    return result;
}

function testHGetAll () (map) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    map result = conn.hGetAll("testHGetAllKey");
    string _ = conn.quit();
    return result;
}

function testHIncrBy () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.hIncrBy("testHIncrByKey", "testHIncrByField1", 2);
    string _ = conn.quit();
    return result;
}

function testHIncrByFloat () (float) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    float result = conn.hIncrByFloat("testHIncrByFloatKey", "testHIncrByFloatField1", 0.2f);
    string _ = conn.quit();
    return result;
}

function testHKeys () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.hKeys("testHKeysKey");
    string _ = conn.quit();
    return result;
}

function testHLen () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.hLen("testHLenKey");
    string _ = conn.quit();
    return result;
}

function testHMGet () (map) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    map result = conn.hMGet("testHMGetKey", ["testHMGetField1", "testHMGetField2", "testHMGetField3"]);
    string _ = conn.quit();
    return result;
}

function testHMSet () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    map fieldValueMap = {testHMSetField1:"testHMSetValue1", testHMSetField2:"testHMSetValue2"};
    string result = conn.hMSet("testHMSetKey", fieldValueMap);
    string _ = conn.quit();
    return result;
}

function testHSet () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.hSet("testHSetKey", "testHSetField1", "testHSetValue1");
    string _ = conn.quit();
    return result;
}

function testHSetNx () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.hSet("testHSetNxKey", "testHSetNxField1", "testHSetNxValue1");
    string _ = conn.quit();
    return result;
}

function testHStrln () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.hStrln("testHStrlnKey", "testHStrlnField1");
    string _ = conn.quit();
    return result;
}

function testHVals () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.hVals("testHValsKey");
    string _ = conn.quit();
    return result;
}
