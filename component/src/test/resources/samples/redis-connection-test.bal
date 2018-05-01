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

function testInitWithConnectionParam() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: { connectionPooling: true, isClusterConnection: false, ssl: false,
            startTls: false, verifyPeer: false, database: 0, connectionTimeout: 500 }
    };
    var result = conn->ping();
    conn.stop();
    return result;
}

function testPing() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->ping();
    conn.stop();
    return result;
}

function testEcho() returns (any) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: {}
    };
    var result = conn->echo("Manuri");
    conn.stop();
    return result;
}

function testConnectionRelease() returns (string) {
    endpoint redis:Client conn {
        host: REDIS_HOST,
        password: "",
        options: { connectionPooling: true }
    };
    int i = 0;
    while(i < 8) {
        _ = check conn -> ping();
        i++;
    }
    string result = check conn->ping();
    conn.stop();
    return result;
}

