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

import io.ballerina.runtime.api.ErrorCreator;
import io.ballerina.runtime.api.StringUtils;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.runtime.values.HandleValue;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.Constants.REDIS_EXCEPTION_OCCURRED;

/**
 * Redis hash actions.
 */
public class HashActions extends AbstractRedisAction {

    /**
     * Delete one or more hash fields.
     * 
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param fields Array of fields to be deleted
     * @return Number of fields that were removed from the hash, not including specified but non existing fields
     */
    public static Object hDel(HandleValue redisDataSourceHandleValue, String key, BArray fields) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hDel(StringUtils.fromString(key), redisDataSource, createStringArrayFromBArray(fields));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Determine if a hash field exists.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param field Array of fields to be deleted
     * @return boolean `true` if the hash contains the field. boolean false if the hash does not contain
     * field or key does not exist
     */
    public static Object hExists(HandleValue redisDataSourceHandleValue, String key, String field) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hExists(key, field, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the value of a hash field.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param field The field
     * @return The value of the field
     */
    public static Object hGet(HandleValue redisDataSourceHandleValue, String key, String field) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hGet(key, field, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the all values of a hash.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @return Map of field-value pairs
     */
    public static Object hGetAll(HandleValue redisDataSourceHandleValue, String key) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hGetAll(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Increment the integer value of a hash field by the given number.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param field The field
     * @param amount The amount to increment
     * @return The value of the field
     */
    public static Object hIncrBy(HandleValue redisDataSourceHandleValue, String key, String field, int amount) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hIncrBy(key, field, amount, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Increment the float value of a hash field by the given number.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param field The field
     * @param amount The amount to increment
     * @return The value of the field
     */
    public static Object hIncrByFloat(HandleValue redisDataSourceHandleValue, String key, String field, double amount) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hIncrByFloat(key, field, amount, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get all the fields in a hash.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @return Array of hash fields
     */
    public static Object hKeys(HandleValue redisDataSourceHandleValue, String key) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hKeys(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the number of fields in a hash.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @return Number of fields
     */
    public static Object hLen(HandleValue redisDataSourceHandleValue, String key) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hLen(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the values of all the given hash fields.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param fields Array of hash fields
     * @return Map of field-value pairs
     */
    public static Object hMGet(HandleValue redisDataSourceHandleValue, String key, BArray fields) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hMGet(key, redisDataSource, createStringArrayFromBArray(fields));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set multiple hash fields to multiple values.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param fieldValueMap Map of field-value pairs
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object hMSet(HandleValue redisDataSourceHandleValue, String key, BMap fieldValueMap) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hMSet(key, createMapFromBMap(fieldValueMap), redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set the string value of a hash field.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param field The field
     * @param value The value to be set to the field
     * @return boolean `true` if field is a new field in the hash and value was set. boolean false if
     * field already exists in the hash and the value was updated
     */
    public static Object hSet(HandleValue redisDataSourceHandleValue, String key, String field, String value) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hSet(key, field, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set the string value of a hash field.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param field The field
     * @param value The value to be set to the field
     * @return boolean `true` if field is a new field in the hash and value was set. boolean false if
     * field already exists in the hash and the value was updated
     */
    public static Object hSetNx(HandleValue redisDataSourceHandleValue, String key, String field, String value) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hSetNx(key, field, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the string length of the field value in a hash.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @param field The field
     * @return The length of the field value, or 0 when field is not present in the hash or key does
     * not exist at all
     */
    public static Object hStrln(HandleValue redisDataSourceHandleValue, String key, String field) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hStrln(key, field, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get all the values in a hash.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the hash
     * @return Array of values in the hash, or an empty array when key does not exist
     */
    public static Object hVals(HandleValue redisDataSourceHandleValue, String key) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            return hVals(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
    }
}
