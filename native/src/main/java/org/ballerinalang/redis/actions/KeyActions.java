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
import io.ballerina.runtime.api.values.BString;
import io.ballerina.runtime.internal.values.HandleValue;
import io.ballerina.runtime.api.values.BArray;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.utils.ModuleUtils;

import static org.ballerinalang.redis.Constants.REDIS_EXCEPTION_OCCURRED;

/**
 * Redis key actions.
 */
public class KeyActions extends AbstractRedisAction {

    /**
     * Delete one or more keys.
     * 
     * redisDataSourceHandleValue redis datasource
     * @param keys The key to be deleted
     * @return The number of keys that were removed
     */
    public static Object del(HandleValue redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return del(redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Determine how many keys exist.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys The keys of which existence to be found out
     * @return The number of existing keys
     */
    public static Object exists(HandleValue redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return exists(redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set a key's time to live in seconds.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The keys of which expiry time to be set
     * @param seconds Expiry in seconds
     * @return boolean `true` if the timeout was set. false if key does not exist or the timeout could not be set
     */
    public static Object expire(HandleValue redisDataSourceHandleValue, String key, int seconds) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return expire(key, seconds, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Find all keys matching the given pattern.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param pattern The pattern to match
     * @return Array of keys matching the given pattern
     */
    public static Object keys(HandleValue redisDataSourceHandleValue, String pattern) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return keys(pattern, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Move a key to another database.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key to be moved
     * @param database The database to which the key needs to be moved
     * @return boolean true if key was succesfully moved, boolean false otherwise
     */
    public static Object move(HandleValue redisDataSourceHandleValue, String key, int database) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return move(key, database, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove the expiration from a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of which expiry time should be removed
     * @return boolean `true` if the timeout was removed. boolean `false` if key does not exist or does not have
     *         an associated timeout
     */
    public static Object persist(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return persist(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set a key's time to live in milliseconds.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key he key of which expiry time should be removed
     * @param timeMilliSeconds The expiry time in milli seconds
     * @return boolean `true` if the timeout was set. boolean false if key does not exist or the timeout could not
     *         be set
     */
    public static Object pExpire(HandleValue redisDataSourceHandleValue, String key, int timeMilliSeconds) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return pExpire(key, timeMilliSeconds, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the time to live for a key in milliseconds.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of which time-to-live should be obtained
     * @return time-to-live of the key, in milli seconds
     */
    public static Object pTtl(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return pTtl(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Return a random key from the keyspace.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @return The random key
     */
    public static Object randomKey(HandleValue redisDataSourceHandleValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return randomKey(redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Rename a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key to be renamed
     * @param newName The new name of the key
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object rename(HandleValue redisDataSourceHandleValue, String key, String newName) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rename(key, newName, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Rename a key, only if the new key does not exist.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key to be renamed
     * @param newName The new name of the key boolean `true` if key was renamed to newkey. boolean `false` if
     *                newkey already exists.
     * @return boolean `true` if key was renamed to newkey. boolean `false` if newkey already exists
     */
    public static Object renameNx(HandleValue redisDataSourceHandleValue, String key, String newName) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return renameNx(key, newName, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Sort the elements in a list, set or sorted set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the data typeure to be sorted
     * @return Sorted array containing the members of the sorted data type
     */
    public static Object sort(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sort(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the time to live for a key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of which the time to live needs to be obtained
     * @return Time to live in seconds or a negative value/`error` in order to signal an error in evaluating ttl.
     *         Whether it is a negative value of an `error` would differ depending on whether the error occurs at DB
     *         level or the driver level
     */
    public static Object ttl(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return ttl(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Determine the type stored at key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of which the type needs to be obtained
     * @return Type stored at key
     */
    public static Object redisType(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return type(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }
}
