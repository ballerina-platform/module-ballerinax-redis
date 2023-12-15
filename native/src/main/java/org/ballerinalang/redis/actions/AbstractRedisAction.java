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
import io.lettuce.core.api.sync.RedisSetCommands;
import io.lettuce.core.api.sync.RedisSortedSetCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import org.ballerinalang.redis.connection.RedisConnectionManager;
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

    // Native redis connection object, which is stored in Ballerina redis client object
    public static final String CONN_OBJ = "CONNECTION_OBJECT";

    private static final String MUST_NOT_BE_NULL = "must not be null";
    private static final String KEY_MUST_NOT_BE_NULL = "Key " + MUST_NOT_BE_NULL;
    private static final String KEYS_MUST_NOT_BE_NULL = "Key(s) " + MUST_NOT_BE_NULL;
    private static final String ARGUMENTS_MUST_NOT_BE_NULL = "Arguments " + MUST_NOT_BE_NULL;
    private static final String REDIS_SERVER_ERROR = "Redis server error: ";

    // ========================== String Commands  =============================== //

    static <K, V> String set(K key, V value, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.set(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K> String get(K key, RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.get(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static long append(String key, String value, RedisConnectionManager<String, String> connManager)
            throws RedisConnectorException {
        RedisStringCommands<String, String> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.append(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static long bitCount(String key, RedisConnectionManager<String, String> connManager)
            throws RedisConnectorException {
        RedisStringCommands<String, String> redisCommands = null;
        try {
            redisCommands = connManager.getStringCommandConnection();
            return redisCommands.bitcount(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    static <K, V> long bitopAnd(K destination, RedisConnectionManager<K, V> connManager, K[] keys)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.bitopAnd(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long bitopOr(K destination, RedisConnectionManager<K, V> connManager, K[] keys)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.bitopOr(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long bitopNot(K destination, K key, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.bitopNot(destination, key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long bitopXor(K destination, RedisConnectionManager<K, V> connManager, K[] keys)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.bitopXor(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long decr(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.decr(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long decrBy(K key, int value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.decrby(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long getBit(K key, int offset, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.getbit(key, offset);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K> String getRange(K key, int start, int end, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.getrange(key, start, end);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K> String getSet(K key, String value, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.getset(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long incr(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.incr(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long incrBy(K key, int value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.incrby(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> double incrByFloat(K key, double value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.incrbyfloat(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K> BMap<BString, Object> mGet(RedisConnectionManager<K, String> connManager, K[] key)
            throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            List<KeyValue<K, String>> result = stringCommands.mget(key);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> String mSet(Map<K, V> map, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.mset(map);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> boolean mSetnx(Map<K, V> map, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.msetnx(map);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K> String pSetex(K key, String value, long expirationPeriodMs,
                             RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.psetex(key, expirationPeriodMs, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long setBit(K key, int value, long offset, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.setbit(key, offset, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K> String setEx(K key, String value, long expirationPeriodSeconds,
                            RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.setex(key, expirationPeriodSeconds, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K> boolean setNx(K key, String value, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.setnx(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long setRange(K key, long offset, V value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.setrange(key, offset, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    static <K, V> long strln(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = connManager.getStringCommandConnection();
            return stringCommands.strlen(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    //// ========================== List Commands  =============================== //

    static <K, V> long lPush(K key, RedisConnectionManager<K, V> connManager, V[] value)
            throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.lpush(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K> String lPop(K key, RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.lpop(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K, V> long lPushX(K key, RedisConnectionManager<K, V> connManager, V[] values)
            throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.lpushx(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K> BMap<BString, Object> bLPop(long timeout, RedisConnectionManager<K, String> connManager, K[] keys)
            throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
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
            connManager.releaseResources(listCommands);
        }
    }

    static <K> BMap<BString, Object> bRPop(long timeout, RedisConnectionManager<K, String> connManager, K[] keys)
            throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
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
            connManager.releaseResources(listCommands);
        }
    }

    static <K> String lIndex(K key, long index, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.lindex(key, index);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K, V> long lInsert(K key, boolean before, V pivot, V value,
                               RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.linsert(key, before, pivot, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K, V> long lLen(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.llen(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K> BArray lRange(K key, long start, long stop, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            List<String> result = listCommands.lrange(key, start, stop);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K, V> long lRem(K key, long count, V value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.lrem(key, count, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K, V> String lSet(K key, long index, V value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.lset(key, index, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K, V> String lTrim(K key, long start, long stop, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.ltrim(key, start, stop);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K> String rPop(K key, RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.rpop(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K> String rPopLPush(K src, K destination, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.rpoplpush(src, destination);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    static <K, V> long rPush(K key, RedisConnectionManager<K, V> connManager, V[] values)
            throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.rpush(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    protected <K> String brPopLPush(K source, K dest, long timeout, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.brpoplpush(timeout, source, dest);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    protected <K, V> long rPushX(K key, RedisConnectionManager<K, V> connManager, V[] values)
            throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = connManager.getListCommandConnection();
            return listCommands.rpushx(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    // ============================= Set Commands  ================================== //

    static <K, V> long sAdd(K key, RedisConnectionManager<K, V> connManager, V[] values)
            throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            return setCommands.sadd(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K, V> long sCard(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            return setCommands.scard(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> BArray sDiff(RedisConnectionManager<K, String> connManager, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            Set<String> result = setCommands.sdiff(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> long sDiffStore(K dest, RedisConnectionManager<K, String> connManager, K[] keys)
            throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            return setCommands.sdiffstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> BArray sInter(RedisConnectionManager<K, String> connManager, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            Set<String> result = setCommands.sinter(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> long sInterStore(K dest, RedisConnectionManager<K, String> connManager, K[] keys)
            throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            return setCommands.sinterstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K, V> boolean sIsMember(K key, V value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            return setCommands.sismember(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> BArray sMembers(K key, RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            Set<String> result = setCommands.smembers(key);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K, V> boolean sMove(K source, K dest, V member, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            return setCommands.smove(source, dest, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> BArray sPop(K key, int count, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            Set<String> result = setCommands.spop(key, count);
            return (result == null || result.isEmpty()) ? null : createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> BArray sRandMember(K key, int count, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            List<String> result = setCommands.srandmember(key, count);
            return (result == null || result.isEmpty()) ? null : createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> long sRem(K key, RedisConnectionManager<K, String> connManager, String[] members)
            throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            return setCommands.srem(key, members);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> BArray sUnion(RedisConnectionManager<K, String> connManager, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            Set<String> result = setCommands.sunion(keys);
            return createBStringArrayFromSet(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    static <K> long sUnionStore(K dest, RedisConnectionManager<K, String> connManager, K[] keys)
            throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = connManager.getSetCommandConnection();
            return setCommands.sunionstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Argements " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    // ============================= Sorted Set Commands  ================================== //

    static <K, V> long zAdd(K key, RedisConnectionManager<K, V> connManager, Map<V, Double> valueScoreMap)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zadd(key, createArrayFromScoredValueMap(valueScoreMap));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Members " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> long zCard(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zcard(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> long zCount(K key, double min, double max, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        Range<Double> range = Range.create(min, max);
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zcount(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> double zIncrBy(K key, double amount, V member, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zincrby(key, amount, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> long zInterStore(K dest, RedisConnectionManager<K, V> connManager, K[] keys)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zinterstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> long zLexCount(K key, V min, V max, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            Range<V> range = Range.create(min, max);
            return sortedSetCommands.zlexcount(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> BArray zRange(K key, long min, long max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            List<String> result = sortedSetCommands.zrange(key, min, max);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> BArray zRangeByLex(K key, String min, String max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            Range<String> range = Range.create(min, max);
            List<String> result = sortedSetCommands.zrangebylex(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> BArray zRevRangeByLex(K key, String min, String max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            Range<String> range = Range.create(min, max);
            List<String> result = sortedSetCommands.zrevrangebylex(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Arguments" + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> BArray zRangeByScore(K key, double min, double max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            Range<Double> range = Range.create(min, max);
            List<String> result = sortedSetCommands.zrangebyscore(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> long zRank(K key, V member, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zrank(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> long zRem(K key, RedisConnectionManager<K, V> connManager, V[] members)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zrem(key, members);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> long zRemRangeByLex(K key, String min, String max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            Range<String> range = Range.create(min, max);
            return sortedSetCommands.zremrangebylex(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> long zRemRangeByRank(K key, long min, long max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zremrangebyrank(key, min, max);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> long zRemRangeByScore(K key, double min, double max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            Range<Double> range = Range.create(min, max);
            return sortedSetCommands.zremrangebyscore(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> BArray zRevRange(K key, long min, long max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            List<String> result = sortedSetCommands.zrevrange(key, min, max);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K> BArray zRevRangeByScore(K key, double min, double max, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            Range<Double> range = Range.create(min, max);
            List<String> result = sortedSetCommands.zrevrangebyscore(key, range);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> long zRevRank(K key, V member, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zrevrank(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> double zScore(K key, V member, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zscore(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    static <K, V> long zUnionStore(K dest, RedisConnectionManager<K, V> connManager, K[] keys)
            throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zunionstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Destination key/source key(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    // ============================= Hash Commands  ================================== //

    static <K, V> long hDel(BString key, RedisConnectionManager<K, V> connManager, K[] fields)
            throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hdel((K) key.toString(), fields);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K, V> boolean hExists(K key, K field, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hexists(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K> String hGet(K key, K field, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hget(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K> BMap<BString, Object> hGetAll(K key, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            Map<K, String> result = hashCommands.hgetall(key);
            return createBMapFromMap(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K> long hIncrBy(K key, K field, long amount, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hincrby(key, field, amount);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K> double hIncrByFloat(K key, K field, double amount, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hincrbyfloat(key, field, amount);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static BArray hKeys(String key, RedisConnectionManager<String, String> connManager) throws RedisConnectorException {
        RedisHashCommands<String, String> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            List<String> result = hashCommands.hkeys(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K> long hLen(K key, RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hlen(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K> BMap<BString, Object> hMGet(K key, RedisConnectionManager<K, String> connManager, K[] fields)
            throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            List<KeyValue<K, String>> result = hashCommands.hmget(key, fields);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K, V> String hMSet(K key, Map<K, V> fieldValueMap, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hmset(key, fieldValueMap);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K, V> boolean hSet(K key, K field, V value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hset(key, field, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K, V> boolean hSetNx(K key, K field, V value, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hsetnx(key, field, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K, V> long hStrln(K key, K field, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            return hashCommands.hstrlen(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    static <K> BArray hVals(K key, RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = connManager.getHashCommandConnection();
            List<String> result = hashCommands.hvals(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    // ============================= Key Commands  ================================== //

    static <K, V> long del(RedisConnectionManager<K, V> connManager, K[] keys) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.del(keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K, V> long exists(RedisConnectionManager<K, V> connManager, K[] keys) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.exists(keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K, V> boolean expire(K key, long seconds, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.expire(key, seconds);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <V> BArray keys(String pattern, RedisConnectionManager<String, V> connManager)
            throws RedisConnectorException {
        RedisKeyCommands<String, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            List<String> result = keyCommands.keys(pattern);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K, V> boolean move(K key, int db, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.move(key, db);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K, V> boolean persist(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.persist(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K, V> boolean pExpire(K key, long milliSeconds, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.pexpire(key, milliSeconds);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K, V> long pTtl(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.pttl(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K> String randomKey(RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.randomkey();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K> String rename(K key, K newName, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.rename(key, newName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K> boolean renameNx(K key, K newName, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.renamenx(key, newName);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K> BArray sort(K key, RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            List<String> result = keyCommands.sort(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K, V> long ttl(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.ttl(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    static <K, V> String type(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.type(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    // TODO: Add as a native action once byte type is supported in ballerina. When doing so retrun a BType
    protected <K, V> byte[] dump(K key, RedisConnectionManager<K, V> connManager) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = connManager.getKeyCommandConnection();
            return keyCommands.dump(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    // ============================= Connection Commands  ================================== //

    static <K, V> String auth(String password, RedisConnectionManager<K, V> connManager)
            throws RedisConnectorException {
        RedisCommands<K, V> redisCommands = null;
        try {
            redisCommands = connManager.getBaseCommandConnection();
            return redisCommands.auth(password);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Password " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    static <K> String echo(String message, RedisConnectionManager<K, String> connManager)
            throws RedisConnectorException {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = connManager.getBaseCommandConnection();
            ;
            return redisCommands.echo(message);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    static <K> String ping(RedisConnectionManager<K, String> connManager) throws RedisConnectorException {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = connManager.getBaseCommandConnection();
            return redisCommands.ping();
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    protected static <K, V> void close(RedisConnectionManager<K, V> connManager) {
        if (connManager.isPoolingEnabled()) {
            connManager.closeConnectionPool();
        } else if (connManager.isClusterConnection()) {

            connManager.getRedisClusterCommands().quit();
        } else {
            connManager.getRedisCommands().quit();
        }
    }
}
