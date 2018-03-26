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

package org.ballerinalang.data.redis.actions;

import io.lettuce.core.KeyValue;
import io.lettuce.core.Range;
import io.lettuce.core.ScoredValue;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.data.redis.RedisDataSource;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStringArray;
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

    protected AbstractRedisAction() {
    }

    //String Commands

    protected <K, V> BString set(K key, V value, RedisDataSource<K, V> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().set(key, value) :
                    redisDataSource.getRedisCommands().set(key, value);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString get(K key, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().get(key) :
                    redisDataSource.getRedisCommands().get(key);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected BInteger append(String key, String value, RedisDataSource<String, String> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().append(key, value) :
                    redisDataSource.getRedisCommands().append(key, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected BInteger bitCount(String key, RedisDataSource<String, String> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().bitcount(key) :
                    redisDataSource.getRedisCommands().bitcount(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger bitopAnd(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().bitopAnd(destination, keys) :
                    redisDataSource.getRedisCommands().bitopAnd(destination, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger bitopOr(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().bitopOr(destination, keys) :
                    redisDataSource.getRedisCommands().bitopOr(destination, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger bitopNot(K destination, K key, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().bitopNot(destination, key) :
                    redisDataSource.getRedisCommands().bitopNot(destination, key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger bitopXor(K destination, RedisDataSource<K, V> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().bitopXor(destination, keys) :
                    redisDataSource.getRedisCommands().bitopXor(destination, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger decr(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().decr(key) :
                    redisDataSource.getRedisCommands().decr(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger decrBy(K key, int value, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().decrby(key, value) :
                    redisDataSource.getRedisCommands().decrby(key, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger getBit(K key, int offset, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().getbit(key, offset) :
                    redisDataSource.getRedisCommands().getbit(key, offset);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString getRange(K key, int start, int end, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().getrange(key, start, end) :
                    redisDataSource.getRedisCommands().getrange(key, start, end);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString getSet(K key, String value, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().getset(key, value) :
                    redisDataSource.getRedisCommands().getset(key, value);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger incr(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().incr(key) :
                    redisDataSource.getRedisCommands().incr(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger incrBy(K key, int value, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().incrby(key, value) :
                    redisDataSource.getRedisCommands().incrby(key, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BFloat incrByFloat(K key, double value, RedisDataSource<K, V> redisDataSource) {
        try {
            Double result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().incrbyfloat(key, value) :
                    redisDataSource.getRedisCommands().incrbyfloat(key, value);
            return new BFloat(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BMap mGet(RedisDataSource<K, String> redisDataSource, K... key) {
        try {
            List<KeyValue<K, String>> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().mget(key) :
                    redisDataSource.getRedisCommands().mget(key);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BString mSet(Map<K, V> map, RedisDataSource<K, V> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().mset(map) :
                    redisDataSource.getRedisCommands().mset(map);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean mSetnx(Map<K, V> map, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().msetnx(map) :
                    redisDataSource.getRedisCommands().msetnx(map);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString pSetex(K key, String value, long expirationPeriodMS,
            RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().psetex(key, expirationPeriodMS, value) :
                    redisDataSource.getRedisCommands().psetex(key, expirationPeriodMS, value);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger setBit(K key, int value, long offset, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().setbit(key, offset, value) :
                    redisDataSource.getRedisCommands().setbit(key, offset, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString setEx(K key, String value, long expirationPeriodSeconds,
            RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().setex(key, expirationPeriodSeconds, value) :
                    redisDataSource.getRedisCommands().setex(key, expirationPeriodSeconds, value);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BBoolean setNx(K key, String value, RedisDataSource<K, String> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().setnx(key, value) :
                    redisDataSource.getRedisCommands().setnx(key, value);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger setRange(K key, long offset, V value, RedisDataSource<K, V> redisDataSource) {
        try {
            long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().setrange(key, offset, value) :
                    redisDataSource.getRedisCommands().setrange(key, offset, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger strln(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().strlen(key) :
                    redisDataSource.getRedisCommands().strlen(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    //List Commands
    protected <K, V> BInteger lPush(K key, RedisDataSource<K, V> redisDataSource, V... value) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().lpush(key, value) :
                    redisDataSource.getRedisCommands().lpush(key, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString lPop(K key, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().lpop(key) :
                    redisDataSource.getRedisCommands().lpop(key);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger lPushX(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().lpush(key, values) :
                    redisDataSource.getRedisCommands().lpushx(key, values);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BMap<K, BString> bLPop(long timeout, RedisDataSource<K, String> redisDataSource, K... keys) {
        try {
            KeyValue<K, String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().blpop(timeout, keys) :
                    redisDataSource.getRedisCommands().blpop(timeout, keys);
            if (result != null) {
                BMap<K, BString> bMap = new BMap<>();
                bMap.put(result.getKey(), new BString(result.getValue()));
                return bMap;
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Keys " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BMap<K, BString> bRPop(long timeout, RedisDataSource<K, String> redisDataSource, K... keys) {
        try {
            KeyValue<K, String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().brpop(timeout, keys) :
                    redisDataSource.getRedisCommands().brpop(timeout, keys);
            if (result != null) {
                BMap<K, BString> bMap = new BMap<>();
                bMap.put(result.getKey(), new BString(result.getValue()));
                return bMap;
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString brPopLPush(K source, K destination, long timeout,
            RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().brpoplpush(timeout, source, destination) :
                    redisDataSource.getRedisCommands().brpoplpush(timeout, source, destination);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Keys " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString lIndex(K key, long index, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().lindex(key, index) :
                    redisDataSource.getRedisCommands().lindex(key, index);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger lInsert(K key, boolean before, V pivot, V value, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().linsert(key, before, pivot, value) :
                    redisDataSource.getRedisCommands().linsert(key, before, pivot, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger lLen(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().llen(key) :
                    redisDataSource.getRedisCommands().llen(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray lRange(K key, long start, long stop, RedisDataSource<K, String> redisDataSource) {
        try {
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().lrange(key, start, stop) :
                    redisDataSource.getRedisCommands().lrange(key, start, stop);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger lRem(K key, long count, V value, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().lrem(key, count, value) :
                    redisDataSource.getRedisCommands().lrem(key, count, value);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BString lSet(K key, long index, V value, RedisDataSource<K, V> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().lset(key, index, value) :
                    redisDataSource.getRedisCommands().lset(key, index, value);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BString lTrim(K key, long start, long stop, RedisDataSource<K, V> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().ltrim(key, start, stop) :
                    redisDataSource.getRedisCommands().ltrim(key, start, stop);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString rPop(K key, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().rpop(key) :
                    redisDataSource.getRedisCommands().rpop(key);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString rPopLPush(K source, K destination, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().rpoplpush(source, destination) :
                    redisDataSource.getRedisCommands().rpoplpush(source, destination);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger rPush(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().rpush(key, values) :
                    redisDataSource.getRedisCommands().rpush(key, values);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger rPushX(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().rpushx(key, values) :
                    redisDataSource.getRedisCommands().rpushx(key, values);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    //Set Commands

    protected <K, V> BInteger sAdd(K key, RedisDataSource<K, V> redisDataSource, V... values) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sadd(key, values) :
                    redisDataSource.getRedisCommands().sadd(key, values);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger sCard(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().scard(key) :
                    redisDataSource.getRedisCommands().scard(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray sDiff(RedisDataSource<K, String> redisDataSource, K... keys) {
        try {
            Set<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sdiff(keys) :
                    redisDataSource.getRedisCommands().sdiff(keys);
            BStringArray valueArray = createBStringArrayFromSet(result);
            return valueArray;
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BInteger sDiffStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sdiffstore(dest, keys) :
                    redisDataSource.getRedisCommands().sdiffstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray sInter(RedisDataSource<K, String> redisDataSource, K... keys) {
        try {
            Set<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sinter(keys) :
                    redisDataSource.getRedisCommands().sinter(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BInteger sInterStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sinterstore(dest, keys) :
                    redisDataSource.getRedisCommands().sinterstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean sIsMember(K key, V value, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sismember(key, value) :
                    redisDataSource.getRedisCommands().sismember(key, value);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray sMembers(K key, RedisDataSource<K, String> redisDataSource) {
        try {
            Set<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().smembers(key) :
                    redisDataSource.getRedisCommands().smembers(key);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean sMove(K source, K dest, V member, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().smove(source, dest, member) :
                    redisDataSource.getRedisCommands().smove(source, dest, member);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Keys " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray sPop(K key, int count, RedisDataSource<K, String> redisDataSource) {
        try {
            Set<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().spop(key, count) :
                    redisDataSource.getRedisCommands().spop(key, count);
            if (result != null) {
                return createBStringArrayFromSet(result);
            } else {
                return null;
            }
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray sRandMember(K key, int count, RedisDataSource<K, String> redisDataSource) {
        try {
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().srandmember(key, count) :
                    redisDataSource.getRedisCommands().srandmember(key, count);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BInteger sRem(K key, RedisDataSource<K, String> redisDataSource, String... members) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().srem(key, members) :
                    redisDataSource.getRedisCommands().srem(key, members);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray sUnion(RedisDataSource<K, String> redisDataSource, K... keys) {
        try {
            Set<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sunion(keys) :
                    redisDataSource.getRedisCommands().sunion(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Keys " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BInteger sUnionStore(K dest, RedisDataSource<K, String> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sunionstore(dest, keys) :
                    redisDataSource.getRedisCommands().sunionstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Argements " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BString quit(RedisDataSource<K, V> redisDataSource) {
        String result = isClusterConnection(redisDataSource) ?
                redisDataSource.getRedisClusterCommands().quit() :
                redisDataSource.getRedisCommands().quit();
        return new BString(result);
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
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zadd(key, scoredValues) :
                    redisDataSource.getRedisCommands().zadd(key, scoredValues);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Members " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger zCard(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zcard(key) :
                    redisDataSource.getRedisCommands().zcard(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger zCount(K key, double min, double max, RedisDataSource<K, V> redisDataSource) {
        Range<Double> range = Range.create(min, max);
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zcount(key, range) :
                    redisDataSource.getRedisCommands().zcount(key, range);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BFloat zIncrBy(K key, double amount, K member, RedisDataSource<K, V> redisDataSource) {
        try {
            Double result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zincrby(key, amount, member) :
                    redisDataSource.getRedisCommands().zincrby(key, amount, member);
            return new BFloat(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger zInterStore(K dest, RedisDataSource<K, V> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zinterstore(dest, keys) :
                    redisDataSource.getRedisCommands().zinterstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger zLexCount(K key, V min, V max, RedisDataSource<K, V> redisDataSource) {
        try {
            Range<V> range = Range.create(min, max);
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zlexcount(key, range) :
                    redisDataSource.getRedisCommands().zlexcount(key, range);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray zRange(K key, long min, long max, RedisDataSource<K, String> redisDataSource) {
        try {
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zrange(key, min, max) :
                    redisDataSource.getRedisCommands().zrange(key, min, max);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray zRangeByLex(K key, String min, String max, RedisDataSource<K, String> redisDataSource) {
        try {
            Range<String> range = Range.create(min, max);
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zrangebylex(key, range) :
                    redisDataSource.getRedisCommands().zrangebylex(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray zRevRangeByLex(K key, String min, String max,
            RedisDataSource<K, String> redisDataSource) {
        try {
            Range<String> range = Range.create(min, max);
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zrevrangebylex(key, range) :
                    redisDataSource.getRedisCommands().zrevrangebylex(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments" + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray zRangeByScore(K key, double min, double max,
            RedisDataSource<K, String> redisDataSource) {
        try {
            Range<Double> range = Range.create(min, max);
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zrangebyscore(key, range) :
                    redisDataSource.getRedisCommands().zrangebyscore(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger zRank(K key, V member, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zrank(key, member) :
                    redisDataSource.getRedisCommands().zrank(key, member);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger zRem(K key, RedisDataSource<K, V> redisDataSource, V... members) {
        Long result = isClusterConnection(redisDataSource) ?
                redisDataSource.getRedisClusterCommands().zrem(key, members) :
                redisDataSource.getRedisCommands().zrem(key, members);
        return new BInteger(result);
    }

    protected <K> BInteger zRemRangeByLex(K key, String min, String max, RedisDataSource<K, String> redisDataSource) {
        try {
            Range<String> range = Range.create(min, max);
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zremrangebylex(key, range) :
                    redisDataSource.getRedisCommands().zremrangebylex(key, range);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BInteger zRemRangeByRank(K key, long min, long max, RedisDataSource<K, String> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zremrangebyrank(key, min, max) :
                    redisDataSource.getRedisCommands().zremrangebyrank(key, min, max);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BInteger zRemRangeByScore(K key, double min, double max, RedisDataSource<K, String> redisDataSource) {
        try {
            Range<Double> range = Range.create(min, max);
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zremrangebyscore(key, range) :
                    redisDataSource.getRedisCommands().zremrangebyscore(key, range);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray zRevRange(K key, long min, long max, RedisDataSource<K, String> redisDataSource) {
        try {
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zrevrange(key, min, max) :
                    redisDataSource.getRedisCommands().zrevrange(key, min, max);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray zRevRangeByScore(K key, double min, double max,
            RedisDataSource<K, String> redisDataSource) {
        try {
            Range<Double> range = Range.create(min, max);
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zrevrangebyscore(key, range) :
                    redisDataSource.getRedisCommands().zrevrangebyscore(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Arguments " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger zRevRank(K key, V member, RedisDataSource<K, V> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zrevrank(key, member) :
                    redisDataSource.getRedisCommands().zrevrank(key, member);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BFloat zScore(K key, V member, RedisDataSource<K, V> redisDataSource) {
        try {
            double result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zscore(key, member) :
                    redisDataSource.getRedisCommands().zscore(key, member);
            return new BFloat(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger zUnionStore(K dest, RedisDataSource<K, V> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().zunionstore(dest, keys) :
                    redisDataSource.getRedisCommands().zunionstore(dest, keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Destination key/source key(s) " + MUST_NOT_BE_NULL);
        }
    }

    // Hash Commands

    protected <K, V> BInteger hDel(K key, RedisDataSource<K, V> redisDataSource, K... fields) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hdel(key, fields) :
                    redisDataSource.getRedisCommands().hdel(key, fields);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean hExists(K key, K field, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hexists(key, field) :
                    redisDataSource.getRedisCommands().hexists(key, field);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString hGet(K key, K field, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hget(key, field) :
                    redisDataSource.getRedisCommands().hget(key, field);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BMap<K, BString> hGetAll(K key, RedisDataSource<K, String> redisDataSource) {
        try {
            Map<K, String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hgetall(key) :
                    redisDataSource.getRedisCommands().hgetall(key);
            return createBMapFromMap(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BInteger hIncrBy(K key, K field, long amount, RedisDataSource<K, String> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hincrby(key, field, amount) :
                    redisDataSource.getRedisCommands().hincrby(key, field, amount);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BFloat hIncrByFloat(K key, K field, double amount, RedisDataSource<K, String> redisDataSource) {
        try {
            Double result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hincrbyfloat(key, field, amount) :
                    redisDataSource.getRedisCommands().hincrbyfloat(key, field, amount);
            return new BFloat(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        }
    }

    protected BStringArray hKeys(String key, RedisDataSource<String, String> redisDataSource) {
        try {
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hkeys(key) :
                    redisDataSource.getRedisCommands().hkeys(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BInteger hLen(K key, RedisDataSource<K, String> redisDataSource) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hlen(key) :
                    redisDataSource.getRedisCommands().hlen(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BMap<K, BString> hMGet(K key, RedisDataSource<K, String> redisDataSource, K... fields) {
        try {
            List<KeyValue<K, String>> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hmget(key, fields) :
                    redisDataSource.getRedisCommands().hmget(key, fields);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BString hMSet(K key, Map<K, V> fieldValueMap, RedisDataSource<K, V> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hmset(key, fieldValueMap) :
                    redisDataSource.getRedisCommands().hmset(key, fieldValueMap);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean hSet(K key, K field, V value, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hset(key, field, value) :
                    redisDataSource.getRedisCommands().hset(key, field, value);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean hSetNx(K key, K field, V value, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hsetnx(key, field, value) :
                    redisDataSource.getRedisCommands().hsetnx(key, field, value);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger hStrln(K key, K field, RedisDataSource<K, V> redisDataSource) {
        try {
            long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hstrlen(key, field) :
                    redisDataSource.getRedisCommands().hstrlen(key, field);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key/field " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray hVals(K key, RedisDataSource<K, String> redisDataSource) {
        try {
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().hvals(key) :
                    redisDataSource.getRedisCommands().hvals(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    // Key commands
    protected <K, V> BInteger del(RedisDataSource<K, V> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().del(keys) :
                    redisDataSource.getRedisCommands().del(keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    // TODO: Add as a native action once byte type is supported in ballerina. When doing so retrun a BType
    protected <K, V> byte[] dump(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            byte[] result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().dump(key) :
                    redisDataSource.getRedisCommands().dump(key);
            return result;
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger exists(RedisDataSource<K, V> redisDataSource, K... keys) {
        try {
            Long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().exists(keys) :
                    redisDataSource.getRedisCommands().exists(keys);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key(s) " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean expire(K key, long seconds, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().expire(key, seconds) :
                    redisDataSource.getRedisCommands().expire(key, seconds);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <V> BStringArray keys(String pattern, RedisDataSource<String, V> redisDataSource) {
        try {
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().keys(pattern) :
                    redisDataSource.getRedisCommands().keys(pattern);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean move(K key, int db, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().move(key, db) :
                    redisDataSource.getRedisCommands().move(key, db);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean persist(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().persist(key) :
                    redisDataSource.getRedisCommands().persist(key);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BBoolean pExpire(K key, long milliSeconds, RedisDataSource<K, V> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().pexpire(key, milliSeconds) :
                    redisDataSource.getRedisCommands().pexpire(key, milliSeconds);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger pTtl(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().pttl(key) :
                    redisDataSource.getRedisCommands().pttl(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString randomKey(RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().randomkey() :
                    redisDataSource.getRedisCommands().randomkey();
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString rename(K key, K newName, RedisDataSource<K, String> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().rename(key, newName) :
                    redisDataSource.getRedisCommands().rename(key, newName);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BBoolean renameNx(K key, K newName, RedisDataSource<K, String> redisDataSource) {
        try {
            boolean result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().renamenx(key, newName) :
                    redisDataSource.getRedisCommands().renamenx(key, newName);
            return new BBoolean(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BStringArray sort(K key, RedisDataSource<K, String> redisDataSource) {
        try {
            List<String> result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().sort(key) :
                    redisDataSource.getRedisCommands().sort(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BInteger ttl(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            long result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().ttl(key) :
                    redisDataSource.getRedisCommands().ttl(key);
            return new BInteger(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    protected <K, V> BString type(K key, RedisDataSource<K, V> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().type(key) :
                    redisDataSource.getRedisCommands().type(key);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Key " + MUST_NOT_BE_NULL);
        }
    }

    //Connection commands

    protected <K, V> BString auth(String password, RedisDataSource<K, V> redisDataSource) {
        try {
            String result = isClusterConnection(redisDataSource) ?
                    redisDataSource.getRedisClusterCommands().auth(password) :
                    redisDataSource.getRedisCommands().auth(password);
            return new BString(result);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Password " + MUST_NOT_BE_NULL);
        }
    }

    protected <K> BString echo(String message, RedisDataSource<K, String> redisDataSource) {
        String result = isClusterConnection(redisDataSource) ?
                redisDataSource.getRedisClusterCommands().echo(message) :
                redisDataSource.getRedisCommands().echo(message);
        return new BString(result);
    }

    protected <K> BString ping(RedisDataSource<K, String> redisDataSource) {
        String result = isClusterConnection(redisDataSource) ?
                redisDataSource.getRedisClusterCommands().ping() :
                redisDataSource.getRedisCommands().ping();
        return new BString(result);
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
        bMap.keySet().forEach(item -> map.put(item, bMap.get(item).stringValue()));
        return map;
    }
}
