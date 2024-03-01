// Copyright (c) 2024 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 LLC. licenses this file to you under the Apache License,
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

import ballerina/log;
import ballerinax/redis;

// Define the Redis client configuration
configurable string host = ?;
configurable int port = ?;

redis:ConnectionConfig redisConfig = {
    connection: {
        host,
        port
    }
};

// Initialize the Redis client
redis:Client redis = check new (redisConfig);

// Define the caching functions
function addOrUpdateCache(string key, string value) returns redis:Error? {
    // Set the value in the Redis cache
    string|redis:Error? result = redis->set(key, value);
    if result is redis:Error {
        log:printError("Error adding/updating cache: ", result);
        return result;
    }
}

function getFromCache(string key) returns string|redis:Error? {
    // Get the value from the Redis cache
    string|redis:Error? result = redis->get(key);
    if result is string {
        return result;
    } else if (result is error) {
        log:printError("Error getting from cache: ", result);
        return result;
    }
    return ();
}

public function main() {
    // Add or update cache
    redis:Error? setResult = addOrUpdateCache("key1", "value1");
    if setResult is redis:Error {
        log:printError("Error adding/updating cache: ", setResult);
        return;
    }

    // Retrieve from cache
    string|redis:Error? cachedValue = getFromCache("key1");
    if cachedValue is string {
        log:printInfo("Value retrieved from cache: " + cachedValue);
    } else if cachedValue is error {
        log:printError("Error retrieving from cache: ", cachedValue);
    }
}
