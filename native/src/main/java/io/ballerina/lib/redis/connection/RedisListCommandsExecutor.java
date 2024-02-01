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
import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.KeyValue;
import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisListCommands;

import java.util.List;

import static io.ballerina.lib.redis.utils.Constants.ARGUMENTS_MUST_NOT_BE_NULL;
import static io.ballerina.lib.redis.utils.Constants.KEYS_MUST_NOT_BE_NULL;
import static io.ballerina.lib.redis.utils.Constants.KEY_MUST_NOT_BE_NULL;
import static io.ballerina.lib.redis.utils.Constants.REDIS_SERVER_ERROR;
import static io.ballerina.lib.redis.utils.ConversionUtils.createBStringArrayFromList;

/**
 * Executor implementation for Redis key commands.
 *
 * @since 2.6.0
 */
public class RedisListCommandsExecutor {

    private final RedisConnectionManager<?, ?> connManager;

    public RedisListCommandsExecutor(RedisConnectionManager<?, ?> connManager) {
        this.connManager = connManager;
    }

    public <K, V> long lPush(K key, V[] value) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.lpush(key, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K> String lPop(K key) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) connManager.getListCommandConnection();
            return listCommands.lpop(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K, V> long lPushX(K key, V[] values) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.lpushx(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K> BMap<BString, Object> bLPop(long timeout, K[] keys) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) connManager.getListCommandConnection();
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

    public <K> BMap<BString, Object> bRPop(long timeout, K[] keys) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) connManager.getListCommandConnection();
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

    public <K> String lIndex(K key, long index) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) connManager.getListCommandConnection();
            return listCommands.lindex(key, index);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K, V> long lInsert(K key, boolean before, V pivot, V value) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.linsert(key, before, pivot, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K, V> long lLen(K key) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.llen(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K> BArray lRange(K key, long start, long stop) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) connManager.getListCommandConnection();
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

    public <K, V> long lRem(K key, long count, V value) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.lrem(key, count, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K, V> String lSet(K key, long index, V value) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.lset(key, index, value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K, V> String lTrim(K key, long start, long stop) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.ltrim(key, start, stop);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K> String rPop(K key) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) connManager.getListCommandConnection();
            return listCommands.rpop(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K> String rPopLPush(K src, K destination) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) connManager.getListCommandConnection();
            return listCommands.rpoplpush(src, destination);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K, V> long rPush(K key, V[] values) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.rpush(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K> String brPopLPush(K source, K dest, long timeout) throws RedisConnectorException {
        RedisListCommands<K, String> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, String>) connManager.getListCommandConnection();
            return listCommands.brpoplpush(timeout, source, dest);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEYS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }

    public <K, V> long rPushX(K key, V[] values) throws RedisConnectorException {
        RedisListCommands<K, V> listCommands = null;
        try {
            listCommands = (RedisListCommands<K, V>) connManager.getListCommandConnection();
            return listCommands.rpushx(key, values);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(listCommands);
        }
    }
}
