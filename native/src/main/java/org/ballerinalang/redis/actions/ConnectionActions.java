/*
 * Copyright (c) 2020, WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http:www.apache.orglicensesLICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.redis.actions;

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;

/**
 * Redis connection actions.
 */
public class ConnectionActions extends AbstractRedisAction {

    /**
     * Ping the redis database server.
     *
     * @param redisClient Client from the Ballerina redis client
     */
    public static Object ping(BObject redisClient) {
        RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
        try {
            return StringUtils.fromString(ping(redisDataSource));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Authenticate to the server.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param password    The password
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object auth(BObject redisClient, BString password) {
        RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
        try {
            return StringUtils.fromString(auth(password.getValue(), redisDataSource));
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
        RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
        try {
            return StringUtils.fromString(echo(message.getValue(), redisDataSource));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Close a redis server connection.
     *
     * @param redisClient Client from the Ballerina redis client
     */
    public static void close(BObject redisClient) {
        RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
        close(redisDataSource);
    }
}
