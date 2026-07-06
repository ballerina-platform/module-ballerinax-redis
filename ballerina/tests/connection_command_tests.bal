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

@test:Config {
    groups: ["standalone", "cluster"]
}
function testPing() returns error? {
    string result = check redis->ping();
    test:assertEquals(result, "PONG");
}

@test:Config {
    groups: ["standalone", "cluster"]
}
function testEcho() returns error? {
    string result = check redis->echo("Hello");
    test:assertEquals(result, "Hello");
}

// `flushDb` is run against a dedicated database index (not the shared `redis` client's database) so that the
// fixture data set up in `init.bal` and relied on by the rest of the test suite is not wiped out. A key is also
// set on the shared `redis` client's (default) database to verify that `flushDb` only clears the selected
// database and leaves other databases untouched.

@test:Config {
    groups: ["standalone"]
}
function testFlushDb() returns error? {
    if clusterMode {
        return;
    }

    Client flushDbClient = check new (connection = {host: "localhost", port: 6379, options: {database: 14}});

    string setResult = check flushDbClient->set("testFlushDbKey", "testFlushDbValue");
    test:assertEquals(setResult, "OK");

    string setOtherDbResult = check redis->set("testFlushDbIsolationKey", "testFlushDbIsolationValue");
    test:assertEquals(setOtherDbResult, "OK");

    check flushDbClient->flushDb();

    int existsResult = check flushDbClient->exists(["testFlushDbKey"]);
    test:assertEquals(existsResult, 0);

    int otherDbExistsResult = check redis->exists(["testFlushDbIsolationKey"]);
    test:assertEquals(otherDbExistsResult, 1);

    int deleteResult = check redis->del(["testFlushDbIsolationKey"]);
    test:assertEquals(deleteResult, 1);

    check flushDbClient.close();
}

// `flushAll` clears every logical database on the target server, so it is run against the isolated
// `redis-standalone-ssl` service (port 6380) instead of the shared `redis` client's server, to avoid wiping the
// fixture data that the rest of the test suite depends on.

@test:Config {
    groups: ["standalone"]
}
function testFlushAll() returns error? {
    if clusterMode {
        return;
    }

    Client flushAllClient = check new (
        connection = {host: "localhost", port: 6380},
        secureSocket = {verifyMode: NONE}
    );

    string setResult = check flushAllClient->set("testFlushAllKey", "testFlushAllValue");
    test:assertEquals(setResult, "OK");

    check flushAllClient->flushAll();

    int existsResult = check flushAllClient->exists(["testFlushAllKey"]);
    test:assertEquals(existsResult, 0);

    check flushAllClient.close();
}
