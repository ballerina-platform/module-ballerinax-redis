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
import org.ballerinalang.jvm.values.MapValue;
import org.ballerinalang.jvm.values.api.BArray;
import org.ballerinalang.jvm.values.api.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BValueArray;
import org.ballerinalang.redis.RedisDataSource;

import java.util.Map;

import static org.ballerinalang.redis.BallerinaRedisDbException.REDIS_EXCEPTION_OCCURRED;

public class HashActions extends AbstractRedisAction {

    public static long hDel(HandleValue redisDataSourceHandleValue, String key, BArray fields) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hDel(key, redisDataSource, createStringArrayFromBArray(fields)).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static boolean hExists(HandleValue redisDataSourceHandleValue, String key, String field) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hExists(key, field, redisDataSource).booleanValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static BString hGet(HandleValue redisDataSourceHandleValue, String key, String field) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hGet(key, field, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

//    public static BMap hGetAll(HandleValue redisDataSourceHandleValue, String key) {
//        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
//        try {
//            return hGetAll(key, redisDataSource);
//        } catch (Throwable e) {
//            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
//        }
//    }

    public static long hIncrBy(HandleValue redisDataSourceHandleValue, String key, String field, int amount) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hIncrBy(key, field, amount, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static double hIncrByFloat(HandleValue redisDataSourceHandleValue, String key, String field, double amount) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hIncrByFloat(key, field, amount, redisDataSource).floatValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long hLen(HandleValue redisDataSourceHandleValue, String key) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hLen(key, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static BValueArray hKeys(HandleValue redisDataSourceHandleValue, String key) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hKeys(key, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static boolean hSet(HandleValue redisDataSourceHandleValue, String key, String field, String value) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hSet(key, field, value, redisDataSource).booleanValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }
}
