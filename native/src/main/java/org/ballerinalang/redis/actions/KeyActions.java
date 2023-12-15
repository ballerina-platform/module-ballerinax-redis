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
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.redis.connection.RedisConnectionManager;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;
import static org.ballerinalang.redis.utils.ConversionUtils.createStringArrayFromBArray;

/**
 * Redis key actions.
 */
public class KeyActions extends AbstractRedisAction {

    /**
     * Delete one or more keys.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param keys        The key to be deleted
     * @return The number of keys that were removed
     */
    public static Object del(BObject redisClient, BArray keys) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return del(connectionManager, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Determine how many keys exist.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param keys        The keys of which existence to be found out
     * @return The number of existing keys
     */
    public static Object exists(BObject redisClient, BArray keys) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return exists(connectionManager, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set a key's time to live in seconds.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The keys of which expiry time to be set
     * @param seconds     Expiry in seconds
     * @return boolean `true` if the timeout was set. false if key does not exist or the timeout could not be set
     */
    public static Object expire(BObject redisClient, BString key, int seconds) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return expire(key.getValue(), seconds, connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Find all keys matching the given pattern.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param pattern     The pattern to match
     * @return Array of keys matching the given pattern
     */
    public static Object keys(BObject redisClient, BString pattern) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return keys(pattern.getValue(), connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Move a key to another database.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key to be moved
     * @param database    The database to which the key needs to be moved
     * @return boolean true if key was succesfully moved, boolean false otherwise
     */
    public static Object move(BObject redisClient, BString key, int database) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return move(key.getValue(), database, connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove the expiration from a key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of which expiry time should be removed
     * @return boolean `true` if the timeout was removed. boolean `false` if key does not exist or does not have an
     * associated timeout
     */
    public static Object persist(BObject redisClient, BString key) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return persist(key.getValue(), connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set a key's time to live in milliseconds.
     *
     * @param redisClient      Client from the Ballerina redis client
     * @param key              he key of which expiry time should be removed
     * @param timeMilliSeconds The expiry time in milli seconds
     * @return boolean `true` if the timeout was set. boolean false if key does not exist or the timeout could not be
     * set
     */
    public static Object pExpire(BObject redisClient, BString key, int timeMilliSeconds) {
        try {
            RedisConnectionManager redisDataSource = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return pExpire(key.getValue(), timeMilliSeconds, redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the time to live for a key in milliseconds.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of which time-to-live should be obtained
     * @return time-to-live of the key, in milli seconds
     */
    public static Object pTtl(BObject redisClient, BString key) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return pTtl(key.getValue(), connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return a random key from the keyspace.
     *
     * @param redisClient Client from the Ballerina redis client
     * @return The random key
     */
    public static Object randomKey(BObject redisClient) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return StringUtils.fromString(randomKey(connectionManager));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Rename a key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key to be renamed
     * @param newName     The new name of the key
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object rename(BObject redisClient, BString key, BString newName) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return StringUtils.fromString(rename(key.getValue(), newName.getValue(), connectionManager));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Rename a key, only if the new key does not exist.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key to be renamed
     * @param newName     The new name of the key boolean `true` if key was renamed to newkey. boolean
     *                    `false` if newkey already exists.
     * @return boolean `true` if key was renamed to newkey. boolean `false` if newkey already exists
     */
    public static Object renameNx(BObject redisClient, BString key, BString newName) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return renameNx(key.getValue(), newName.getValue(), connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Sort the elements in a list, set or sorted set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the data typeure to be sorted
     * @return Sorted array containing the members of the sorted data type
     */
    public static Object sort(BObject redisClient, BString key) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return sort(key.getValue(), connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the time to live for a key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of which the time to live needs to be obtained
     * @return Time to live in seconds or a negative value/`error` in order to signal an error in evaluating ttl.
     * Whether it is a negative value of an `error` would differ depending on whether the error occurs at DB level or
     * the driver level
     */
    public static Object ttl(BObject redisClient, BString key) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return ttl(key.getValue(), connectionManager);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Determine the type stored at key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of which the type needs to be obtained
     * @return Type stored at key
     */
    public static Object redisType(BObject redisClient, BString key) {
        try {
            RedisConnectionManager connectionManager = (RedisConnectionManager) redisClient.getNativeData(CONN_OBJ);
            return StringUtils.fromString(type(key.getValue(), connectionManager));
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
