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
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisHashCommands;

import java.util.List;
import java.util.Map;

import static io.ballerina.lib.redis.utils.Constants.KEY_MUST_NOT_BE_NULL;
import static io.ballerina.lib.redis.utils.Constants.MUST_NOT_BE_NULL;
import static io.ballerina.lib.redis.utils.Constants.REDIS_SERVER_ERROR;
import static io.ballerina.lib.redis.utils.ConversionUtils.createBMapFromKeyValueList;
import static io.ballerina.lib.redis.utils.ConversionUtils.createBMapFromMap;
import static io.ballerina.lib.redis.utils.ConversionUtils.createBStringArrayFromList;

/**
 * Executor implementation for Redis hash commands.
 *
 * @since 2.6.0
 */
public class RedisHashCommandExecutor {

    private final RedisConnectionManager<?, ?> connManager;

    public RedisHashCommandExecutor(RedisConnectionManager<?, ?> connManager) {
        this.connManager = connManager;
    }

    public <K, V> long hDel(BString key, K[] fields) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, V>) connManager.getHashCommandConnection();
            return hashCommands.hdel((K) key.toString(), fields);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K, V> boolean hExists(K key, K field) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, V>) connManager.getHashCommandConnection();
            return hashCommands.hexists(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K> String hGet(K key, K field) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, String>) connManager.getHashCommandConnection();
            return hashCommands.hget(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K> BMap<BString, Object> hGetAll(K key) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, String>) connManager.getHashCommandConnection();
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

    public <K> long hIncrBy(K key, K field, long amount) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, String>) connManager.getHashCommandConnection();
            return hashCommands.hincrby(key, field, amount);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K> double hIncrByFloat(K key, K field, double amount) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, String>) connManager.getHashCommandConnection();
            return hashCommands.hincrbyfloat(key, field, amount);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public BArray hKeys(String key) throws RedisConnectorException {
        RedisHashCommands<String, String> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<String, String>) connManager.getHashCommandConnection();
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

    public <K> long hLen(K key) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, String>) connManager.getHashCommandConnection();
            return hashCommands.hlen(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K> BMap<BString, Object> hMGet(K key, K[] fields) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, String>) connManager.getHashCommandConnection();
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

    public <K, V> String hMSet(K key, Map<K, V> fieldValueMap) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, V>) connManager.getHashCommandConnection();
            return hashCommands.hmset(key, fieldValueMap);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K, V> boolean hSet(K key, K field, V value) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, V>) connManager.getHashCommandConnection();
            return hashCommands.hset(key, field, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K, V> boolean hSetNx(K key, K field, V value) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, V>) connManager.getHashCommandConnection();
            return hashCommands.hsetnx(key, field, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K, V> long hStrln(K key, K field) throws RedisConnectorException {
        RedisHashCommands<K, V> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, V>) connManager.getHashCommandConnection();
            return hashCommands.hstrlen(key, field);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Key/field " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(hashCommands);
        }
    }

    public <K> BArray hVals(K key) throws RedisConnectorException {
        RedisHashCommands<K, String> hashCommands = null;
        try {
            hashCommands = (RedisHashCommands<K, String>) connManager.getHashCommandConnection();
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

}
