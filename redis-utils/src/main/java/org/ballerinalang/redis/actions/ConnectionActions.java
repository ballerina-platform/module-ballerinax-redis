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

import org.ballerinalang.jvm.BallerinaErrors;
import org.ballerinalang.jvm.values.HandleValue;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.BallerinaRedisDbErrors.REDIS_EXCEPTION_OCCURRED;

public class ConnectionActions extends AbstractRedisAction {

    /**
     * Ping the redis database server.
     *
     * @param redisDataSourceHandleValue redis datasource handleValue
     */
    public static Object ping(HandleValue redisDataSourceHandleValue) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return ping(redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Authenticate to the server.
     * 
     * @param redisDataSourceHandleValue redis datasource
     * @param password The password
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object auth(HandleValue redisDataSourceHandleValue, String password) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return auth(password, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Echo the given string.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param message The message to be echo-ed
     * @return The message itself if the operation was successful
     */
    public static Object echo(HandleValue redisDataSourceHandleValue, String message) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return echo(message, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }
}
