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
import ballerinax/redis;

// Initialize the Redis client configuration.
redis:ConnectionConfig redisConfig = {
    connection: {
        host: "localhost",
        port: 6379
    }
};

// Create the Redis client.
final redis:Client redis = check new (redisConfig);

# The user profile record.
#
# + userId - The user ID
# + name - The user's name
# + email - The user's email
public type UserProfile record {
    string userId;
    string name;
    string email;
};

# A service that provides user profile management.
service /userProfile on new http:Listener(8080) {

    # Resource to get a user profile.
    #
    # + userId - The user ID
    # + return - The user profile if found, or an error if not found
    isolated resource function get .(string userId) returns UserProfile|error {
        string? cachedProfile = check redis->get(userId);
        if (cachedProfile is string) {
            // Extend the cache expiry time
            boolean _ = check redis->expire(userId, 3600);
            // Use the cached profile
            UserProfile userProfile = check (check cachedProfile.fromBalString()).cloneWithType();
            return userProfile;
        } else {
            // Simulate database fetch
            json userProfile = {"userId": userId, "name": "John Doe", "email": "john@example.com"};
            // Save to cache
            string _ = check redis->setEx(userId, userProfile.toJsonString(), 3600);
            return check userProfile.cloneWithType();
        }
    }

    # Resource to save a new user profile.
    #
    # + request - The HTTP request
    # + return - An error if the user profile data is invalid
    isolated resource function post .(http:Request request) returns error? {
        UserProfile|error userProfile = (check request.getJsonPayload()).cloneWithType();
        if (userProfile is UserProfile) {
            string userId = userProfile.userId;
            // Simulate saving to database and cache
            string _ = check redis->setEx("userProfile:" + userId, userProfile.toJsonString(), 3600);
        } else {
            return error("Invalid user profile data");
        }
    }

    # Resource to update an existing user profile.
    #
    # + request - The HTTP request
    # + return - An error if the user profile data is invalid
    isolated resource function put .(http:Request request) returns error? {
        UserProfile|error userProfile = (check request.getJsonPayload()).cloneWithType();
        if (userProfile is UserProfile) {
            // Simulate update in database and cache
            string _ = check redis->setEx(userProfile.userId, userProfile.toJsonString(), 3600);
        } else {
            return error("Invalid user profile data");
        }
    }
}
