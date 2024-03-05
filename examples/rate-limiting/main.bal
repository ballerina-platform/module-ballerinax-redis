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
import ballerinax/redis;

// Define the Redis client configuration
configurable string host = ?;
configurable int port = ?;

redis:ConnectionConfig redisConfig = {
    connection: string `redis://${host}:${port}`
};

// Initialize the Redis client
final redis:Client redis = check new (redisConfig);

// Define the rate limiting parameters
final int windowSeconds = 60; // Time window for rate limiting in seconds
final int maxRequests = 5; // Maximum number of requests allowed within the window

// Define the HTTP service
service / on new http:Listener(9090) {

    isolated resource function get rateLimitedEndpoint(http:Request req) returns http:Response|http:TooManyRequests|error {
        // Extract API key from the request
        string apiKey = check req.getHeader("X-API-Key");

        // Check if the request is allowed based on rate limiting
        boolean|redis:Error allowed = isAllowed(apiKey);

        if allowed is boolean {
            if allowed {
                // Process the request if it's allowed
                http:Response response = new;
                response.setTextPayload("Request allowed");
                return response;
            } else {
                // Return a rate limit exceeded error if the request is not allowed
                return http:TOO_MANY_REQUESTS;
            }
        } else {
            // Return an internal server error if there was an error checking rate limiting
            return error("Error checking rate limiting");
        }
    }
}

# Function to check if a request is allowed based on rate limiting.
#
# + apiKey - API key of the request
# + return - Whether the request is allowed or an error if there was an issue
isolated function isAllowed(string apiKey) returns boolean|redis:Error {
    // Generate a unique key for the rate limiting window based on the API key
    string rateLimitKey = "ratelimit:" + apiKey;

    // Retrieve the current request count from Redis
    int|redis:Error result = redis->incr(rateLimitKey);
    if result is int {
        // If the request count exceeds the maximum allowed requests, deny the request
        if result > maxRequests {
            return false;
        }
        // Set the expiry time for the rate limiting window if it's the first request in the window
        if result == 1 {
            boolean|redis:Error expireResult = redis->expire(rateLimitKey, windowSeconds);
            if (expireResult is error) {
                log:printError("Error setting expiry for rate limit key: ", expireResult);
                return expireResult;
            }
        }
        return true;
    } else {
        log:printError("Error incrementing rate limit key: ", result);
        return result;
    }
}
