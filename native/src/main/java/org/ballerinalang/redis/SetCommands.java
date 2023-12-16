/*
 * Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
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

package org.ballerinalang.redis;

import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.redis.connection.RedisSetCommandExecutor;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;
import static org.ballerinalang.redis.utils.ConversionUtils.createStringArrayFromBArray;
import static org.ballerinalang.redis.utils.RedisUtils.getConnection;

/**
 * Ballerina native util implementation for redis set commands.
 */
public class SetCommands {

    /**
     * Add one or more members to a set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the set
     * @param values      Array of values to be added
     * @return The number of elements that were added to the set, not including all the elements which were already
     * present in the set
     */
    public static Object sAdd(BObject redisClient, BString key, BArray values) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sAdd(key.getValue(), createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the number of members in a set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the set
     * @return The cardinality (number of elements) of the set
     */
    public static Object sCard(BObject redisClient, BString key) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sCard(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return set resulting from the difference between the first set and all the successive sets.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param keys        The keys of the sets
     * @return An array of members of the resulting set
     */
    public static Object sDiff(BObject redisClient, BArray keys) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sDiff(createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Obtain the set resulting from the difference between the first set and all the successive. sets and store at the
     * provided destination.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination The destination key of the resulting set
     * @param keys        The keys of the sets to find the difference of
     * @return The number of members in the resulting set
     */
    public static Object sDiffStore(BObject redisClient, BString destination, BArray keys) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sDiffStore(destination.getValue(), createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return the intersection of the provided sets.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param keys        The keys of the sets to be intersected
     * @return An array of members of the resulting set
     */
    public static Object sInter(BObject redisClient, BArray keys) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sInter(createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Obtain the intersection of the provided sets and store at the provided destination.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination The destination key of the resulting set
     * @param keys        The keys of the sets to be intersected
     * @return An array of members of the resulting set
     */
    public static Object sInterStore(BObject redisClient, BString destination, BArray keys) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sInterStore(destination.getValue(), createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Determine if a given value is a member of a set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the set
     * @param value       The value
     * @return boolean true/false depending on whether the value is a member of the set or not
     */
    public static Object sIsMember(BObject redisClient, BString key, BString value) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sIsMember(key.getValue(), value.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get all the members in a set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the set
     * @return Array of all members in the set
     */
    public static Object sMembers(BObject redisClient, BString key) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sMembers(key.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Move a member from one set to another.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param src         The source key
     * @param destination The destination key
     * @param member      The member to be moved
     * @return `true` if the element is moved. `false` if the element is not a member of source and no operation was
     * performed
     */
    public static Object sMove(BObject redisClient, BString src, BString destination, BString member) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sMove(src.getValue(), destination.getValue(), member.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove and return a random member from a set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The source key
     * @param count       Number of members to pop
     * @return Array of removed elements or `nil` if key does not exist
     */
    public static Object sPop(BObject redisClient, BString key, int count) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sPop(key.getValue(), count);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get one or multiple random members from a set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the set
     * @param count       Number of members to obtain
     * @return Array of the randomly selected elements, or `nil` when key does not
     */
    public static Object sRandMember(BObject redisClient, BString key, int count) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sRandMember(key.getValue(), count);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove one or more members from a set.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param key         The key of the set
     * @param members     Array of members to remove
     * @return The number of members that were removed from the set, not including non existing members
     */
    public static Object sRem(BObject redisClient, BString key, BArray members) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sRem(key.getValue(), createStringArrayFromBArray(members));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return the union of multiple sets.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param keys        Array of keys of sets
     * @return Array of members of the resulting set
     */
    public static Object sUnion(BObject redisClient, BArray keys) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sUnion(createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Return the union of multiple sets.
     *
     * @param redisClient Client from the Ballerina redis client
     * @param destination The destination key of the resulting set
     * @param keys        Array of keys of sets
     * @return Number of members of the resulting set
     */
    public static Object sUnionStore(BObject redisClient, BString destination, BArray keys) {
        try {
            RedisSetCommandExecutor executor = getConnection(redisClient).getSetCommandExecutor();
            return executor.sUnionStore(destination.getValue(), createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
