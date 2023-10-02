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

import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BHandle;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.SetArgs;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.utils.ModuleUtils;

import static org.ballerinalang.redis.Constants.REDIS_EXCEPTION_OCCURRED;

/**
 * Redis string actions.
 */
public class StringActions extends AbstractRedisAction {

    /**
     * Append a value to a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key key
     * @param redisValue                 value
     * @return size of the new string
     */
    public static Object append(BHandle redisDataSourceHandleValue, String key, String redisValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return append(key, redisValue, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Count set bits in a string.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key key
     * @return bit count
     */
    public static Object bitCount(BHandle redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitCount(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Perform bitwise AND between strings.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination                Result key of the operation
     * @param keys                       Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpAnd(BHandle redisDataSourceHandleValue, String destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopAnd(destination, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Perform bitwise OR between strings.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination                Result key of the operation
     * @param keys                       Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpOr(BHandle redisDataSourceHandleValue, String destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopOr(destination, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Perform bitwise NOT on a string.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination                Result key of the operation
     * @param key                        Input keys to perform AND between
     * @return The size of the string stored in the destination key
     */
    public static Object bitOpNot(BHandle redisDataSourceHandleValue, String destination, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopNot(destination, key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Perform bitwise XOR between strings.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination                Result key of the operation
     * @param keys                       Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpXor(BHandle redisDataSourceHandleValue, String destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bitopXor(destination, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Decrement the integer value of a key by one.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @return The value of the key after the decrement
     */
    public static Object decr(BHandle redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return decr(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Decrement the integer value of a key by the given number.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param redisValue                 The offset
     * @return The bit value stored at offset
     */
    public static Object decrBy(BHandle redisDataSourceHandleValue, String key, int redisValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return decrBy(key, redisValue, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get a string value from redis database.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key key
     * @return value
     */
    public static Object get(BHandle redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return get(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Returns the bit value at offset in the string value stored at key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param offset                     The offset
     * @return The bit value stored at offset
     */
    public static Object getBit(BHandle redisDataSourceHandleValue, String key, int offset) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getBit(key, offset, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get a substring of the string stored at a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param startPos                   The starting point of the substring
     * @param end                        The end point of the substring
     * @return The substring
     */
    public static Object getRange(BHandle redisDataSourceHandleValue, String key, int startPos, int end) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getRange(key, startPos, end, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set the string value of a key and return its old value.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param value                      value
     * @return The old value stored at key
     */
    public static Object getSet(BHandle redisDataSourceHandleValue, String key, String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return getSet(key, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Increment the integer value of a key by one.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @return The value of the key after increment
     */
    public static Object incr(BHandle redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incr(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Increment the integer value of a key by the given amount.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param value                      value
     * @return The value of the key after increment
     */
    public static Object incrBy(BHandle redisDataSourceHandleValue, String key, int value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incrBy(key, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Increment the integer value of a key by the given amount.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param value                      value
     * @return The value of the key after increment
     */
    public static Object incrByFloat(BHandle redisDataSourceHandleValue, String key, float value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return incrByFloat(key, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the values of all the given keys.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys                       The keys of which the values need to be retrieved
     * @return Array of values at the specified keys
     */
    public static Object mGet(BHandle redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return createBstringArrayFromBMap(mGet(redisDataSource, createStringArrayFromBArray(keys)));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set multiple keys to multiple values.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys                       A map of key-value pairs to be set
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object mSet(BHandle redisDataSourceHandleValue, BMap keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return mSet(createMapFromBMap(keys), redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set multiple keys to multiple values, only if none of the keys exist.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys                       A map of key-value pairs to be set
     * @return True if the operation was successful, false if it failed
     */
    public static Object mSetNx(BHandle redisDataSourceHandleValue, BMap keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return mSetnx(createMapFromBMap(keys), redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set the value and expiration in milliseconds of a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param value                      value
     * @param expirationTime             Expiration time in milli seconds
     * @return New value of the key
     */
    public static Object pSetEx(BHandle redisDataSourceHandleValue, String key, String value, int expirationTime) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return pSetex(key, value, expirationTime, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set a string value for a given key value.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key key
     * @param redisValue                 value
     * @return `OK` if successful
     */
    public static Object set(BHandle redisDataSourceHandleValue, String key, String redisValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return set(key, redisValue, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

        /**
     * Set a string value for a given key value while setting SET command options.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key key
     * @param redisValue                 value
     * @param options                    SET command options
     * @return `OK` if successful
     */
    public static Object set(BHandle redisDataSourceHandleValue, String key, String redisValue,
                             BMap<BString, Object> options) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return set(key, redisValue, createSetArgument(options), redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Sets or clears the bit at offset in the string value stored at key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param value                      value
     * @param offset                     The offset at which the value should be set
     * @return The original bit value stored at offset
     */
    public static Object setBit(BHandle redisDataSourceHandleValue, String key, int value, int offset) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setBit(key, value, offset, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set the value and expiration of a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param value                      value
     * @param expirationPeriodSeconds    Expiration time to be set, in seconds
     * @return New value of the key or
     */
    public static Object setEx(BHandle redisDataSourceHandleValue, String key, String value,
                               int expirationPeriodSeconds) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setEx(key, value, expirationPeriodSeconds, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set the value of a key, only if the key does not exist.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param value                      value
     * @return New value of the key
     */
    public static Object setNx(BHandle redisDataSourceHandleValue, String key, String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setNx(key, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Overwrite part of a string at key starting at the specified offset.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @param offset                     The offset at which the value should be set
     * @param value                      value
     * @return The length of the string after it was modified
     */
    public static Object setRange(BHandle redisDataSourceHandleValue, String key, int offset, String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return setRange(key, offset, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the length of the value stored in a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key                        key
     * @return The length of the string at key, or 0 when key does not exis
     */
    public static Object strln(BHandle redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return strln(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(),
                    StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Create SetArgs object from BMap.
     *
     * @param options BMap of SetArgs options given by the user
     * @return SetArgs object
     */
    private static SetArgs createSetArgument(BMap<BString, Object> options) {
        SetArgs setArgs = new SetArgs();

        for (BString key : options.getKeys()) {
            if (key.toString().equals("nx") && options.getBooleanValue(key)) {
                setArgs.nx();
            } else if (key.toString().equals("xx") && options.getBooleanValue(key)) {
                setArgs.xx();
            } else if (key.toString().equals("ex") && options.getFloatValue(key) != null) {
                long ex = options.getFloatValue(key).longValue();
                setArgs.ex(ex);
            } else if (key.toString().equals("px") && options.getFloatValue(key) != null) {
                long px = options.getFloatValue(key).longValue();
                setArgs.px(px);
            }
        }
        return setArgs;
    }
}
