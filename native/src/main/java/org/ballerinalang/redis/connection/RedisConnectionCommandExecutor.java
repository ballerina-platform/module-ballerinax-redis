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

import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisCommands;
import org.ballerinalang.redis.exceptions.RedisConnectorException;

import static org.ballerinalang.redis.utils.Constants.MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.REDIS_SERVER_ERROR;

/**
 * Executor implementation for Redis connection commands.
 *
 * @since 3.0.0
 */
public class RedisConnectionCommandExecutor {

    private final RedisConnectionManager<?, ?> connManager;

    public RedisConnectionCommandExecutor(RedisConnectionManager<?, ?> connManager) {
        this.connManager = connManager;
    }

    public String auth(String password) throws RedisConnectorException {
        RedisCommands<?, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<?, String>) connManager.getConnectionCommandConnection();
            return redisCommands.auth(password);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Password " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    public String echo(String message) throws RedisConnectorException {
        RedisCommands<?, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<?, String>) connManager.getConnectionCommandConnection();
            return redisCommands.echo(message);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    public <K> String ping() throws RedisConnectorException {
        RedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (RedisCommands<K, String>) connManager.getConnectionCommandConnection();
            return redisCommands.ping();
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    public void close() throws RedisConnectorException {
        if (connManager.isPoolingEnabled()) {
            connManager.closeConnectionPool();
        } else if (connManager.isClusterConnection()) {
            connManager.getRedisClusterCommands().quit();
        } else {
            connManager.getRedisCommands().quit();
        }
    }
}
