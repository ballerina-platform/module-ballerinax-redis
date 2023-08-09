/*
 * Copyright (c) 2020, WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
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

package org.ballerinalang.redis.actions;

import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.runtime.internal.values.HandleValue;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.utils.ModuleUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.ballerinalang.redis.Constants.REDIS_EXCEPTION_OCCURRED;

/**
 * Redis sorted set actions.
 */
public class SortedSetActions extends AbstractRedisAction {

    /**
     * Add one or more members to a sorted set, or update its score if it already exist.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param memberScoreMap A map of members and corresponding scores
     * @return The number of elements that were added to the sorted set, not including all the elements which were
     *         already present in the set for which the score was updated
     */
    public static Object zAdd(HandleValue redisDataSourceHandleValue, String key,
                              BMap<BString, Object> memberScoreMap) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            Map<String, Double> map = new LinkedHashMap<>();
            for (Map.Entry<BString, Object> entry : memberScoreMap.entrySet()) {
                map.put(entry.getKey().toString(), (Double) entry.getValue());
            }
            return zAdd(key, redisDataSource, map);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the number of members in a sorted set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @return The cardinality (number of elements) of the sorted set
     */
    public static Object zCard(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zCard(key, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Count the members in a sorted set with scores within the given range.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum score of the range
     * @param max The maximum score of the range
     * @return The number of elements in the specified score range
     */
    public static Object zCount(HandleValue redisDataSourceHandleValue, String key, float min, float max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zCount(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Increment the score of a member in a sorted set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param amount The amount to increment
     * @param member The member whose score to be incremented
     * @return The new score of the member
     */
    public static Object zIncrBy(HandleValue redisDataSourceHandleValue, String key, float amount, String member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zIncrBy(key, amount, member, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Intersect multiple sorted sets and store the resulting sorted set in a new key.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination The destination key of the resulting sorted set
     * @param keys The keys of the sorted sets to be intersected
     * @return The number of elements in the resulting sorted set
     */
    public static Object zInterStore(HandleValue redisDataSourceHandleValue, String destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zInterStore(destination, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Count the members in a sorted set within the given lexicographical range.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination The key of the sorted set
     * @param min The minimum lexicographical value of the range
     * @param max The maximum lexicographical value of the range
     * @return The number of elements in the specified lexicographical value range
     */
    public static Object zLexCount(HandleValue redisDataSourceHandleValue, String destination, String min, String max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zLexCount(destination, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Return a range of members in a sorted set, by index.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum index of the range
     * @param max The maximum index of the range
     * @return The range of members in a sorted set, by index
     */
    public static Object zRange(HandleValue redisDataSourceHandleValue, String key, int min, int max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRange(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range from lowest to highest.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum lexicographical value of the range
     * @param max The maximum lexicographical value of the range
     * @return Array of members in the specified lexicographical value range ordered from lowest to highest
     */
    public static Object zRangeByLex(HandleValue redisDataSourceHandleValue, String key, String min, String max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRangeByLex(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range ordered from highest to
     * lowest.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The lexicographical value of the range
     * @param max The maximum lexicographical value of the range
     * @return Array of members in the specified lexicographical value range ordered from highest to lowest
     */
    public static Object zRevRangeByLex(HandleValue redisDataSourceHandleValue, String key, String min, String max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRevRangeByLex(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Return a range of members in a sorted set, by score from lowest to highest.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum score of the range
     * @param max The maximum score of the range
     * @return Array of members in the specified score range ordered from lowest to highest
     */
    public static Object zRangeByScore(HandleValue redisDataSourceHandleValue, String key, float min, float max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRangeByScore(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Determine the index of a member in a sorted set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param member The member of which the index needs to be obtained
     * @return  The index of the member
     */
    public static Object zRank(HandleValue redisDataSourceHandleValue, String key, String member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRank(key, member, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove one or more members from a sorted set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param memebers The members to be removed
     * @return The number of members removed from the sorted set, not including non existing members
     */
    public static Object zRem(HandleValue redisDataSourceHandleValue, String key, BArray memebers) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRem(key, redisDataSource, createStringArrayFromBArray(memebers));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove all members in a sorted set between the given lexicographical range.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum lexicographical value of the range
     * @param max The maximum lexicographical value of the range
     * @return The number of members removed from the sorted set
     */
    public static Object zRemRangeByLex(HandleValue redisDataSourceHandleValue, String key, String min, String max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRemRangeByLex(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove all members in a sorted set within the given indices.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum index of the range
     * @param max The maximum index of the range
     * @return The number of members removed from the sorted set
     */
    public static Object zRemRangeByRank(HandleValue redisDataSourceHandleValue, String key, int min, int max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRemRangeByRank(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove all members in a sorted set within the given scores.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum score of the range
     * @param max The maximum score of the range
     * @return The number of members removed from the sorted set
     */
    public static Object zRemRangeByScore(HandleValue redisDataSourceHandleValue, String key, float min, float max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRemRangeByScore(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Return a range of members in a sorted set, by index, ordered highest to lowest.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum index of the range
     * @param max The maximum index of the range
     * @return The number of elements in the specified index range
     */
    public static Object zRevRange(HandleValue redisDataSourceHandleValue, String key, int min, int max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRevRange(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Return a range of members in a sorted set, by score from highest to lowest.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param min The minimum score of the range
     * @param max The maximum score of the range
     * @return Array of members in the specified score range ordered from highest to lowest
     */
    public static Object zRevRangeByScore(HandleValue redisDataSourceHandleValue, String key, float min, float max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRevRangeByScore(key, min, max, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Determine the index of a member in a sorted set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param member The member of which the index needs to be obtained
     * @return The index of the member
     */
    public static Object zRevRank(HandleValue redisDataSourceHandleValue, String key, String member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zRevRank(key, member, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Determine the score of a member in a sorted set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the sorted set
     * @param member The member of which the score needs to be obtained
     * @return
     */
    public static Object zScore(HandleValue redisDataSourceHandleValue, String key, String member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zScore(key, member, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Determine the score of a member in a sorted set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination The key of the sorted set
     * @param keys The member of which the score needs to be obtained
     * @return The score of the member
     */
    public static Object zUnionStore(HandleValue redisDataSourceHandleValue, String destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return zUnionStore(destination, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }
}
