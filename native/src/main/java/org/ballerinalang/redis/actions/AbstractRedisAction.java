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

package org.ballerinalang.redis.actions;

import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.KeyValue;
import io.lettuce.core.Range;
import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.api.sync.RedisHashCommands;
import io.lettuce.core.api.sync.RedisKeyCommands;
import io.lettuce.core.api.sync.RedisListCommands;
import io.lettuce.core.api.sync.RedisServerCommands;
import io.lettuce.core.api.sync.RedisSetCommands;
import io.lettuce.core.api.sync.RedisSortedSetCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.exceptions.RedisConnectorException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.ballerinalang.redis.utils.ConversionUtils.createArrayFromScoredValueMap;
import static org.ballerinalang.redis.utils.ConversionUtils.createBMapFromKeyValueList;
import static org.ballerinalang.redis.utils.ConversionUtils.createBMapFromMap;
import static org.ballerinalang.redis.utils.ConversionUtils.createBStringArrayFromList;
import static org.ballerinalang.redis.utils.ConversionUtils.createBStringArrayFromSet;

/**
 * {@code {@link AbstractRedisAction}} is the base class for all Redis connector actions.
 *
 * @since 0.5.0
 */
public abstract class AbstractRedisAction {

    public static final String DATA_SOURCE = "DATA_SOURCE";

    private static final String MUST_NOT_BE_NULL = "must not be null";
    private static final String KEY_MUST_NOT_BE_NULL = "Key " + MUST_NOT_BE_NULL;
    private static final String KEYS_MUST_NOT_BE_NULL = "Key(s) " + MUST_NOT_BE_NULL;
    private static final String ARGUMENTS_MUST_NOT_BE_NULL = "Arguments " + MUST_NOT_BE_NULL;
    private static final String REDIS_SERVER_ERROR = "Redis server error: ";

    // ========================== String Commands  =============================== //

    static <K, V> String set(K key, V value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.set(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K> String get(K key, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.get(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static long append(String key, String value, RedisDataSource<String, String> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<String, String> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.append(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static long bitCount(String key, RedisDataSource<String, String> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<String, String> redisCommands = null;
        try {
            redisCommands = getRedisStringCommands(redisDataSource);
            return redisCommands.bitcount(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long bitopAnd(K destination, RedisDataSource<K, V> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.bitopAnd(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long bitopOr(K destination, RedisDataSource<K, V> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.bitopOr(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long bitopNot(K destination, K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.bitopNot(destination, key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long bitopXor(K destination, RedisDataSource<K, V> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.bitopXor(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long decr(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.decr(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long decrBy(K key, int value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.decrby(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long getBit(K key, int offset, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.getbit(key, offset);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K> String getRange(K key, int start, int end, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.getrange(key, start, end);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K> String getSet(K key, String value, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.getset(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long incr(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.incr(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long incrBy(K key, int value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.incrby(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> double incrByFloat(K key, double value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.incrbyfloat(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, Object> mGet(RedisDataSource<K, String> redisDataSource, K[] key) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            List<KeyValue<K, String>> result = stringCommands.mget(key);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> String mSet(Map<K, V> map, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.mset(map);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> boolean mSetnx(Map<K, V> map, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.msetnx(map);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K> String pSetex(K key, String value, long expirationPeriodMS,
                             RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.psetex(key, expirationPeriodMS, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long setBit(K key, int value, long offset, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.setbit(key, offset, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K> String setEx(K key, String value, long expirationPeriodSeconds,
                            RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.setex(key, expirationPeriodSeconds, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K> boolean setNx(K key, String value, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.setnx(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long setRange(K key, long offset, V value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.setrange(key, offset, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    static <K, V> long strln(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = getRedisStringCommands(redisDataSource);
            return stringCommands.strlen(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(stringCommands, redisDataSource);
        }
    }

    //// ========================== List Commands  =============================== //

    static <K, V> long lPush(K key, RedisDataSource<K, V> redisDataSource, V[] value) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.lpush(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K> String lPop(K key, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.lpop(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K, V> long lPushX(K key, RedisDataSource<K, V> redisDataSource, V[] values) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.lpushx(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, Object> bLPop(long timeout, RedisDataSource<K, String> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            KeyValue<K, String> result = listCommands.blpop(timeout, keys);
            if (result != null) {
                BMap<BString, Object> bMap = ValueCreator.createMapValue();
                bMap.put(StringUtils.fromString((String) result.getKey()), StringUtils.fromString(result.getValue()));
                return bMap;
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, Object> bRPop(long timeout, RedisDataSource<K, String> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            KeyValue<K, String> result = listCommands.brpop(timeout, keys);
            if (result != null) {
                BMap<BString, Object> bMap = ValueCreator.createMapValue();
                bMap.put(StringUtils.fromString((String) result.getKey()), StringUtils.fromString(result.getValue()));
                return bMap;
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K> String lIndex(K key, long index, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) getRedisListCommands(redisDataSource);
            return listCommands.lindex(key, index);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K, V> long lInsert(K key, boolean before, V pivot, V value,
                               RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.linsert(key, before, pivot, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K, V> long lLen(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.llen(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K> BArray lRange(K key, long start, long stop, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            List<String> result = listCommands.lrange(key, start, stop);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K, V> long lRem(K key, long count, V value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.lrem(key, count, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K, V> String lSet(K key, long index, V value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.lset(key, index, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K, V> String lTrim(K key, long start, long stop, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.ltrim(key, start, stop);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K> String rPop(K key, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.rpop(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K> String rPopLPush(K src, K destination, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.rpoplpush(src, destination);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    static <K, V> long rPush(K key, RedisDataSource<K, V> redisDataSource, V[] values) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.rpush(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    protected <K> String brPopLPush(K source, K destination, long timeout,
                                    RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.brpoplpush(timeout, source, destination);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    protected <K, V> long rPushX(K key, RedisDataSource<K, V> redisDataSource, V[] values) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = getRedisListCommands(redisDataSource);
            return listCommands.rpushx(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(listCommands, redisDataSource);
        }
    }

    // ============================= Set Commands  ================================== //

    static <K, V> long sAdd(K key, RedisDataSource<K, V> redisDataSource, V[] values) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            return setCommands.sadd(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K, V> long sCard(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            return setCommands.scard(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> BArray sDiff(RedisDataSource<K, String> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            Set<String> result = setCommands.sdiff(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> long sDiffStore(K dest, RedisDataSource<K, String> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            return setCommands.sdiffstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> BArray sInter(RedisDataSource<K, String> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            Set<String> result = setCommands.sinter(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> long sInterStore(K dest, RedisDataSource<K, String> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            return setCommands.sinterstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K, V> boolean sIsMember(K key, V value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            return setCommands.sismember(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> BArray sMembers(K key, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            Set<String> result = setCommands.smembers(key);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K, V> boolean sMove(K source, K dest, V member, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            return setCommands.smove(source, dest, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> BArray sPop(K key, int count, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            Set<String> result = setCommands.spop(key, count);
            return (result == null || result.isEmpty()) ? null : createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> BArray sRandMember(K key, int count, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            List<String> result = setCommands.srandmember(key, count);
            return (result == null || result.isEmpty()) ? null : createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> long sRem(K key, RedisDataSource<K, String> redisDataSource, String[] members) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            return setCommands.srem(key, members);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> BArray sUnion(RedisDataSource<K, String> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            Set<String> result = setCommands.sunion(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    static <K> long sUnionStore(K dest, RedisDataSource<K, String> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = getRedisSetCommands(redisDataSource);
            return setCommands.sunionstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Argements " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(setCommands, redisDataSource);
        }
    }

    // ============================= Sorted Set Commands  ================================== //

    static <K, V> long zAdd(K key, RedisDataSource<K, V> redisDataSource, Map<V, Double> valueScoreMap) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zadd(key, createArrayFromScoredValueMap(valueScoreMap));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Members " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> long zCard(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zcard(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> long zCount(K key, double min, double max, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        Range<Double> range = Range.create(min, max);
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zcount(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> double zIncrBy(K key, double amount, V member, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zincrby(key, amount, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> long zInterStore(K dest, RedisDataSource<K, V> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zinterstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> long zLexCount(K key, V min, V max, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            Range<V> range = Range.create(min, max);
            return sortedSetCommands.zlexcount(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> BArray zRange(K key, long min, long max, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            List<String> result = sortedSetCommands.zrange(key, min, max);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> BArray zRangeByLex(K key, String min, String max, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            Range<String> range = Range.create(min, max);
            List<String> result = sortedSetCommands.zrangebylex(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> BArray zRevRangeByLex(K key, String min, String max,
                                     RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            Range<String> range = Range.create(min, max);
            List<String> result = sortedSetCommands.zrevrangebylex(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Arguments" + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> BArray zRangeByScore(K key, double min, double max,
                                    RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            Range<Double> range = Range.create(min, max);
            List<String> result = sortedSetCommands.zrangebyscore(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> long zRank(K key, V member, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zrank(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> long zRem(K key, RedisDataSource<K, V> redisDataSource, V[] members) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zrem(key, members);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> long zRemRangeByLex(K key, String min, String max,
                                   RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            Range<String> range = Range.create(min, max);
            return sortedSetCommands.zremrangebylex(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> long zRemRangeByRank(K key, long min, long max,
                                    RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zremrangebyrank(key, min, max);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> long zRemRangeByScore(K key, double min, double max,
                                     RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            Range<Double> range = Range.create(min, max);
            return sortedSetCommands.zremrangebyscore(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> BArray zRevRange(K key, long min, long max, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            List<String> result = sortedSetCommands.zrevrange(key, min, max);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K> BArray zRevRangeByScore(K key, double min, double max,
                                       RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            Range<Double> range = Range.create(min, max);
            List<String> result = sortedSetCommands.zrevrangebyscore(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> long zRevRank(K key, V member, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zrevrank(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> double zScore(K key, V member, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zscore(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    static <K, V> long zUnionStore(K dest, RedisDataSource<K, V> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = getRedisSortedSetCommands(redisDataSource);
            return sortedSetCommands.zunionstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Destination key/source key(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(sortedSetCommands, redisDataSource);
        }
    }

    // ============================= Hash Commands  ================================== //

    static <K, V> long hDel(BString key, RedisDataSource<K, V> redisDataSource, K[] fields) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hdel((K) key.toString(), fields);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K, V> boolean hExists(K key, K field, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hexists(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K> String hGet(K key, K field, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hget(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, Object> hGetAll(K key, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            Map<K, String> result = hashCommands.hgetall(key);
            return createBMapFromMap(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K> long hIncrBy(K key, K field, long amount, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hincrby(key, field, amount);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K> double hIncrByFloat(K key, K field, double amount,
                                   RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hincrbyfloat(key, field, amount);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static BArray hKeys(String key, RedisDataSource<String, String> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<String, String> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            List<String> result = hashCommands.hkeys(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K> long hLen(K key, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hlen(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, Object> hMGet(K key, RedisDataSource<K, String> redisDataSource, K[] fields) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            List<KeyValue<K, String>> result = hashCommands.hmget(key, fields);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K, V> String hMSet(K key, Map<K, V> fieldValueMap, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hmset(key, fieldValueMap);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K, V> boolean hSet(K key, K field, V value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hset(key, field, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K, V> boolean hSetNx(K key, K field, V value, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hsetnx(key, field, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K, V> long hStrln(K key, K field, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            return hashCommands.hstrlen(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    static <K> BArray hVals(K key, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = getRedisHashCommands(redisDataSource);
            List<String> result = hashCommands.hvals(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(hashCommands, redisDataSource);
        }
    }

    // ============================= Key Commands  ================================== //

    static <K, V> long del(RedisDataSource<K, V> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.del(keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K, V> long exists(RedisDataSource<K, V> redisDataSource, K[] keys) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.exists(keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K, V> boolean expire(K key, long seconds, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.expire(key, seconds);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <V> BArray keys(String pattern, RedisDataSource<String, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<String, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            List<String> result = keyCommands.keys(pattern);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K, V> boolean move(K key, int db, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.move(key, db);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K, V> boolean persist(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.persist(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K, V> boolean pExpire(K key, long milliSeconds, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.pexpire(key, milliSeconds);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K, V> long pTtl(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.pttl(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K> String randomKey(RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.randomkey();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K> String rename(K key, K newName, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.rename(key, newName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K> boolean renameNx(K key, K newName, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.renamenx(key, newName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K> BArray sort(K key, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            List<String> result = keyCommands.sort(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K, V> long ttl(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.ttl(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    static <K, V> String type(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.type(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    // TODO: Add as a native action once byte type is supported in ballerina. When doing so retrun a BType
    protected <K, V> byte[] dump(K key, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = getRedisKeyCommands(redisDataSource);
            return keyCommands.dump(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(keyCommands, redisDataSource);
        }
    }

    // ============================= Connection Commands  ================================== //

    static <K, V> String auth(String password, RedisDataSource<K, V> redisDataSource) throws RedisConnectorException {
        RedisCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.auth(password);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Password " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String echo(String message, RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.echo(message);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String ping(RedisDataSource<K, String> redisDataSource) throws RedisConnectorException {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.ping();
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    private static boolean isClusterConnection(RedisDataSource<?, ?> redisDataSource) {
        return redisDataSource.isClusterConnection();
    }

    private static <K, V> void releaseResources(Object redisCommands, RedisDataSource<K, V> redisDataSource) {
        if (redisDataSource.isPoolingEnabled() && redisCommands != null) {
            redisDataSource.releaseResources(redisCommands);
        }
    }

    protected static <K, V> void close(RedisDataSource<K, V> redisDataSource) {
        if (isClusterConnection(redisDataSource)) {
            if (redisDataSource.isPoolingEnabled()) {
                redisDataSource.closeConnectionPool();
            } else {
                redisDataSource.getRedisClusterCommands().quit();
            }
        } else {
            if (redisDataSource.isPoolingEnabled()) {
                redisDataSource.closeConnectionPool();
            } else {
                redisDataSource.getRedisCommands().quit();
            }
        }
    }

    private static <K, V> RedisServerCommands<K, V> getRedisCommands(RedisDataSource<K, V> dataSource) {
        if (isClusterConnection(dataSource)) {
            return dataSource.getRedisClusterCommands();
        } else {
            return dataSource.getRedisCommands();
        }
    }

    private static <K, V> RedisStringCommands<K, V> getRedisStringCommands(RedisDataSource<K, V> dataSource) {
        if (isClusterConnection(dataSource)) {
            return dataSource.getRedisClusterCommands();
        } else {
            return dataSource.getRedisCommands();
        }
    }

    private static <K, V> RedisListCommands<K, V> getRedisListCommands(RedisDataSource<K, V> dataSource) {
        if (isClusterConnection(dataSource)) {
            return dataSource.getRedisClusterCommands();
        } else {
            return dataSource.getRedisCommands();
        }
    }

    private static <K, V> RedisSetCommands<K, V> getRedisSetCommands(RedisDataSource<K, V> dataSource) {
        if (isClusterConnection(dataSource)) {
            return dataSource.getRedisClusterCommands();
        } else {
            return dataSource.getRedisCommands();
        }
    }

    private static <K, V> RedisSortedSetCommands<K, V> getRedisSortedSetCommands(RedisDataSource<K, V> dataSource) {
        if (isClusterConnection(dataSource)) {
            return dataSource.getRedisClusterCommands();
        } else {
            return dataSource.getRedisCommands();
        }
    }

    private static <K, V> RedisHashCommands<K, V> getRedisHashCommands(RedisDataSource<K, V> dataSource) {
        if (isClusterConnection(dataSource)) {
            return dataSource.getRedisClusterCommands();
        } else {
            return dataSource.getRedisCommands();
        }
    }

    private static <K, V> RedisKeyCommands<K, V> getRedisKeyCommands(RedisDataSource<K, V> dataSource) {
        if (isClusterConnection(dataSource)) {
            return dataSource.getRedisClusterCommands();
        } else {
            return dataSource.getRedisCommands();
        }
    }
}
