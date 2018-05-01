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

function testSAdd() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sAdd("testSAddKey", ["testSAddValue3", "testSAddValue4", "testSAddValue5"]);
    conn.stop();
    return result;
}

function testSDiff() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sDiff(["testSDiffKey1", "testSDiffKey2"]);
    conn.stop();
    return result;
}

function testSDiffStore() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sDiffStore("testSDiffStoreDestKey", ["testSDiffKey1", "testSDiffKey2"]);
    conn.stop();
    return result;
}

function testSInter() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sInter(["testSInterKey1", "testSInterKey2"]);
    conn.stop();
    return result;
}

function testSInterStore() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sInterStore("testSInterDestKey", ["testSInterKey1", "testSInterKey2"]);
    conn.stop();
    return result;
}

function testSIsMember() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sIsMember("testSIsMemberKey", "testSIsMemberValue");
    conn.stop();
    return result;
}

function testSMembers() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sMembers("testSMembersKey");
    conn.stop();
    return result;
}

function testSPop() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sPop("testSPopKey", 2);
    conn.stop();
    return result;
}

function testSPopNilOutput() returns (string[]?|error) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sPop("testSPopKeyNonExistent", 2);
    conn.stop();
    match result {
        () => return ();
        string[] s => return s;
        error e => return e;
    }
}

function testSRandMember() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sRandMember("testSRandMemberKey", 2);
    conn.stop();
    return result;
}

function testSRandMemberNilOutput() returns (string[]?|error) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sRandMember("testSRandMemberKeyNonExistent", 2);
    conn.stop();
    match result {
        () => return ();
        string[] s => return s;
        error e => return e;
    }
}

function testSRem() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sRem("testSRemKey", ["testSRemValue1", "testSRemValue3"]);
    conn.stop();
    return result;
}

function testSUnion() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sUnion(["testUnionKey1", "testUnionKey2"]);
    conn.stop();
    return result;
}

function testSUnionStore() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->sUnionStore("testSUnionStoreDestKey", ["testUnionKey1", "testUnionKey2"]);
    conn.stop();
    return result;
}
