/*
 * Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org)
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

package io.ballerina.lib.redis.connection;

import io.ballerina.lib.redis.exceptions.RedisConnectorException;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisStringCommands;

import java.util.List;
import java.util.Map;

import static io.ballerina.lib.redis.utils.Constants.KEYS_MUST_NOT_BE_NULL;
import static io.ballerina.lib.redis.utils.Constants.KEY_MUST_NOT_BE_NULL;
import static io.ballerina.lib.redis.utils.Constants.REDIS_SERVER_ERROR;
import static io.ballerina.lib.redis.utils.ConversionUtils.createBMapFromKeyValueList;

/**
 * Executor implementation for Redis string commands.
 *
 * @since 2.6.0
 */
public class RedisStringCommandExecutor {

    private final RedisConnectionManager<?, ?> connManager;

    public RedisStringCommandExecutor(RedisConnectionManager<?, ?> redisConnectionManager) {
        this.connManager = redisConnectionManager;
    }

    public <K, V> String set(K key, V value) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.set(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K> String get(K key) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, String>) connManager.getStringCommandConnection();
            return stringCommands.get(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public long append(String key, String value) throws RedisConnectorException {
        RedisStringCommands<String, String> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<String, String>) connManager.getStringCommandConnection();
            return stringCommands.append(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public long bitCount(String key) throws RedisConnectorException {
        RedisStringCommands<String, String> redisCommands = null;
        try {
            redisCommands = (RedisStringCommands<String, String>) connManager.getStringCommandConnection();
            return redisCommands.bitcount(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    public <K, V> long bitopAnd(K destination, K[] keys) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.bitopAnd(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long bitopOr(K destination, K[] keys) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.bitopOr(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long bitopNot(K destination, K key) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.bitopNot(destination, key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long bitopXor(K destination, K[] keys) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.bitopXor(destination, keys);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long decr(K key) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.decr(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long decrBy(K key, int value) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.decrby(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long getBit(K key, int offset) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.getbit(key, offset);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K> String getRange(K key, int start, int end) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, String>) connManager.getStringCommandConnection();
            return stringCommands.getrange(key, start, end);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K> String getSet(K key, String value) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, String>) connManager.getStringCommandConnection();
            return stringCommands.getset(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long incr(K key) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.incr(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long incrBy(K key, int value) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.incrby(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> double incrByFloat(K key, double value) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.incrbyfloat(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K> BMap<BString, Object> mGet(K[] key) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, String>) connManager.getStringCommandConnection();
            List<KeyValue<K, String>> result = stringCommands.mget(key);
            return createBMapFromKeyValueList(result);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> String mSet(Map<K, V> map) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.mset(map);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> boolean mSetnx(Map<K, V> map) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.msetnx(map);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K> String pSetex(K key, String value, long expirationPeriodMs) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, String>) connManager.getStringCommandConnection();
            return stringCommands.psetex(key, expirationPeriodMs, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long setBit(K key, int value, long offset) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.setbit(key, offset, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K> String setEx(K key, String value, long expirationPeriodSeconds) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, String>) connManager.getStringCommandConnection();
            return stringCommands.setex(key, expirationPeriodSeconds, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K> boolean setNx(K key, String value) throws RedisConnectorException {
        RedisStringCommands<K, String> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, String>) connManager.getStringCommandConnection();
            return stringCommands.setnx(key, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long setRange(K key, long offset, V value) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.setrange(key, offset, value);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }

    public <K, V> long strLen(K key) throws RedisConnectorException {
        RedisStringCommands<K, V> stringCommands = null;
        try {
            stringCommands = (RedisStringCommands<K, V>) connManager.getStringCommandConnection();
            return stringCommands.strlen(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(stringCommands);
        }
    }
}
