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
import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.util.exceptions.BallerinaException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * {@code {@link AbstractRedisAction}} is the base class for all Redis connector actions.
 *
 * @since 0.5.0
 */
public abstract class AbstractRedisAction extends BlockingNativeCallableUnit {
    protected static final String MUST_NOT_BE_NULL = "must not be null";
    private static final String KEY_MUST_NOT_BE_NULL = "Key " + MUST_NOT_BE_NULL;
    private static final String KEYS_MUST_NOT_BE_NULL = "Key(s) " + MUST_NOT_BE_NULL;
    private static final String ARGUMENTS_MUST_NOT_BE_NULL = "Arguments " + MUST_NOT_BE_NULL;

    protected AbstractRedisAction() {
    }
    
    private <K, V> Object getRedisCommands(RedisDataSource<K, V> redisDataSource) {
        if (isClusterConnection(redisDataSource)) {
            return redisDataSource.getRedisClusterCommands();
        } else {
            return redisDataSource.getRedisCommands();
        }
    }

    //String Commands

    protected <K, V> BString set(K key, V value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            return new BString(redisCommands.set(key, value));
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString get(K key, RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.get(key);
            return result == null ? null : new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected BInteger append(String key, String value, RedisDataSource<String, String> redisDataSource) {
        RedisStringCommands<String, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<String, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.append(key, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected BInteger bitCount(String key, RedisDataSource<String, String> redisDataSource) {
        RedisStringCommands<String, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<String, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.bitcount(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger bitopAnd(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.bitopAnd(destination, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger bitopOr(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.bitopOr(destination, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger bitopNot(K destination, K key, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.bitopNot(destination, key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger bitopXor(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.bitopXor(destination, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger decr(K key, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.decr(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger decrBy(K key, int value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.decrby(key, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger getBit(K key, int offset, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.getbit(key, offset);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString getRange(K key, int start, int end, RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.getrange(key, start, end);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString getSet(K key, String value, RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.getset(key, value);
            return result == null ? null : new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger incr(K key, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.incr(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger incrBy(K key, int value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.incrby(key, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BFloat incrByFloat(K key, double value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Double result = redisCommands.incrbyfloat(key, value);
            return new BFloat(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BMap mGet(RedisDataSource<K, String> redisDataSource, K... key) {
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

    protected <K, V> BString mSet(Map<K, V> map, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            String result = redisCommands.mset(map);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BBoolean mSetnx(Map<K, V> map, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.msetnx(map);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString pSetex(K key, String value, long expirationPeriodMS,
            RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.psetex(key, expirationPeriodMS, value);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger setBit(K key, int value, long offset, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.setbit(key, offset, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString setEx(K key, String value, long expirationPeriodSeconds,
            RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.setex(key, expirationPeriodSeconds, value);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BBoolean setNx(K key, String value, RedisDataSource<K, String> redisDataSource) {
        RedisStringCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, String>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.setnx(key, value);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger setRange(K key, long offset, V value, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            long result = redisCommands.setrange(key, offset, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger strln(K key, RedisDataSource<K, V> redisDataSource) {
        RedisStringCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<K, V>) getRedisCommands(redisDataSource);
            long result = redisCommands.strlen(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    //List Commands
    protected <K, V> BInteger lPush(K key, RedisDataSource<K, V> redisDataSource, V... value) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.lpush(key, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString lPop(K key, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.lpop(key);
            return result == null ? null : new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger lPushX(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.lpushx(key, values);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BMap<K, BString> bLPop(long timeout, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            KeyValue<K, String> result = redisCommands.blpop(timeout, keys);
            if (result != null) {
                BMap<K, BString> bMap = new BMap<>();
                bMap.put(result.getKey(), new BString(result.getValue()));
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

    protected <K> BMap<K, BString> bRPop(long timeout, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            KeyValue<K, String> result = redisCommands.brpop(timeout, keys);
            if (result != null) {
                BMap<K, BString> bMap = new BMap<>();
                bMap.put(result.getKey(), new BString(result.getValue()));
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

    protected <K> BString brPopLPush(K source, K destination, long timeout,
            RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.brpoplpush(timeout, source, destination);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString lIndex(K key, long index, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.lindex(key, index);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger lInsert(K key, boolean before, V pivot, V value, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.linsert(key, before, pivot, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger lLen(K key, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.llen(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray lRange(K key, long start, long stop, RedisDataSource<K, String> redisDataSource) {
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

    protected <K, V> BInteger lRem(K key, long count, V value, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.lrem(key, count, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BString lSet(K key, long index, V value, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            String result = redisCommands.lset(key, index, value);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BString lTrim(K key, long start, long stop, RedisDataSource<K, V> redisDataSource) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            String result = redisCommands.ltrim(key, start, stop);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString rPop(K key, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.rpop(key);
            return result == null ? null : new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString rPopLPush(K source, K destination, RedisDataSource<K, String> redisDataSource) {
        RedisListCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.rpoplpush(source, destination);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger rPush(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.rpush(key, values);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger rPushX(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        RedisListCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisListCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.rpushx(key, values);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    //Set Commands

    protected <K, V> BInteger sAdd(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        RedisSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.sadd(key, values);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger sCard(K key, RedisDataSource<K, V> redisDataSource) {
        RedisSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.scard(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray sDiff(RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Set<String> result = redisCommands.sdiff(keys);
            BStringArray valueArray = createBStringArrayFromSet(result);
            return valueArray;
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BInteger sDiffStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.sdiffstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray sInter(RedisDataSource<K, String> redisDataSource, K... keys) {
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

    protected <K> BInteger sInterStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.sinterstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BBoolean sIsMember(K key, V value, RedisDataSource<K, V> redisDataSource) {
        RedisSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.sismember(key, value);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray sMembers(K key, RedisDataSource<K, String> redisDataSource) {
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

    protected <K, V> BBoolean sMove(K source, K dest, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.smove(source, dest, member);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray sPop(K key, int count, RedisDataSource<K, String> redisDataSource) {
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

    protected <K> BStringArray sRandMember(K key, int count, RedisDataSource<K, String> redisDataSource) {
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

    protected <K> BInteger sRem(K key, RedisDataSource<K, String> redisDataSource, String... members) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.srem(key, members);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray sUnion(RedisDataSource<K, String> redisDataSource, K... keys) {
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

    protected <K> BInteger sUnionStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        RedisSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSetCommands<K, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.sunionstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Argements " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> void close(RedisDataSource<K, V> redisDataSource) {
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

    protected <K, V> BInteger zAdd(K key, RedisDataSource<K, V> redisDataSource, Map<V, Double> valueScoreMap) {
        ScoredValue<V>[] scoredValues = createArrayFromScoredValueMap(valueScoreMap);
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zadd(key, scoredValues);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Members " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger zCard(K key, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zcard(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger zCount(K key, double min, double max, RedisDataSource<K, V> redisDataSource) {
        Range<Double> range = Range.create(min, max);
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zcount(key, range);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BFloat zIncrBy(K key, double amount, K member, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Double result = redisCommands.zincrby(key, amount, member);
            return new BFloat(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger zInterStore(K dest, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zinterstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger zLexCount(K key, V min, V max, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Range<V> range = Range.create(min, max);
            Long result = redisCommands.zlexcount(key, range);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray zRange(K key, long min, long max, RedisDataSource<K, String> redisDataSource) {
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

    protected <K> BStringArray zRangeByLex(K key, String min, String max, RedisDataSource<K, String> redisDataSource) {
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

    protected <K> BStringArray zRevRangeByLex(K key, String min, String max,
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

    protected <K> BStringArray zRangeByScore(K key, double min, double max,
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

    protected <K, V> BInteger zRank(K key, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zrank(key, member);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger zRem(K key, RedisDataSource<K, V> redisDataSource, V... members) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zrem(key, members);
            return new BInteger(result);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BInteger zRemRangeByLex(K key, String min, String max, RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Range<String> range = Range.create(min, max);
            Long result = redisCommands.zremrangebylex(key, range);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BInteger zRemRangeByRank(K key, long min, long max, RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zremrangebyrank(key, min, max);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BInteger zRemRangeByScore(K key, double min, double max, RedisDataSource<K, String> redisDataSource) {
        RedisSortedSetCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, String>) getRedisCommands(redisDataSource);
            Range<Double> range = Range.create(min, max);
            Long result = redisCommands.zremrangebyscore(key, range);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(ARGUMENTS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray zRevRange(K key, long min, long max, RedisDataSource<K, String> redisDataSource) {
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

    protected <K> BStringArray zRevRangeByScore(K key, double min, double max,
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

    protected <K, V> BInteger zRevRank(K key, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zrevrank(key, member);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BFloat zScore(K key, V member, RedisDataSource<K, V> redisDataSource) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            double result = redisCommands.zscore(key, member);
            return new BFloat(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger zUnionStore(K dest, RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisSortedSetCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisSortedSetCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.zunionstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Destination key/source key(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    // Hash Commands

    protected <K, V> BInteger hDel(K key, RedisDataSource<K, V> redisDataSource, K... fields) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.hdel(key, fields);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BBoolean hExists(K key, K field, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.hexists(key, field);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString hGet(K key, K field, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.hget(key, field);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BMap<K, BString> hGetAll(K key, RedisDataSource<K, String> redisDataSource) {
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

    protected <K> BInteger hIncrBy(K key, K field, long amount, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.hincrby(key, field, amount);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BFloat hIncrByFloat(K key, K field, double amount, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            Double result = redisCommands.hincrbyfloat(key, field, amount);
            return new BFloat(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected BStringArray hKeys(String key, RedisDataSource<String, String> redisDataSource) {
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

    protected <K> BInteger hLen(K key, RedisDataSource<K, String> redisDataSource) {
        RedisHashCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, String>) getRedisCommands(redisDataSource);
            Long result = redisCommands.hlen(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BMap<K, BString> hMGet(K key, RedisDataSource<K, String> redisDataSource, K... fields) {
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

    protected <K, V> BString hMSet(K key, Map<K, V> fieldValueMap, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            String result = redisCommands.hmset(key, fieldValueMap);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BBoolean hSet(K key, K field, V value, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.hset(key, field, value);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BBoolean hSetNx(K key, K field, V value, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.hsetnx(key, field, value);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger hStrln(K key, K field, RedisDataSource<K, V> redisDataSource) {
        RedisHashCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisHashCommands<K, V>) getRedisCommands(redisDataSource);
            long result = redisCommands.hstrlen(key, field);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray hVals(K key, RedisDataSource<K, String> redisDataSource) {
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
    protected <K, V> BInteger del(RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.del(keys);
            return new BInteger(result);
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

    protected <K, V> BInteger exists(RedisDataSource<K, V> redisDataSource, K... keys) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            Long result = redisCommands.exists(keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEYS_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BBoolean expire(K key, long seconds, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.expire(key, seconds);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <V> BStringArray keys(String pattern, RedisDataSource<String, V> redisDataSource) {
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

    protected <K, V> BBoolean move(K key, int db, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.move(key, db);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BBoolean persist(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.persist(key);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BBoolean pExpire(K key, long milliSeconds, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.pexpire(key, milliSeconds);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BInteger pTtl(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            long result = redisCommands.pttl(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString randomKey(RedisDataSource<K, String> redisDataSource) {
        RedisKeyCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.randomkey();
            return result == null ? null : new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString rename(K key, K newName, RedisDataSource<K, String> redisDataSource) {
        RedisKeyCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.rename(key, newName);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BBoolean renameNx(K key, K newName, RedisDataSource<K, String> redisDataSource) {
        RedisKeyCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, String>) getRedisCommands(redisDataSource);
            boolean result = redisCommands.renamenx(key, newName);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BStringArray sort(K key, RedisDataSource<K, String> redisDataSource) {
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

    protected <K, V> BInteger ttl(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            long result = redisCommands.ttl(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K, V> BString type(K key, RedisDataSource<K, V> redisDataSource) {
        RedisKeyCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisKeyCommands<K, V>) getRedisCommands(redisDataSource);
            String result = redisCommands.type(key);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException(KEY_MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    //Connection commands

    protected <K, V> BString auth(String password, RedisDataSource<K, V> redisDataSource) {
        RedisCommands<K, V> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, V>) getRedisCommands(redisDataSource);
            String result = redisCommands.auth(password);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Password " + MUST_NOT_BE_NULL);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString echo(String message, RedisDataSource<K, String> redisDataSource) {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.echo(message);
            return new BString(result);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    protected <K> BString ping(RedisDataSource<K, String> redisDataSource) {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, String>) getRedisCommands(redisDataSource);
            String result = redisCommands.ping();
            return new BString(result);
        } finally {
            releaseResources(redisCommands, redisDataSource);
        }
    }

    private boolean isClusterConnection(RedisDataSource redisDataSource) {
        return redisDataSource.isClusterConnection();
    }

    private BStringArray createBStringArrayFromSet(Set<String> set) {
        BStringArray bStringArray = new BStringArray();
        int i = 0;
        for (String item : set) {
            bStringArray.add(i, item);
            i++;
        }
        return bStringArray;
    }

    private BStringArray createBStringArrayFromList(List<String> list) {
        BStringArray bStringArray = new BStringArray();
        int i = 0;
        for (String item : list) {
            bStringArray.add(i, item);
            i++;
        }
        return bStringArray;
    }

    private <V> ScoredValue<V>[] createArrayFromScoredValueMap(Map<V, Double> valueScoreMap) {
        ScoredValue<V>[] scoredValues = new ScoredValue[valueScoreMap.size()];
        int i = 0;
        for (Map.Entry<V, Double> entry : valueScoreMap.entrySet()) {
            scoredValues[i] = ScoredValue.fromNullable(entry.getValue(), entry.getKey());
            i++;
        }
        return scoredValues;
    }

    private <K> BMap<K, BString> createBMapFromMap(Map<K, String> map) {
        BMap<K, BString> bMap = new BMap<>();
        map.forEach((key, value) -> bMap.put(key, new BString(value)));
        return bMap;
    }

    private <K> BMap<K, BString> createBMapFromKeyValueList(List<KeyValue<K, String>> list) {
        BMap<K, BString> bMap = new BMap<>();
        for (KeyValue<K, String> item : list) {
            String value;
            try {
                value = item.getValue();
            } catch (NoSuchElementException e) {
                value = null;
            }
            bMap.put(item.getKey(), new BString(value));
        }
        return bMap;
    }

    protected String[] createArrayFromBStringArray(BStringArray bStringArray) {
        String[] stringArray = new String[(int) bStringArray.size()];
        for (int i = 0; i < bStringArray.size(); i++) {
            stringArray[i] = bStringArray.get(i);
        }
        return stringArray;
    }

    protected Map<String, String> createMapFromBMap(BMap<String, BString> bMap) {
        Map<String, String> map = new HashMap<>(bMap.size());
        bMap.getMap().keySet().forEach(item -> map.put(item, bMap.get(item).stringValue()));
        return map;
    }

    protected void setNullableReturnValues(BValue result, Context context) {
        if (result == null) {
            context.setReturnValues();
        } else {
            context.setReturnValues(result);
        }
    }

    private <K, V> void releaseResources(Object redisCommands, RedisDataSource<K, V> redisDataSource) {
        if (redisDataSource.isPoolingEnabled() && redisCommands != null) {
            redisDataSource.releaseResources(redisCommands);
        }
    }
}
