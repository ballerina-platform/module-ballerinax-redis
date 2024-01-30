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
import io.lettuce.core.Range;
import io.lettuce.core.RedisException;
import io.lettuce.core.api.sync.RedisSortedSetCommands;
import org.ballerinalang.redis.exceptions.RedisConnectorException;

import java.util.List;
import java.util.Map;

import static org.ballerinalang.redis.utils.Constants.ARGUMENTS_MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.KEY_MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.MUST_NOT_BE_NULL;
import static org.ballerinalang.redis.utils.Constants.REDIS_SERVER_ERROR;
import static org.ballerinalang.redis.utils.ConversionUtils.createArrayFromScoredValueMap;
import static org.ballerinalang.redis.utils.ConversionUtils.createBStringArrayFromList;

/**
 * Executor implementation for Redis sorted set commands.
 *
 * @since 2.6.0
 */
public class RedisSortedSetCommandExecutor {

    private final RedisConnectionManager<?, ?> connManager;

    public RedisSortedSetCommandExecutor(RedisConnectionManager<?, ?> connManager) {
        this.connManager = connManager;
    }

    public <K, V> long zAdd(K key, Map<V, Double> valueScoreMap) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zadd(key, createArrayFromScoredValueMap(valueScoreMap));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Members " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K, V> long zCard(K key) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zcard(key);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K, V> long zCount(K key, double min, double max) throws RedisConnectorException {
        Range<Double> range = Range.create(min, max);
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zcount(key, range);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K, V> double zIncrBy(K key, double amount, V member) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zincrby(key, amount, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K, V> long zInterStore(K dest, K[] keys) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zinterstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(ARGUMENTS_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K, V> long zLexCount(K key, V min, V max) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
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

    public <K> BArray zRange(K key, long min, long max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
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

    public <K> BArray zRangeByLex(K key, String min, String max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
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

    public <K> BArray zRevRangeByLex(K key, String min, String max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
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

    public <K> BArray zRangeByScore(K key, double min, double max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
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

    public <K, V> long zRank(K key, V member) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zrank(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K, V> long zRem(K key, V[] members) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zrem(key, members);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K> long zRemRangeByLex(K key, String min, String max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
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

    public <K> long zRemRangeByRank(K key, long min, long max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zremrangebyrank(key, min, max);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K> long zRemRangeByScore(K key, double min, double max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
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

    public <K> BArray zRevRange(K key, long min, long max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
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

    public <K> BArray zRevRangeByScore(K key, double min, double max) throws RedisConnectorException {
        RedisSortedSetCommands<K, String> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, String>) connManager.getSortedSetCommandConnection();
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

    public <K, V> long zRevRank(K key, V member) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zrevrank(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K, V> double zScore(K key, V member) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zscore(key, member);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(KEY_MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }

    public <K, V> long zUnionStore(K dest, K[] keys) throws RedisConnectorException {
        RedisSortedSetCommands<K, V> sortedSetCommands = null;
        try {
            sortedSetCommands = (RedisSortedSetCommands<K, V>) connManager.getSortedSetCommandConnection();
            return sortedSetCommands.zunionstore(dest, keys);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Destination key/source key(s) " + MUST_NOT_BE_NULL, e);
        } catch (RedisException e) {
            throw new RedisConnectorException(REDIS_SERVER_ERROR + e.getMessage(), e);
        } finally {
            connManager.releaseResources(sortedSetCommands);
        }
    }
}
