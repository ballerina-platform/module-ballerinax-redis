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

package io.ballerina.lib.redis;

import io.ballerina.lib.redis.connection.RedisConnectionCommandExecutor;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;

import static io.ballerina.lib.redis.utils.ConversionUtils.createBError;
import static io.ballerina.lib.redis.utils.RedisUtils.getConnection;

/**
 * Ballerina native util implementation for redis connection commands.
 *
 * @since 2.6.0
 */
@SuppressWarnings("unused")
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

    /**
     * Retrieve information and statistics about the cluster observed by the current node. This command is
     * exclusively available in cluster mode.
     * If the connection is in a non-clustered mode, the API will return a `redis:Error`. Other errors will also be
     * appropriately handled.
     *
     * @param redisClient redis client BObject
     * @return a bulk-string-reply as a string array or, a `redis:Error` if the connection is non-clustered or
     * encounters any other exceptions.
     */
    public static Object clusterInfo(BObject redisClient) {
        try {
            RedisConnectionCommandExecutor executor = getConnection(redisClient).getConnectionCommandExecutor();
            return executor.clusterInfo();
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
