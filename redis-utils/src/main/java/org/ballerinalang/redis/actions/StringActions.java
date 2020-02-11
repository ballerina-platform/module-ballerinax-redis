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
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.BallerinaRedisDbException.REDIS_EXCEPTION_OCCURRED;

public class StringActions extends AbstractRedisAction {

    /**
     * Append a string to a value of a given key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey key
     * @param redisValue value
     * @return size of the new string
     */
    public static long append(HandleValue redisDataSourceHandleValue, String redisKey, String redisValue)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return append(redisKey, redisValue, redisDataSource).intValue();
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

    public static long bitOpAnd(HandleValue redisDataSourceHandleValue, String destination, BArray keysArray)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopAnd(destination, redisDataSource, createStringArrayFromBArray(keysArray)).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long bitOpOr(HandleValue redisDataSourceHandleValue, String destination, BArray keysArray)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopOr(destination, redisDataSource, createStringArrayFromBArray(keysArray)).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long bitOpNot(HandleValue redisDataSourceHandleValue, String destination, String key)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopNot(destination, key, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long bitOpXor(HandleValue redisDataSourceHandleValue, String destination, BArray keysArray)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopXor(destination, redisDataSource, createStringArrayFromBArray(keysArray)).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long decr(HandleValue redisDataSourceHandleValue, String redisKey)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return decr(redisKey, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long decrBy(HandleValue redisDataSourceHandleValue, String redisKey, int redisValue)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return decrBy(redisKey, redisValue, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

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

    public static long getBit(HandleValue redisDataSourceHandleValue, String redisKey, int offset)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getBit(redisKey, offset, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static BString getRange(HandleValue redisDataSourceHandleValue, String key, int startPos, int end)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getRange(key, startPos, end, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static BString getSet(HandleValue redisDataSourceHandleValue, String key, String value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getSet(key, value, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long incr(HandleValue redisDataSourceHandleValue, String redisKey)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incr(redisKey, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long incrBy(HandleValue redisDataSourceHandleValue, String redisKey, int value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incrBy(redisKey, value, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static double incrByFloat(HandleValue redisDataSourceHandleValue, String redisKey, float value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incrByFloat(redisKey, value, redisDataSource).floatValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static BMap mGet(HandleValue redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return mGet(redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static String mSet(HandleValue redisDataSourceHandleValue, BMap keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return mSet(createMapFromBMap(keys), redisDataSource).stringValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Boolean mSetNx(HandleValue redisDataSourceHandleValue, BMap keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return mSetnx(createMapFromBMap(keys), redisDataSource).booleanValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static BString pSetEx(HandleValue redisDataSourceHandleValue, String redisKey, String value, int expirationTime)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return pSetex(redisKey, value, expirationTime, redisDataSource);
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
     * @return `OK` if successful
     */
    public static BString set(HandleValue redisDataSourceHandleValue, String redisKey, String redisValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return set(redisKey, redisValue, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long setBit(HandleValue redisDataSourceHandleValue, String redisKey, int value, int offset)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setBit(redisKey, value, offset, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static BString setEx(HandleValue redisDataSourceHandleValue, String redisKey, String value, int expirationPeriodSeconds)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setEx(redisKey, value, expirationPeriodSeconds, redisDataSource);
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static Boolean setNx(HandleValue redisDataSourceHandleValue, String redisKey, String value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setNx(redisKey, value, redisDataSource).booleanValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long setRange(HandleValue redisDataSourceHandleValue, String redisKey, int offset, String value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setRange(redisKey, offset, value, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    public static long strln(HandleValue redisDataSourceHandleValue, String redisKey)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return strln(redisKey, redisDataSource).intValue();
        } catch (Throwable e) {
            throw BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }
}
