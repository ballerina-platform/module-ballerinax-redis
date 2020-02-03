/*
 * Copyright (c) 2018, WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
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

package org.ballerinalang.redis.actions.string;

import org.ballerinalang.jvm.values.HandleValue;
import org.ballerinalang.redis.BallerinaRedisDbException;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.actions.AbstractRedisAction;

import static org.ballerinalang.redis.Constants.REDIS_EXCEPTION_OCCURRED;

/**
 * {@code Set} Maps with "Set" operation of Redis.
 *
 * @since 0.5.0
 */
public class Set extends AbstractRedisAction {

    public static void set(HandleValue redisDataSourceHandleValue, String redisKey, String redisValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            set(redisKey, redisValue, redisDataSource);
        } catch (Throwable e) {
            throw new BallerinaRedisDbException(REDIS_EXCEPTION_OCCURRED + " " + e.getMessage());
        }
    }
}
