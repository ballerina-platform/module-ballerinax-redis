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

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.redis.connection.RedisConnectionManager;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;
import static org.ballerinalang.redis.utils.ConversionUtils.createMapFromBMap;
import static org.ballerinalang.redis.utils.ConversionUtils.createStringArrayFromBArray;

/**
 * Redis hash actions.
 */
public class HashActions extends AbstractRedisAction {

    /**
     * Delete one or more hash fields.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the hash
     * @param fields      Array of fields to be deleted
     * @return Number of fields that were removed from the hash, not including specified but non existing fields
     */
    public static Object hDel(BObject redisClient, BString key, BArray fields) {
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hDel(StringUtils.fromString(key.getValue()), connectionManager, createStringArrayFromBArray(fields));
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hExists(key.getValue(), field.getValue(), connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return StringUtils.fromString(hGet(key.getValue(), field.getValue(), connectionManager));
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hGetAll(key.getValue(), connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hIncrBy(key.getValue(), field.getValue(), amount, connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hIncrByFloat(key.getValue(), field.getValue(), amount, connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hKeys(key.getValue(), connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hLen(key.getValue(), connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hMGet(key.getValue(), connectionManager, createStringArrayFromBArray(fields));
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
    public static Object hMSet(BObject redisClient, BString key, BMap fieldValueMap) {
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return StringUtils.fromString(hMSet(key.getValue(), createMapFromBMap(fieldValueMap), connectionManager));
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hSet(key.getValue(), field.getValue(), value.getValue(), connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hSetNx(key.getValue(), field.getValue(), value.getValue(), connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hStrln(key.getValue(), field.getValue(), connectionManager);
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
        RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
        try {
            return hVals(key.getValue(), connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
