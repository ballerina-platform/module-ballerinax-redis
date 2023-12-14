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

import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.redis.RedisDataSource;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;
import static org.ballerinalang.redis.utils.ConversionUtils.createStringArrayFromBArray;

/**
 * Redis sorted set actions.
 */
public class SortedSetActions extends AbstractRedisAction {

    /**
     * Add one or more members to a sorted set, or update its score if it already exist.
     *
     * @param redisClient    Client from the Ballerina redis client
     * @param key            The key of the sorted set
     * @param memberScoreMap A map of members and corresponding scores
     * @return The number of elements that were added to the sorted set, not including all the elements which were
     * already present in the set for which the score was updated
     */
    public static Object zAdd(BObject redisClient, BString key,
                              BMap<BString, Object> memberScoreMap) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            Map<String, Double> map = new LinkedHashMap<>();
            for (Map.Entry<BString, Object> entry : memberScoreMap.entrySet()) {
                map.put(entry.getKey().toString(), (Double) entry.getValue());
            }
            return zAdd(key.getValue(), redisDataSource, map);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the number of members in a sorted set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @return The cardinality (number of elements) of the sorted set
     */
    public static Object zCard(BObject redisClient, BString key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zCard(key.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Count the members in a sorted set with scores within the given range.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum score of the range
     * @param max         The maximum score of the range
     * @return The number of elements in the specified score range
     */
    public static Object zCount(BObject redisClient, BString key, float min, float max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zCount(key.getValue(), min, max, redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Increment the score of a member in a sorted set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param amount      The amount to increment
     * @param member      The member whose score to be incremented
     * @return The new score of the member
     */
    public static Object zIncrBy(BObject redisClient, BString key, float amount, BString member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zIncrBy(key.getValue(), amount, member.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Intersect multiple sorted sets and store the resulting sorted set in a new key.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination The destination key of the resulting sorted set
     * @param keys        The keys of the sorted sets to be intersected
     * @return The number of elements in the resulting sorted set
     */
    public static Object zInterStore(BObject redisClient, BString destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zInterStore(destination.getValue(), redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Count the members in a sorted set within the given lexicographical range.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination The key of the sorted set
     * @param min         The minimum lexicographical value of the range
     * @param max         The maximum lexicographical value of the range
     * @return The number of elements in the specified lexicographical value range
     */
    public static Object zLexCount(BObject redisClient, BString destination, BString min, BString max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zLexCount(destination.getValue(), min.getValue(), max.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return a range of members in a sorted set, by index.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum index of the range
     * @param max         The maximum index of the range
     * @return The range of members in a sorted set, by index
     */
    public static Object zRange(BObject redisClient, BString key, int min, int max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRange(key.getValue(), min, max, redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range from lowest to highest.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum lexicographical value of the range
     * @param max         The maximum lexicographical value of the range
     * @return Array of members in the specified lexicographical value range ordered from lowest to highest
     */
    public static Object zRangeByLex(BObject redisClient, BString key, BString min, BString max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRangeByLex(key.getValue(), min.getValue(), max.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return a range of members in a sorted set, by lexicographical range ordered from highest to lowest.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The lexicographical value of the range
     * @param max         The maximum lexicographical value of the range
     * @return Array of members in the specified lexicographical value range ordered from highest to lowest
     */
    public static Object zRevRangeByLex(BObject redisClient, BString key, BString min, BString max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRevRangeByLex(key.getValue(), min.getValue(), max.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return a range of members in a sorted set, by score from lowest to highest.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum score of the range
     * @param max         The maximum score of the range
     * @return Array of members in the specified score range ordered from lowest to highest
     */
    public static Object zRangeByScore(BObject redisClient, BString key, float min, float max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRangeByScore(key.getValue(), min, max, redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Determine the index of a member in a sorted set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param member      The member of which the index needs to be obtained
     * @return The index of the member
     */
    public static Object zRank(BObject redisClient, BString key, BString member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRank(key.getValue(), member.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove one or more members from a sorted set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param memebers    The members to be removed
     * @return The number of members removed from the sorted set, not including non existing members
     */
    public static Object zRem(BObject redisClient, BString key, BArray memebers) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRem(key.getValue(), redisDataSource, createStringArrayFromBArray(memebers));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove all members in a sorted set between the given lexicographical range.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum lexicographical value of the range
     * @param max         The maximum lexicographical value of the range
     * @return The number of members removed from the sorted set
     */
    public static Object zRemRangeByLex(BObject redisClient, BString key, BString min, BString max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRemRangeByLex(key.getValue(), min.getValue(), max.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove all members in a sorted set within the given indices.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum index of the range
     * @param max         The maximum index of the range
     * @return The number of members removed from the sorted set
     */
    public static Object zRemRangeByRank(BObject redisClient, BString key, int min, int max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRemRangeByRank(key.getValue(), min, max, redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove all members in a sorted set within the given scores.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum score of the range
     * @param max         The maximum score of the range
     * @return The number of members removed from the sorted set
     */
    public static Object zRemRangeByScore(BObject redisClient, BString key, float min, float max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRemRangeByScore(key.getValue(), min, max, redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return a range of members in a sorted set, by index, ordered highest to lowest.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum index of the range
     * @param max         The maximum index of the range
     * @return The number of elements in the specified index range
     */
    public static Object zRevRange(BObject redisClient, BString key, int min, int max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRevRange(key.getValue(), min, max, redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return a range of members in a sorted set, by score from highest to lowest.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param min         The minimum score of the range
     * @param max         The maximum score of the range
     * @return Array of members in the specified score range ordered from highest to lowest
     */
    public static Object zRevRangeByScore(BObject redisClient, BString key, float min, float max) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRevRangeByScore(key.getValue(), min, max, redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Determine the index of a member in a sorted set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param member      The member of which the index needs to be obtained
     * @return The index of the member
     */
    public static Object zRevRank(BObject redisClient, BString key, BString member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zRevRank(key.getValue(), member.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Determine the score of a member in a sorted set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the sorted set
     * @param member      The member of which the score needs to be obtained
     * @return
     */
    public static Object zScore(BObject redisClient, BString key, BString member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zScore(key.getValue(), member.getValue(), redisDataSource);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Determine the score of a member in a sorted set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination The key of the sorted set
     * @param keys        The member of which the score needs to be obtained
     * @return The score of the member
     */
    public static Object zUnionStore(BObject redisClient, BString destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisClient.getNativeData(DATA_SOURCE);
            return zUnionStore(destination.getValue(), redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
