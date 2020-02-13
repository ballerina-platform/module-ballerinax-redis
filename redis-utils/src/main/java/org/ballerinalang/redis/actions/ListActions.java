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
import org.ballerinalang.jvm.values.api.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.BallerinaRedisDbErrors.REDIS_EXCEPTION_OCCURRED;

public class ListActions extends AbstractRedisAction {

    public static Object lPushX(HandleValue redisDataSourceHandleValue, String redisKey, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lPushX(redisKey, redisDataSource, createStringArrayFromBArray(values)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object bLPop(HandleValue redisDataSourceHandleValue, int timeOut, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bLPop(timeOut, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object bRPop(HandleValue redisDataSourceHandleValue, int timeOut, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bRPop(timeOut, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lPush(HandleValue redisDataSourceHandleValue, String redisKey, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lPush(redisKey, redisDataSource, createStringArrayFromBArray(values)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lPop(HandleValue redisDataSourceHandleValue, String redisKey) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lPop(redisKey, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lIndex(HandleValue redisDataSourceHandleValue, String redisKey, int index) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lIndex(redisKey, index, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lInsert(HandleValue redisDataSourceHandleValue, String key, boolean before, String pivot,
                                      String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lInsert(key, before, pivot, value, redisDataSource).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lLen(HandleValue redisDataSourceHandleValue, String redisKey) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lLen(redisKey, redisDataSource).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lRange(HandleValue redisDataSourceHandleValue, String redisKey, int startPos, int stopPos) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lRange(redisKey, startPos, stopPos, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lRem(HandleValue redisDataSourceHandleValue, String redisKey, int count, String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lRem(redisKey, count, value, redisDataSource).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lSet(HandleValue redisDataSourceHandleValue, String redisKey, int index, String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lSet(redisKey, index, value, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object lTrim(HandleValue redisDataSourceHandleValue, String redisKey, int startPos, int stopPos) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lTrim(redisKey, startPos, startPos, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object rPop(HandleValue redisDataSourceHandleValue, String redisKey) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rPop(redisKey, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object rPopLPush(HandleValue redisDataSourceHandleValue, String src, String destination) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rPopLPush(src, destination, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object rPush(HandleValue redisDataSourceHandleValue, String key, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rPush(key, redisDataSource, createStringArrayFromBArray(values)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Object rPushX(HandleValue redisDataSourceHandleValue, String key, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rPush(key, redisDataSource, createStringArrayFromBArray(values)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }
}
