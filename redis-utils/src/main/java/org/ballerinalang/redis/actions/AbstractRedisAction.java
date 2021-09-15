/*
 * Copyright (c) 2018, WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
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

import io.lettuce.core.KeyValue;
import io.lettuce.core.Range;
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.api.sync.RedisHashCommands;
import io.lettuce.core.api.sync.RedisKeyCommands;
import io.lettuce.core.api.sync.RedisListCommands;
import io.lettuce.core.api.sync.RedisSetCommands;
import io.lettuce.core.api.sync.RedisSortedSetCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.internal.types.BArrayType;
import io.ballerina.runtime.api.PredefinedTypes;
import io.ballerina.runtime.api.Module;
import org.ballerinalang.core.model.types.BTypes;
import io.ballerina.runtime.internal.values.MapValue;
import io.ballerina.runtime.internal.values.MapValueImpl;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.runtime.api.creators.ValueCreator;
import org.ballerinalang.redis.RedisDataSource;
import io.ballerina.runtime.internal.util.exceptions.BallerinaException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * {@code {@link AbstractRedisAction}} is the base class for all Redis connector actions.
 *
 * @since 0.5.0
 */
public abstract class AbstractRedisAction {
    private static final String MUST_NOT_BE_NULL = "must not be null";
    private static final String KEY_MUST_NOT_BE_NULL = "Key " + MUST_NOT_BE_NULL;
    private static final String KEYS_MUST_NOT_BE_NULL = "Key(s) " + MUST_NOT_BE_NULL;
    private static final String ARGUMENTS_MUST_NOT_BE_NULL = "Arguments " + MUST_NOT_BE_NULL;

    protected AbstractRedisAction() {
    }

    private static <K, V> Object getRedisCommands(RedisDataSource<K, V> redisDataSource) {
        if (isClusterConnection(redisDataSource)) {
            return redisDataSource.getRedisClusterCommands();
        } else {
            return redisDataSource.getRedisCommands();
        }
    }

    //String Commands

    static <K, V> String set(K key, V value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.set(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String get(K key, RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.get(key);
            return result == null ? null : result;
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static long append(String key, String value, RedisDataSource<String, String> redisDataSource) {
        RedisStringCommands<String, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<String, String>) getRedisCommands(redisDataSource);
            return redisCommands.append(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static long bitCount(String key, RedisDataSource<String, String> redisDataSource) {
        RedisStringCommands<String, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<String, String>) getRedisCommands(redisDataSource);
            return redisCommands.bitcount(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long bitopAnd(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.bitopAnd(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long bitopOr(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.bitopOr(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long bitopNot(K destination, K key, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.bitopNot(destination, key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long bitopXor(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.bitopXor(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long decr(K key, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.decr(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long decrBy(K key, int value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.decrby(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long getBit(K key, int offset, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.getbit(key, offset);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String getRange(K key, int start, int end, RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.getrange(key, start, end);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String getSet(K key, String value, RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.getset(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long incr(K key, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.incr(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long incrBy(K key, int value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.incrby(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> double incrByFloat(K key, double value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.incrbyfloat(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, BString> mGet(RedisDataSource<K, String> redisDataSource, K... key) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            List<KeyValue<K, String>> result = redisCommands.mget(key);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> String mSet(Map<K, V> map, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.mset(map);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean mSetnx(Map<K, V> map, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.msetnx(map);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String pSetex(K key, String value, long expirationPeriodMS,
                              RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.psetex(key, expirationPeriodMS, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long setBit(K key, int value, long offset, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.setbit(key, offset, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String setEx(K key, String value, long expirationPeriodSeconds,
                             RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.setex(key, expirationPeriodSeconds, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> boolean setNx(K key, String value, RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.setnx(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long setRange(K key, long offset, V value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.setrange(key, offset, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long strln(K key, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.strlen(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    //List Commands
    static <K, V> long lPush(K key, RedisDataSource<K, V> redisDataSource, V... value) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.lpush(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String lPop(K key, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.lpop(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long lPushX(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.lpushx(key, values);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, BString> bLPop(long timeout, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            KeyValue<K, String> result = redisCommands.blpop(timeout, keys);
            if (result != null) {
                MapValue<BString, BString> bMap = new MapValueImpl<>();
                bMap.put(StringUtils.fromString((String) result.getKey()), StringUtils.fromString(result.getValue()));
                return bMap;
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, BString> bRPop(long timeout, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            KeyValue<K, String> result = redisCommands.brpop(timeout, keys);
            if (result != null) {
                MapValue<BString, BString> bMap = new MapValueImpl<>();
                bMap.put(StringUtils.fromString((String) result.getKey()), StringUtils.fromString(result.getValue()));
                return bMap;
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> String brPopLPush(K source, K destination, long timeout,
            RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.brpoplpush(timeout, source, destination);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String lIndex(K key, long index, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.lindex(key, index);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long lInsert(K key, boolean before, V pivot, V value,
                                             RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.linsert(key, before, pivot, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long lLen(K key, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.llen(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray lRange(K key, long start, long stop, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            List<String> result = redisCommands.lrange(key, start, stop);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long lRem(K key, long count, V value, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.lrem(key, count, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> String lSet(K key, long index, V value, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.lset(key, index, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> String lTrim(K key, long start, long stop, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.ltrim(key, start, stop);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String rPop(K key, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.rpop(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String rPopLPush(K src, K destination, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.rpoplpush(src, destination);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long rPush(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.rpush(key, values);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> long rPushX(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.rpushx(key, values);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    //Set Commands

    static <K, V> long sAdd(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        RedisSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.sadd(key, values);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long sCard(K key, RedisDataSource<K, V> redisDataSource) {
        RedisSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.scard(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray sDiff(RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Set<String> result = redisCommands.sdiff(keys);
            BArray valueArray = createBStringArrayFromSet(result);
            return valueArray;
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long sDiffStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.sdiffstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray sInter(RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Set<String> result = redisCommands.sinter(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long sInterStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.sinterstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean sIsMember(K key, V value, RedisDataSource<K, V> redisDataSource) {
        RedisSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.sismember(key, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray sMembers(K key, RedisDataSource<K, String> redisDataSource) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Set<String> result = redisCommands.smembers(key);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean sMove(K source, K dest, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.smove(source, dest, member);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray sPop(K key, int count, RedisDataSource<K, String> redisDataSource) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Set<String> result = redisCommands.spop(key, count);
            return (result == null || result.isEmpty()) ? null : createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray sRandMember(K key, int count, RedisDataSource<K, String> redisDataSource) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            List<String> result = redisCommands.srandmember(key, count);
            return (result == null || result.isEmpty()) ? null : createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long sRem(K key, RedisDataSource<K, String> redisDataSource, String... members) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.srem(key, members);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray sUnion(RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Set<String> result = redisCommands.sunion(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long sUnionStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.sunionstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Argements " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
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

    // Sorted Set Commands

    static <K, V> long zAdd(K key, RedisDataSource<K, V> redisDataSource, Map<V, Double> valueScoreMap) {
        ScoredValue<V>[] scoredValues = createArrayFromScoredValueMap(valueScoreMap);
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zadd(key, scoredValues);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Members " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long zCard(K key, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zcard(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long zCount(K key, double min, double max, RedisDataSource<K, V> redisDataSource) {
        Range<Double> range = Range.create(min, max);
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zcount(key, range);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> double zIncrBy(K key, double amount, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zincrby(key, amount, member);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long zInterStore(K dest, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zinterstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long zLexCount(K key, V min, V max, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Range<V> range = Range.create(min, max);
            return redisCommands.zlexcount(key, range);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray zRange(K key, long min, long max, RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            List<String> result = redisCommands.zrange(key, min, max);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray zRangeByLex(K key, String min, String max, RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Range<String> range = Range.create(min, max);
            List<String> result = redisCommands.zrangebylex(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray zRevRangeByLex(K key, String min, String max,
                                               RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Range<String> range = Range.create(min, max);
            List<String> result = redisCommands.zrevrangebylex(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments" + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray zRangeByScore(K key, double min, double max,
                                              RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Range<Double> range = Range.create(min, max);
            List<String> result = redisCommands.zrangebyscore(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long zRank(K key, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zrank(key, member);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long zRem(K key, RedisDataSource<K, V> redisDataSource, V... members) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zrem(key, members);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long zRemRangeByLex(K key, String min, String max,
                                                 RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Range<String> range = Range.create(min, max);
            return redisCommands.zremrangebylex(key, range);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long zRemRangeByRank(K key, long min, long max,
                                                  RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.zremrangebyrank(key, min, max);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long zRemRangeByScore(K key, double min, double max,
                                                   RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Range<Double> range = Range.create(min, max);
            return redisCommands.zremrangebyscore(key, range);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray zRevRange(K key, long min, long max, RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            List<String> result = redisCommands.zrevrange(key, min, max);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray zRevRangeByScore(K key, double min, double max,
                                                 RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Range<Double> range = Range.create(min, max);
            List<String> result = redisCommands.zrevrangebyscore(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long zRevRank(K key, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zrevrank(key, member);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> double zScore(K key, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zscore(key, member);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long zUnionStore(K dest, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.zunionstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Destination key/source key(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    // Hash Commands

    static <K, V> long hDel(BString key, RedisDataSource<K, V> redisDataSource, K... fields) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.hdel((K) key.toString(), fields);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean hExists(K key, K field, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.hexists(key, field);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String hGet(K key, K field, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.hget(key, field);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, BString> hGetAll(K key, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            Map<K, String> result = redisCommands.hgetall(key);
            return createBMapFromMap(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long hIncrBy(K key, K field, long amount, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.hincrby(key, field, amount);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> double hIncrByFloat(K key, K field, double amount,
                                             RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.hincrbyfloat(key, field, amount);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static BArray hKeys(String key, RedisDataSource<String, String> redisDataSource) {
        RedisHashCommands<String, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<String, String>) getRedisCommands(redisDataSource);
            List<String> result = redisCommands.hkeys(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> long hLen(K key, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.hlen(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BMap<BString, BString> hMGet(K key, RedisDataSource<K, String> redisDataSource, K... fields) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            List<KeyValue<K, String>> result = redisCommands.hmget(key, fields);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> String hMSet(K key, Map<K, V> fieldValueMap, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.hmset(key, fieldValueMap);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean hSet(K key, K field, V value, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.hset(key, field, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean hSetNx(K key, K field, V value, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.hsetnx(key, field, value);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long hStrln(K key, K field, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.hstrlen(key, field);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray hVals(K key, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            List<String> result = redisCommands.hvals(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    // Key commands
    static <K, V> long del(RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.del(keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    // TODO: Add as a native action once byte type is supported in ballerina. When doing so retrun a BType
    protected <K, V> byte[] dump(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            byte[] result = redisCommands.dump(key);
            return result;
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long exists(RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.exists(keys);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean expire(K key, long seconds, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.expire(key, seconds);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <V> BArray keys(String pattern, RedisDataSource<String, V> redisDataSource) {
        RedisKeyCommands<String, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<String, V>) getRedisCommands(redisDataSource);
            List<String> result = redisCommands.keys(pattern);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean move(K key, int db, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.move(key, db);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean persist(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.persist(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> boolean pExpire(K key, long milliSeconds, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.pexpire(key, milliSeconds);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long pTtl(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.pttl(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String randomKey(RedisDataSource<K, String> redisDataSource) {
        RedisKeyCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.randomkey();
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String rename(K key, K newName, RedisDataSource<K, String> redisDataSource) {
        RedisKeyCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.rename(key, newName);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> boolean renameNx(K key, K newName, RedisDataSource<K, String> redisDataSource) {
        RedisKeyCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.renamenx(key, newName);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> BArray sort(K key, RedisDataSource<K, String> redisDataSource) {
        RedisKeyCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, String>) getRedisCommands(redisDataSource);
            List<String> result = redisCommands.sort(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> long ttl(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.ttl(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K, V> String type(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.type(key);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    //Connection commands

    static <K, V> String auth(String password, RedisDataSource<K, V> redisDataSource) {
        RedisCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, V>) getRedisCommands(redisDataSource);
            return redisCommands.auth(password);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Password " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String echo(String message, RedisDataSource<K, String> redisDataSource) {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.echo(message);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    static <K> String ping(RedisDataSource<K, String> redisDataSource) {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, String>) getRedisCommands(redisDataSource);
            return redisCommands.ping();
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    private static boolean isClusterConnection(RedisDataSource redisDataSource) {
        return redisDataSource.isClusterConnection();
    }

    private static BArray createBStringArrayFromSet(Set<String> set) {
        BArray bStringArray = ValueCreator.createArrayValue(new BArrayType(PredefinedTypes.TYPE_STRING));
        for (String item : set) {
            bStringArray.append(StringUtils.fromString(item));
        }
        return bStringArray;
    }

    private static BArray createBStringArrayFromList(List<String> list) {
        BArray bStringArray = ValueCreator.createArrayValue(new BArrayType(PredefinedTypes.TYPE_STRING));
        for (String item : list) {
            bStringArray.append(StringUtils.fromString(item));
        }
        return bStringArray;
    }

    private static <V> ScoredValue<V>[] createArrayFromScoredValueMap(Map<V, Double> valueScoreMap) {
        ScoredValue<V>[] scoredValues = new ScoredValue[valueScoreMap.size()];
        int i = 0;
        for (Map.Entry<V, Double> entry : valueScoreMap.entrySet()) {
            scoredValues[i] = ScoredValue.fromNullable(entry.getValue(), entry.getKey());
            i++;
        }
        return scoredValues;
    }

    private static <K> BMap<BString, BString> createBMapFromMap(Map<K, String> map) {
        MapValue<BString, BString> bMap = new MapValueImpl<>();
        map.forEach((key, value) -> bMap.put(StringUtils.fromString((String) key), StringUtils.fromString(value)));
        return bMap;
    }

    private static <K> BMap<BString, BString> createBMapFromKeyValueList(List<KeyValue<K, String>> list) {
        MapValue<BString, BString> bMap = new MapValueImpl<>();
        for (KeyValue<K, String> item : list) {
            String value;
            try {
                value = item.getValue();
            } catch (NoSuchElementException e) {
                value = null;
            }
            bMap.put(StringUtils.fromString((String) item.getKey()), StringUtils.fromString(value));
        }
        return bMap;
    }

    static String[] createStringArrayFromBArray(BArray bStringArray) {
        String[] stringArray = new String[bStringArray.size()];
        int i = 0;
        for (Object item : bStringArray.getStringArray()) {
            stringArray[i++] = item.toString();
        }
        return stringArray;
    }

    static Map<String, Object> createMapFromBMap(BMap<BString, Object> bMap) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<BString, Object> entry : bMap.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return map;
    }

    static BArray createBstringArrayFromBMap(BMap<BString, BString> bMap) {
        BArray bStringArray = ValueCreator.createArrayValue(new BArrayType(PredefinedTypes.TYPE_STRING));
        for (Map.Entry<BString, BString> entry : bMap.entrySet()) {
            bStringArray.append(entry.getValue());
        }
        return bStringArray;
    }

    private static <K, V> void releaseResources(Object redisCommands, RedisDataSource<K, V> redisDataSource) {
        if (redisDataSource.isPoolingEnabled() && redisCommands != null) {
            redisDataSource.releaseResources(redisCommands);
        }
    }
}
