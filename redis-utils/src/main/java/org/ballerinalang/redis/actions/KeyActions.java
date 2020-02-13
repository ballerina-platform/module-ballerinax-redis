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
import org.ballerinalang.model.values.BString;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.BallerinaRedisDbErrors.REDIS_EXCEPTION_OCCURRED;

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
            return del(redisDataSource, createStringArrayFromBArray(keys)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
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
            return exists(redisDataSource, createStringArrayFromBArray(keys)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
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
            return expire(key, seconds, redisDataSource).booleanValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
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
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
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
            return move(key, database, redisDataSource).booleanValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key
     * @return
     */
    public static Object persist(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return persist(key, redisDataSource).booleanValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key
     * @param timeMilliSeconds
     * @return
     */
    public static Object pExpire(HandleValue redisDataSourceHandleValue, String key, int timeMilliSeconds) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return pExpire(key, timeMilliSeconds, redisDataSource).booleanValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key
     * @return
     */
    public static Object pTtl(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return pTtl(key, redisDataSource).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     *
     * @param redisDataSourceHandleValue redis datasource
     * @return
     */
    public static Object randomKey(HandleValue redisDataSourceHandleValue) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return randomKey(redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key
     * @param newName
     * @return
     */
    public static Object rename(HandleValue redisDataSourceHandleValue, String key, String newName) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rename(key, newName, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key
     * @param newName
     * @return
     */
    public static Object renameNx(HandleValue redisDataSourceHandleValue, String key, String newName) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return renameNx(key, newName, redisDataSource).booleanValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key
     * @return
     */
    public static Object sort(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sort(key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key
     * @return
     */
    public static Object ttl(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return ttl(key, redisDataSource).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * 
     * @param redisDataSourceHandleValue redis datasource
     * @param key
     * @return
     */
    public static Object redisType(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return type(key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }
}
