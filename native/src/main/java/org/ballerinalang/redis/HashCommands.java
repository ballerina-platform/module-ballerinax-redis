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
import org.ballerinalang.redis.connection.RedisHashCommandExecutor;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;
import static org.ballerinalang.redis.utils.ConversionUtils.createMapFromBMap;
import static org.ballerinalang.redis.utils.ConversionUtils.createStringArrayFromBArray;
import static org.ballerinalang.redis.utils.RedisUtils.getConnection;

/**
 * Ballerina native util implementation for redis hash commands.
 *
 * @since 3.0.0
 */
@SuppressWarnings("unused")
public class HashCommands {

    /**
     * Delete one or more hash fields.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param fields      Array of fields to be deleted
     * @return Number of fields that were removed from the hash, not including specified but non-existing fields
     */
    public static Object hDel(BObject redisClient, BString key, BArray fields) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hDel(StringUtils.fromString(key.getValue()), createStringArrayFromBArray(fields));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Determine if a hash field exists.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param field       Array of fields to be deleted
     * @return boolean `true` if the hash contains the field. boolean false if the hash does not contain field or key
     * does not exist
     */
    public static Object hExists(BObject redisClient, BString key, BString field) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hExists(key.getValue(), field.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the value of a hash field.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param field       The field
     * @return The value of the field
     */
    public static Object hGet(BObject redisClient, BString key, BString field) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return StringUtils.fromString(executor.hGet(key.getValue(), field.getValue()));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the all values of a hash.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @return Map of field-value pairs
     */
    public static Object hGetAll(BObject redisClient, BString key) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hGetAll(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Increment the integer value of a hash field by the given number.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param field       The field
     * @param amount      The amount to increment
     * @return The value of the field
     */
    public static Object hIncrBy(BObject redisClient, BString key, BString field, int amount) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hIncrBy(key.getValue(), field.getValue(), amount);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Increment the float value of a hash field by the given number.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param field       The field
     * @param amount      The amount to increment
     * @return The value of the field
     */
    public static Object hIncrByFloat(BObject redisClient, BString key, BString field, double amount) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hIncrByFloat(key.getValue(), field.getValue(), amount);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get all the fields in a hash.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @return Array of hash fields
     */
    public static Object hKeys(BObject redisClient, BString key) {

        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hKeys(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the number of fields in a hash.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @return Number of fields
     */
    public static Object hLen(BObject redisClient, BString key) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hLen(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the values of all the given hash fields.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param fields      Array of hash fields
     * @return Map of field-value pairs
     */
    public static Object hMGet(BObject redisClient, BString key, BArray fields) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hMGet(key.getValue(), createStringArrayFromBArray(fields));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set multiple hash fields to multiple values.
     *
     * @param redisClient   Client from the Ballerina redis client
     * @param key           The key of the hash
     * @param fieldValueMap Map of field-value pairs
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object hMSet(BObject redisClient, BString key, BMap<BString, Object> fieldValueMap) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return StringUtils.fromString(executor.hMSet(key.getValue(), createMapFromBMap(fieldValueMap)));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set the string value of a hash field.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param field       The field
     * @param value       The value to be set to the field
     * @return boolean `true` if field is a new field in the hash and value was set. boolean false if field already
     * exists in the hash and the value was updated
     */
    public static Object hSet(BObject redisClient, BString key, BString field, BString value) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hSet(key.getValue(), field.getValue(), value.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set the string value of a hash field.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param field       The field
     * @param value       The value to be set to the field
     * @return boolean `true` if field is a new field in the hash and value was set. boolean false if field already
     * exists in the hash and the value was updated
     */
    public static Object hSetNx(BObject redisClient, BString key, BString field, BString value) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hSetNx(key.getValue(), field.getValue(), value.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the string length of the field value in a hash.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param field       The field
     * @return The length of the field value, or 0 when field is not present in the hash or key does not exist at all
     */
    public static Object hStrln(BObject redisClient, BString key, BString field) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hStrln(key.getValue(), field.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get all the values in a hash.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @return Array of values in the hash, or an empty array when key does not exist
     */
    public static Object hVals(BObject redisClient, BString key) {
        try {
            RedisHashCommandExecutor executor = getConnection(redisClient).getHashCommandExecutor();
            return executor.hVals(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
