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
import io.lettuce.core.api.sync.RedisSetCommands;
import org.ballerinalang.redis.exceptions.RedisConnectorException;

import java.util.List;
import java.util.Set;

import static org.ballerinalang.redis.utils.Constants.ARGUMENTS_MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.KEYS_MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.KEY_MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.REDIS_SERVER_ERROR;
import static org.ballerinalang.redis.utils.ConversionUtils.createBStringArrayFromList;
import static org.ballerinalang.redis.utils.ConversionUtils.createBStringArrayFromSet;

/**
 * Executor implementation for Redis set commands.
 *
 * @since 3.0.0
 */
public class RedisSetCommandExecutor {

    private final RedisConnectionManager<?, ?> connManager;

    public RedisSetCommandExecutor(RedisConnectionManager<?, ?> connManager) {
        this.connManager = connManager;
    }

    public <K, V> long sAdd(K key, V[] values) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, V>) connManager.getSetCommandConnection();
            return setCommands.sadd(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    public <K, V> long sCard(K key) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, V>) connManager.getSetCommandConnection();
            return setCommands.scard(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    public <K> BArray sDiff(K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
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

    public <K> long sDiffStore(K dest, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
            return setCommands.sdiffstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    public <K> BArray sInter(K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
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

    public <K> long sInterStore(K dest, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
            return setCommands.sinterstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    public <K, V> boolean sIsMember(K key, V value) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, V>) connManager.getSetCommandConnection();
            return setCommands.sismember(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    public <K> BArray sMembers(K key) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
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

    public <K, V> boolean sMove(K source, K dest, V member) throws RedisConnectorException {
        RedisSetCommands<K, V> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, V>) connManager.getSetCommandConnection();
            return setCommands.smove(source, dest, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    public <K> BArray sPop(K key, int count) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
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

    public <K> BArray sRandMember(K key, int count) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
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

    public <K> long sRem(K key, String[] members) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
            return setCommands.srem(key, members);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

    public <K> BArray sUnion(K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
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

    public <K> long sUnionStore(K dest, K[] keys) throws RedisConnectorException {
        RedisSetCommands<K, String> setCommands = null;
        try {
            setCommands = (RedisSetCommands<K, String>) connManager.getSetCommandConnection();
            return setCommands.sunionstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Arguments " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(setCommands);
        }
    }

}
