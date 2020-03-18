// Copyright (c) 2020 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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

import ballerina/test;
import ballerina/io;
import ballerinax/java;

ClientEndpointConfiguration redisConfig = {
    host: "localhost",
    password: "",
    options: {
        connectionPooling: true,
        isClusterConnection: false,
        ssl: false,
        startTls: false,
        verifyPeer: false,
        connectionTimeout: 500
    }
};

var stringResult = initRedisServer();
Client conn = check new (redisConfig);

@test:BeforeSuite
public function initDb() {
    setupRedisStringDatabase();
    setupRedisKeyDatabase();
    setupRedisListDatabase();
    setupRedisHashDatabase();
    setupRedisSetDatabase();
    setupRedisSortedSetDatabase();
}

@test:AfterSuite
public function stopServer() {
    var result = stopRedisServer();
    if (result is error) {
        test:assertFail(io:sprintf("Error from Connector: %s - %s", result.reason(),
        <string>result.detail()["message"]));
    }
}

function initRedisServer() returns handle | error = @java:Method {
    name: "initServer",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;

function stopRedisServer() returns handle | error = @java:Method {
    name: "stopServer",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;

function getValue(handle key) returns handle = @java:Method {
    name: "getValue",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;

function sisMember(handle key, handle value) returns boolean = @java:Method {
    name: "sisMember",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;

function hexists(handle key, handle 'field) returns boolean = @java:Method {
    name: "hexists",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;

function hget(handle key, handle 'field) returns handle = @java:Method {
    name: "hget",
    class: "org.ballerinalang.redis.utils.RedisDbUtils"
} external;
