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

package org.ballerinalang.redis.connection;

import io.ballerina.runtime.api.values.BArray;
import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisKeyCommands;
import org.ballerinalang.redis.exceptions.RedisConnectorException;

import java.util.List;

import static org.ballerinalang.redis.utils.Constants.KEYS_MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.KEY_MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.REDIS_SERVER_ERROR;
import static org.ballerinalang.redis.utils.ConversionUtils.createBStringArrayFromList;

/**
 * Executor implementation for Redis key commands.
 *
 * @since 2.6.0
 */
public class RedisKeyCommandExecutor {

    private final RedisConnectionManager<?, ?> connManager;

    public RedisKeyCommandExecutor(RedisConnectionManager<?, ?> connManager) {
        this.connManager = connManager;
    }

    public <K, V> long del(K[] keys) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.del(keys);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K, V> long exists(K[] keys) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.exists(keys);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K, V> boolean expire(K key, long seconds) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.expire(key, seconds);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <V> BArray keys(String pattern) throws RedisConnectorException {
        RedisKeyCommands<String, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<String, V>) connManager.getKeyCommandConnection();
            List<String> result = keyCommands.keys(pattern);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K, V> boolean move(K key, int db) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.move(key, db);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K, V> boolean persist(K key) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.persist(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K, V> boolean pExpire(K key, long milliSeconds) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.pexpire(key, milliSeconds);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K, V> long pTtl(K key) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.pttl(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K> K randomKey() throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, String>) connManager.getKeyCommandConnection();
            return keyCommands.randomkey();
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K> String rename(K key, K newName) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, String>) connManager.getKeyCommandConnection();
            return keyCommands.rename(key, newName);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K> boolean renameNx(K key, K newName) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, String>) connManager.getKeyCommandConnection();
            return keyCommands.renamenx(key, newName);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K> BArray sort(K key) throws RedisConnectorException {
        RedisKeyCommands<K, String> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, String>) connManager.getKeyCommandConnection();
            List<String> result = keyCommands.sort(key);
            return createBStringArrayFromList(result);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K, V> long ttl(K key) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.ttl(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    public <K, V> String type(K key) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.type(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }

    // TODO: Add as a native action once byte type is supported in ballerina. When doing so return a BType
    public <K, V> byte[] dump(K key) throws RedisConnectorException {
        RedisKeyCommands<K, V> keyCommands = null;
        try {
            keyCommands = (RedisKeyCommands<K, V>) connManager.getKeyCommandConnection();
            return keyCommands.dump(key);
        } catch (IllegalArgumentException e) {
            throw new RedisConnectorException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(keyCommands);
        }
    }
}
