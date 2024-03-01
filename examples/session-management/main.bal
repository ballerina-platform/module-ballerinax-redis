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

import ballerina/http;
import ballerina/log;
import ballerina/uuid;
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

// Define the HTTP service
service / on new http:Listener(9090) {

    // Resource to handle login and session creation
    resource function post loginAndCreateSession(http:Request req) returns string|http:Unauthorized|error {
        // Extract username and password from the request
        string username = (check req.getFormParams()).get("username");
        string password = (check req.getFormParams()).get("password");

        // Validate username and password (dummy implementation)
        if (username == "user" && password == "pass") {
            // Generate a unique session ID
            string sessionId = uuid:createType1AsString();

            // Store session ID in Redis with a TTL of 1 hour (3600 seconds)
            string|redis:Error result = redis->setEx(username, sessionId, 3600);
            if result is redis:Error {
                log:printError("Error creating session: ", result);
                return result;
            }

            // Return session ID in the response
            return sessionId;

        } else {
            // Return unauthorized if credentials are invalid
            return http:UNAUTHORIZED;
        }
    }

    // Resource to handle logout and session deletion
    resource function post logoutAndDeleteSession(http:Request req) returns error? {
        // Extract username from the request
        string username = (check req.getFormParams()).get("username");

        // Delete session from Redis
        int|redis:Error result = redis->del([username]);
        if result is redis:Error {
            log:printError("Error deleting session: ", result);
            return result;
        }
    }
}
