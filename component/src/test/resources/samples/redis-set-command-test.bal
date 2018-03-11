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

function testSAdd () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.sAdd("testSAddKey", ["testSAddValue3", "testSAddValue4", "testSAddValue5"]);
    string _ = conn.quit();
    return result;
}

function testSDiff () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.sDiff(["testSDiffKey1", "testSDiffKey2"]);
    string _ = conn.quit();
    return result;
}

function testSDiffStore () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.sDiffStore("testSDiffStoreDestKey", ["testSDiffKey1", "testSDiffKey2"]);
    string _ = conn.quit();
    return result;
}

function testSInter () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.sInter(["testSInterKey1", "testSInterKey2"]);
    string _ = conn.quit();
    return result;
}

function testSInterStore () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.sInterStore("testSInterDestKey", ["testSInterKey1", "testSInterKey2"]);
    string _ = conn.quit();
    return result;
}

function testSIsMember () (boolean) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    boolean result = conn.sIsMember("testSIsMemberKey", "testSIsMemberValue");
    string _ = conn.quit();
    return result;
}

function testSMembers () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.sMembers("testSMembersKey");
    string _ = conn.quit();
    return result;
}

function testSPop () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.sPop("testSPopKey", 2);
    string _ = conn.quit();
    return result;
}

function testSRandMember () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.sRandMember("testSRandMemberKey", 2);
    string _ = conn.quit();
    return result;
}

function testSRem () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.sRem("testSRemKey", ["testSRemValue1", "testSRemValue3"]);
    string _ = conn.quit();
    return result;
}

function testSUnion () (string[]) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    string[] result = conn.sUnion(["testUnionKey1", "testUnionKey2"]);
    string _ = conn.quit();
    return result;
}

function testSUnionStore () (int) {
    endpoint<redis:ClientConnector> conn {
        create redis:ClientConnector(REDIS_HOST, "", {});
    }
    int result = conn.sUnionStore("testSUnionStoreDestKey", ["testUnionKey1", "testUnionKey2"]);
    string _ = conn.quit();
    return result;
}
