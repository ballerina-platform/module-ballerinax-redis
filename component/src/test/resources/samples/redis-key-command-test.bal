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

function testDel () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.del(["testDelKey1", "testDelKey2", "testDelKey3"]);
    string _ = conn.quit();
    return result;
}

function testExists () (int, int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.exists(["testExistsKey"]);
    int result2 = conn.exists(["nonExistentKey"]);
    string _ = conn.quit();
    return result, result2;
}

function testExpire () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.expire("testExpireKey", 3);
    string _ = conn.quit();
    return result;
}

function testKeys () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.keys("testKeysKey*");
    string _ = conn.quit();
    return result;
}

function testMove () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.move("testMoveKey", 1);
    string _ = conn.quit();
    return result;
}

function testPersist () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.persist("testPersistKey");
    string _ = conn.quit();
    return result;
}

function testPExpire () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.pExpire("testPExpireKey", 3000);
    string _ = conn.quit();
    return result;
}

function testPTtl () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.pTtl("testPTtlKey");
    string _ = conn.quit();
    return result;
}

function testRandomKey () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.randomKey();
    string _ = conn.quit();
    return result;
}

function testRename () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.rename("testRenameKey", "testRenameKey1");
    string _ = conn.quit();
    return result;
}

function testRenameNx () (boolean, boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.renameNx("testRenameNxKey", "testRenameNxKeyRenamed");
    boolean result2 = conn.renameNx("testRenameNxKey1", "testRenameNxKeyExisting");
    string _ = conn.quit();
    return result, result2;
}

function testSort () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.sort("testSortKey");
    string _ = conn.quit();
    return result;
}

function testTtl () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.pTtl("testTtlKey");
    string _ = conn.quit();
    return result;
}

function testType () (string) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string result = conn.redisType("testTypeKey");
    string _ = conn.quit();
    return result;
}

