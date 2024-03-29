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
import io.ballerina.lib.redis.utils.ConversionUtils;
import io.ballerina.runtime.api.values.BArray;
import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.BaseRedisCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;

import static io.ballerina.lib.redis.utils.Constants.MUST_NOT_BE_NULL;
import static io.ballerina.lib.redis.utils.Constants.REDIS_SERVER_ERROR;

/**
 * Executor implementation for Redis connection commands.
 *
 * @since 2.6.0
 */
public class RedisConnectionCommandExecutor {

    private final RedisConnectionManager<?, ?> connManager;
    private static final String CLUSTER_INFO_SEPARATOR = "\\r\\n";

    public RedisConnectionCommandExecutor(RedisConnectionManager<?, ?> connManager) {
        this.connManager = connManager;
    }

    public String auth(String password) throws RedisConnectorException {
        RedisClusterCommands<?, String> clusterCommands = null;
        RedisCommands<?, String> redisCommands = null;
        try {
            if (connManager.isClusterConnection()) {
                clusterCommands = (RedisAdvancedClusterCommands<?, String>) connManager.getRedisClusterCommands();
                return clusterCommands.auth(password);
            } else {
                redisCommands = (RedisCommands<?, String>) connManager.getRedisCommands();
                return redisCommands.auth(password);
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Password " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(clusterCommands);
            connManager.releaseResources(redisCommands);
        }
    }

    public <K> String echo(String message) throws RedisConnectorException {
        BaseRedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (BaseRedisCommands<K, String>) connManager.getConnectionCommandConnection();
            return redisCommands.echo(message);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(redisCommands);
        }
    }

    public <K> String ping() throws RedisConnectorException {
        BaseRedisCommands<K, String> redisCommands = null;
        try {
            redisCommands = (BaseRedisCommands<K, String>) connManager.getConnectionCommandConnection();
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

    public BArray clusterInfo() throws RedisConnectorException {
        if (!connManager.isClusterConnection()) {
            throw new RedisConnectorException("Cannot execute cluster info command on a non-cluster connection");
        }

        RedisClusterCommands<?, String> clusterCommands = null;
        try {
            clusterCommands = (RedisAdvancedClusterCommands<?, String>) connManager.getRedisClusterCommands();
            String clusterInfo = clusterCommands.clusterInfo();
            String[] infoArray = clusterInfo.split(CLUSTER_INFO_SEPARATOR);
            return ConversionUtils.createBStringArrayFromJArray(infoArray);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(clusterCommands);
        }
    }
}
