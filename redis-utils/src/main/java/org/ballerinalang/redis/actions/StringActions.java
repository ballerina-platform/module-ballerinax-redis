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
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.BallerinaRedisDbException.REDIS_EXCEPTION_OCCURRED;

public class StringActions extends AbstractRedisAction {
    /**
     * Get a string value from redis database.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey key
     * @return value
     */
    public static BString get(HandleValue redisDataSourceHandleValue, String redisKey) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return get(redisKey, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Set a string value for a given key value.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey key
     * @param redisValue value
     */
    public static void set(HandleValue redisDataSourceHandleValue, String redisKey, String redisValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            set(redisKey, redisValue, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Append a string to a value of a given key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey key
     * @param redisValue value
     * @return size of the new string
     */
    public static BInteger append(HandleValue redisDataSourceHandleValue, String redisKey, String redisValue)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return append(redisKey, redisValue, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Count set bits in a string.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey key
     * @return bit count
     */
    public static long bitCount(HandleValue redisDataSourceHandleValue, String redisKey)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitCount(redisKey, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }
}
