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
import org.ballerinalang.jvm.values.api.BArray;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.BallerinaRedisDbException.REDIS_EXCEPTION_OCCURRED;

public class KeyActions extends AbstractRedisAction {

    public static long del(HandleValue redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return del(redisDataSource, keys).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }
}
