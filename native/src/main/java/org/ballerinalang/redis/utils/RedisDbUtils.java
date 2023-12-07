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

package org.ballerinalang.redis.utils;

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;

/**
 * Redis database utils to run unit tests.
 */
public class RedisDbUtils {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static RedisCommands<String, String> redisCommands;

    static {
        setUpClient();
    }

    /**
     * Create a redis database client.
     */
    private static void setUpClient() {
        StatefulRedisConnection<String, String> statefulRedisConnection;
        RedisURI redisURI = RedisURI.Builder.redis(REDIS_HOST, REDIS_PORT).build();
        RedisClient redisClient = RedisClient.create(redisURI);
        statefulRedisConnection = redisClient.connect(StringCodec.UTF8);
        redisCommands = statefulRedisConnection.sync();
    }

    /**
     * Get value for a given key.
     *
     * @param key key
     * @return value
     */
    public static BString getValue(String key) {
        if (redisCommands.get(key) != null) {
            return StringUtils.fromString(redisCommands.get(key));
        } else {
            return StringUtils.fromString("");
        }
    }

    /**
     * Check if set contains a member.
     *
     * @param key   key
     * @param value member
     * @return True if member exists in set, false if not
     */
    public static boolean sisMember(String key, String value) {
        return redisCommands.sismember(key, value);
    }

    /**
     * Check if hash exists.
     *
     * @param key   key
     * @param field field
     * @return True if hash exists, false if not
     */
    public static boolean hexists(String key, String field) {
        return redisCommands.hexists(key, field);
    }

    /**
     * Get a value of a hash.
     *
     * @param key   key
     * @param field field
     * @return hash value
     */
    public static String hget(String key, String field) {
        return redisCommands.hget(key, field);
    }

    /**
     * Check if key exists.
     *
     * @param key key
     * @return 1 if key exists, 0 if not
     */
    public static long exist(String key) {
        return redisCommands.exists(key);
    }

    /**
     * Make a key expire after a given time.
     *
     * @param key      key
     * @param duration duration
     */
    public static void pexpire(String key, int duration) {
        redisCommands.pexpire(key, duration);
    }

    /**
     * Get the time to live for a key.
     *
     * @param key key
     * @return time to live
     */
    public static long pttl(String key) {
        return redisCommands.pttl(key);
    }

    /**
     * Get the value using an index of a set.
     *
     * @param key   key
     * @param index index
     * @return value
     */
    public static BString lindex(String key, int index) {
        return StringUtils.fromString(redisCommands.lindex(key, index));
    }
}
