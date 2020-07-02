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
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.Constants.REDIS_EXCEPTION_OCCURRED;

/**
 * Redis string actions.
 */
public class StringActions extends AbstractRedisAction {

    /**
     * Append a value to a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key key
     * @param redisValue value
     * @return size of the new string
     */
    public static Object append(HandleValue redisDataSourceHandleValue, String key, String redisValue)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return append(key, redisValue, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Count set bits in a string.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key key
     * @return bit count
     */
    public static Object bitCount(HandleValue redisDataSourceHandleValue, String key)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitCount(key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Perform bitwise AND between strings.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination Result key of the operation
     * @param keys Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpAnd(HandleValue redisDataSourceHandleValue, String destination, BArray keys)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopAnd(destination, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Perform bitwise OR between strings.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination Result key of the operation
     * @param keys Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpOr(HandleValue redisDataSourceHandleValue, String destination, BArray keys)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopOr(destination, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Perform bitwise NOT on a string.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination Result key of the operation
     * @param key Input keys to perform AND between
     * @return The size of the string stored in the destination key
     */
    public static Object bitOpNot(HandleValue redisDataSourceHandleValue, String destination, String key)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopNot(destination, key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Perform bitwise XOR between strings.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination Result key of the operation
     * @param keys Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpXor(HandleValue redisDataSourceHandleValue, String destination, BArray keys)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopXor(destination, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Decrement the integer value of a key by one.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @return The value of the key after the decrement
     */
    public static Object decr(HandleValue redisDataSourceHandleValue, String key)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return decr(key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Decrement the integer value of a key by the given number.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param redisValue The offset
     * @return The bit value stored at offset
     */
    public static Object decrBy(HandleValue redisDataSourceHandleValue, String key, int redisValue)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return decrBy(key, redisValue, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Get a string value from redis database.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key key
     * @return value
     */
    public static Object get(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return get(key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Returns the bit value at offset in the string value stored at key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param offset The offset
     * @return The bit value stored at offset
     */
    public static Object getBit(HandleValue redisDataSourceHandleValue, String key, int offset)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getBit(key, offset, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Get a substring of the string stored at a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param startPos The starting point of the substring
     * @param end The end point of the substring
     * @return The substring
     */
    public static Object getRange(HandleValue redisDataSourceHandleValue, String key, int startPos, int end)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getRange(key, startPos, end, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Set the string value of a key and return its old value.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param value value
     * @return The old value stored at key
     */
    public static Object getSet(HandleValue redisDataSourceHandleValue, String key, String value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getSet(key, value, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Increment the integer value of a key by one.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @return The value of the key after increment
     */
    public static Object incr(HandleValue redisDataSourceHandleValue, String key)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incr(key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Increment the integer value of a key by the given amount.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param value value
     * @return The value of the key after increment
     */
    public static Object incrBy(HandleValue redisDataSourceHandleValue, String key, int value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incrBy(key, value, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Increment the integer value of a key by the given amount.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param value value
     * @return The value of the key after increment
     */
    public static Object incrByFloat(HandleValue redisDataSourceHandleValue, String key, float value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incrByFloat(key, value, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Get the values of all the given keys.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys The keys of which the values need to be retrieved
     * @return Array of values at the specified keys
     */
    public static Object mGet(HandleValue redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return createBstringArrayFromBMap(mGet(redisDataSource, createStringArrayFromBArray(keys)));
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Set multiple keys to multiple values.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys A map of key-value pairs to be set
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object mSet(HandleValue redisDataSourceHandleValue, BMap keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return mSet(createMapFromBMap(keys), redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Set multiple keys to multiple values, only if none of the keys exist.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys A map of key-value pairs to be set
     * @return True if the operation was successful, false if it failed
     */
    public static Object mSetNx(HandleValue redisDataSourceHandleValue, BMap keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return mSetnx(createMapFromBMap(keys), redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Set the value and expiration in milliseconds of a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param value value
     * @param expirationTime Expiration time in milli seconds
     * @return New value of the key
     */
    public static Object pSetEx(HandleValue redisDataSourceHandleValue, String key, String value, int expirationTime)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return pSetex(key, value, expirationTime, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Set a string value for a given key value.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key key
     * @param redisValue value
     * @return `OK` if successful
     */
    public static Object set(HandleValue redisDataSourceHandleValue, String key, String redisValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return set(key, redisValue, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Sets or clears the bit at offset in the string value stored at key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param value value
     * @param offset The offset at which the value should be set
     * @return The original bit value stored at offset
     */
    public static Object setBit(HandleValue redisDataSourceHandleValue, String key, int value, int offset)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setBit(key, value, offset, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Set the value and expiration of a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param value value
     * @param expirationPeriodSeconds Expiration time to be set, in seconds
     * @return New value of the key or
     */
    public static Object setEx(HandleValue redisDataSourceHandleValue, String key, String value,
                               int expirationPeriodSeconds)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setEx(key, value, expirationPeriodSeconds, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Set the value of a key, only if the key does not exist.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param value value
     * @return New value of the key
     */
    public static Object setNx(HandleValue redisDataSourceHandleValue, String key, String value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setNx(key, value, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Overwrite part of a string at key starting at the specified offset.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @param offset The offset at which the value should be set
     * @param value value
     * @return The length of the string after it was modified
     */
    public static Object setRange(HandleValue redisDataSourceHandleValue, String key, int offset, String value)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setRange(key, offset, value, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }

    /**
     * Get the length of the value stored in a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key key
     * @return The length of the string at key, or 0 when key does not exis
     */
    public static Object strln(HandleValue redisDataSourceHandleValue, String key)  {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return strln(key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, e.getMessage());
        }
    }
}
