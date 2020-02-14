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

import org.ballerinalang.jvm.BallerinaErrors;
import org.ballerinalang.jvm.values.HandleValue;
import org.ballerinalang.jvm.values.api.BArray;
import org.ballerinalang.redis.RedisDataSource;

import static org.ballerinalang.redis.BallerinaRedisDbErrors.REDIS_EXCEPTION_OCCURRED;

/**
 * Redis set actions.
 */
public class SetActions extends AbstractRedisAction {

    /**
     * Add one or more members to a set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the set
     * @param values Array of values to be added
     * @return The number of elements that were added to the set, not including all the elements which were
     *         already present in the set
     */
    public static Object sAdd(HandleValue redisDataSourceHandleValue, String key, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sAdd(key, redisDataSource, createStringArrayFromBArray(values)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Get the number of members in a set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the set
     * @return The cardinality (number of elements) of the set
     */
    public static Object sCard(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sCard(key, redisDataSource).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Return set resulting from the difference between the first set and all the successive sets.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys The keys of the sets
     * @return An array of members of the resulting set
     */
    public static Object sDiff(HandleValue redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sDiff(redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Obtain the set resulting from the difference between the first set and all the successive.
     * sets and store at the provided destination.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination The destination key of the resulting set
     * @param keys The keys of the sets to find the difference of
     * @return The number of members in the resulting set
     */
    public static Object sDiffStore(HandleValue redisDataSourceHandleValue, String destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sDiffStore(destination, redisDataSource, createStringArrayFromBArray(keys)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Return the intersection of the provided sets.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys The keys of the sets to be intersected
     * @return An array of members of the resulting set
     */
    public static Object sInter(HandleValue redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sInter(redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Obtain the intersection of the provided sets and store at the provided destination.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination The destination key of the resulting set
     * @param keys The keys of the sets to be intersected
     * @return An array of members of the resulting set
     */
    public static Object sInterStore(HandleValue redisDataSourceHandleValue, String destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sInterStore(destination, redisDataSource, createStringArrayFromBArray(keys)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Determine if a given value is a member of a set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the set
     * @param value The value
     * @return boolean true/false depending on whether the value is a member of the set or not
     */
    public static Object sIsMember(HandleValue redisDataSourceHandleValue, String key, String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sIsMember(key, value, redisDataSource).booleanValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Get all the members in a set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the set
     * @return Array of all members in the set
     */
    public static Object sMembers(HandleValue redisDataSourceHandleValue, String key) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sMembers(key, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Move a member from one set to another.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param src The source key
     * @param destination The destination key
     * @param member The member to be moved
     * @return `true` if the element is moved. `false` if the element is not a member of source and no
     *          operation was performed
     */
    public static Object sMove(HandleValue redisDataSourceHandleValue, String src, String destination, String member) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sMove(src, destination, member, redisDataSource).booleanValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Remove and return a random member from a set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The source key
     * @param count Number of members to pop
     * @return Array of removed elements or `nil` if key does not exist
     */
    public static Object sPop(HandleValue redisDataSourceHandleValue, String key, int count) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sPop(key, count, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Get one or multiple random members from a set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the set
     * @param count Number of members to obtain
     * @return Array of the randomly selected elements, or `nil` when key does not
     */
    public static Object sRandMember(HandleValue redisDataSourceHandleValue, String key, int count) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sRandMember(key, count, redisDataSource);
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Remove one or more members from a set.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the set
     * @param members Array of members to remove
     * @return The number of members that were removed from the set, not including non existing members
     */
    public static Object sRem(HandleValue redisDataSourceHandleValue, String key, BArray members) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sRem(key, redisDataSource, createStringArrayFromBArray(members)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Return the union of multiple sets.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param keys Array of keys of sets
     * @return Array of members of the resulting set
     */
    public static Object sUnion(HandleValue redisDataSourceHandleValue, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sUnion(redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }

    /**
     * Return the union of multiple sets.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param destination The destination key of the resulting set
     * @param keys Array of keys of sets
     * @return Number of members of the resulting set
     */
    public static Object sUnionStore(HandleValue redisDataSourceHandleValue, String destination, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return sUnionStore(destination, redisDataSource, createStringArrayFromBArray(keys)).intValue();
        } catch (Throwable e) {
            return BallerinaErrors.createError(REDIS_EXCEPTION_OCCURRED, e.getMessage());
        }
    }
}
