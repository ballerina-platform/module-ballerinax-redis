/*
 * Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.redis;

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.redis.connection.RedisConnectionCommandExecutor;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;
import static org.ballerinalang.redis.utils.RedisUtils.getConnection;

/**
 * Ballerina native util implementation for redis connection commands.
 */
public class ConnectionCommands {

    /**
     * Authenticate to the server.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param password    The password
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object auth(BObject redisClient, BString password) {
        try {
            RedisConnectionCommandExecutor executor = getConnection(redisClient).getConnectionCommandExecutor();
            String response = executor.auth(password.getValue());
            return StringUtils.fromString(response);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Ping the redis database server.
     *
     * @param redisClient Client from the Ballerina redis client
     */
    public static Object ping(BObject redisClient) {
        try {
            RedisConnectionCommandExecutor executor = getConnection(redisClient).getConnectionCommandExecutor();
            String response = executor.ping();
            return StringUtils.fromString(response);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Echo the given string.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param message     The message to be echo-ed
     * @return The message itself if the operation was successful
     */
    public static Object echo(BObject redisClient, BString message) {
        try {
            RedisConnectionCommandExecutor executor = getConnection(redisClient).getConnectionCommandExecutor();
            String response = executor.echo(message.getValue());
            return StringUtils.fromString(response);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Close a redis server connection.
     *
     * @param redisClient Client from the Ballerina redis client
     */
    public static Object close(BObject redisClient) {
        try {
            RedisConnectionCommandExecutor executor = getConnection(redisClient).getConnectionCommandExecutor();
            executor.close();
            return null;
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
