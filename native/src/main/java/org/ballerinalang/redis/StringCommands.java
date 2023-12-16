/*
 * Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.redis;

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.redis.connection.RedisStringCommandExecutor;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;
import static org.ballerinalang.redis.utils.ConversionUtils.createBStringArrayFromBMap;
import static org.ballerinalang.redis.utils.ConversionUtils.createMapFromBMap;
import static org.ballerinalang.redis.utils.ConversionUtils.createStringArrayFromBArray;
import static org.ballerinalang.redis.utils.RedisUtils.getConnection;

/**
 * Ballerina native util implementation for redis string commands.
 */
public class StringCommands {

    /**
     * Append a value to a key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param redisValue  value
     * @return size of the new string
     */
    public static Object append(BObject redisClient, BString key, BString redisValue) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.append(key.getValue(), redisValue.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Count set bits in a string.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key key
     * @return bit count
     */
    public static Object bitCount(BObject redisClient, BString key) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.bitCount(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Perform bitwise AND between strings.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination Result key of the operation
     * @param keys        Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpAnd(BObject redisClient, BString destination, BArray keys) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.bitopAnd(destination.getValue(), createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Perform bitwise OR between strings.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination Result key of the operation
     * @param keys        Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpOr(BObject redisClient, BString destination, BArray keys) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.bitopOr(destination.getValue(), createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Perform bitwise NOT on a string.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination Result key of the operation
     * @param key         Input keys to perform AND between
     * @return The size of the string stored in the destination key
     */
    public static Object bitOpNot(BObject redisClient, BString destination, BString key) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.bitopNot(destination.getValue(), key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Perform bitwise XOR between strings.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination Result key of the operation
     * @param keys        Input keys to perform AND between
     * @return The size of the string stored in the destination key, that is equal to the size of the longest input
     * string
     */
    public static Object bitOpXor(BObject redisClient, BString destination, BArray keys) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.bitopXor(destination.getValue(), createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Decrement the integer value of a key by one.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @return The value of the key after the decrement
     */
    public static Object decr(BObject redisClient, BString key) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.decr(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Decrement the integer value of a key by the given number.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param redisValue  The offset
     * @return The bit value stored at offset
     */
    public static Object decrBy(BObject redisClient, BString key, int redisValue) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.decrBy(key.getValue(), redisValue);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get a string value from redis database.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key key
     * @return value
     */
    public static Object get(BObject redisClient, BString key) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return StringUtils.fromString(executor.get(key.getValue()));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Returns the bit value at offset in the string value stored at key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param offset      The offset
     * @return The bit value stored at offset
     */
    public static Object getBit(BObject redisClient, BString key, int offset) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.getBit(key.getValue(), offset);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get a substring of the string stored at a key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param startPos    The starting point of the substring
     * @param end         The end point of the substring
     * @return The substring
     */
    public static Object getRange(BObject redisClient, BString key, int startPos, int end) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return StringUtils.fromString(executor.getRange(key.getValue(), startPos, end));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set the string value of a key and return its old value.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param value       value
     * @return The old value stored at key
     */
    public static Object getSet(BObject redisClient, BString key, BString value) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return StringUtils.fromString(executor.getSet(key.getValue(), value.getValue()));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Increment the integer value of a key by one.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @return The value of the key after increment
     */
    public static Object incr(BObject redisClient, BString key) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.incr(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Increment the integer value of a key by the given amount.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param value       value
     * @return The value of the key after increment
     */
    public static Object incrBy(BObject redisClient, BString key, int value) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.incrBy(key.getValue(), value);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Increment the integer value of a key by the given amount.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param value       value
     * @return The value of the key after increment
     */
    public static Object incrByFloat(BObject redisClient, BString key, float value) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.incrByFloat(key.getValue(), value);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the values of all the given keys.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param keys        The keys of which the values need to be retrieved
     * @return Array of values at the specified keys
     */
    public static Object mGet(BObject redisClient, BArray keys) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return createBStringArrayFromBMap(executor.mGet(createStringArrayFromBArray(keys)));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set multiple keys to multiple values.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param keys        A map of key-value pairs to be set
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object mSet(BObject redisClient, BMap<BString, Object> keys) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return StringUtils.fromString(executor.mSet(createMapFromBMap(keys)));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set multiple keys to multiple values, only if none of the keys exist.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param keys        A map of key-value pairs to be set
     * @return True if the operation was successful, false if it failed
     */
    public static Object mSetNx(BObject redisClient, BMap<BString, Object> keys) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.mSetnx(createMapFromBMap(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set the value and expiration in milliseconds of a key.
     *
     * @param redisClient    Client from the Ballerina redis client
     * @param key            key
     * @param value          value
     * @param expirationTime Expiration time in milli seconds
     * @return New value of the key
     */
    public static Object pSetEx(BObject redisClient, BString key, BString value, int expirationTime) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return StringUtils.fromString(executor.pSetex(key.getValue(), value.getValue(), expirationTime));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set a string value for a given key value.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key key
     * @param redisValue  value
     * @return `OK` if successful
     */
    public static Object set(BObject redisClient, BString key, BString redisValue) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return StringUtils.fromString(executor.set(key.getValue(), redisValue.getValue()));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Sets or clears the bit at offset in the string value stored at key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param value       value
     * @param offset      The offset at which the value should be set
     * @return The original bit value stored at offset
     */
    public static Object setBit(BObject redisClient, BString key, int value, int offset) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.setBit(key.getValue(), value, offset);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set the value and expiration of a key.
     *
     * @param redisClient             Client from the Ballerina redis client
     * @param key                     key
     * @param value                   value
     * @param expirationPeriodSeconds Expiration time to be set, in seconds
     * @return New value of the key or
     */
    public static Object setEx(BObject redisClient, BString key, BString value,
                               int expirationPeriodSeconds) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return StringUtils.fromString(executor.setEx(key.getValue(), value.getValue(), expirationPeriodSeconds));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set the value of a key, only if the key does not exist.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param value       value
     * @return New value of the key
     */
    public static Object setNx(BObject redisClient, BString key, BString value) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.setNx(key.getValue(), value.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Overwrite part of a string at key starting at the specified offset.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @param offset      The offset at which the value should be set
     * @param value       value
     * @return The length of the string after it was modified
     */
    public static Object setRange(BObject redisClient, BString key, int offset, BString value) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.setRange(key.getValue(), offset, value.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the length of the value stored in a key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         key
     * @return The length of the string at key, or 0 when key does not exis
     */
    public static Object strln(BObject redisClient, BString key) {
        try {
            RedisStringCommandExecutor executor = getConnection(redisClient).getStringCommandExecutor();
            return executor.strln(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
