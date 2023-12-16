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

package org.ballerinalang.redis;

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import org.ballerinalang.redis.connection.RedisListCommandsExecutor;

import static org.ballerinalang.redis.utils.ConversionUtils.createBError;
import static org.ballerinalang.redis.utils.ConversionUtils.createStringArrayFromBArray;
import static org.ballerinalang.redis.utils.RedisUtils.getConnection;

/**
 * Ballerina native util implementation for redis list commands.
 */
public class ListCommands {

    /**
     * Prepend one or multiple values to a list, only if the list exists.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key
     * @param values      The values to be prepended
     * @return The length of the list after the push operation(s)
     */
    public static Object lPushX(BObject redisClient, BString redisKey, BArray values) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.lPushX(redisKey.getValue(), createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove and get the first element in a list, or block until one is available.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param timeOut     The timeout in seconds
     * @param keys        The keys
     * @return nil` when no element could be popped and the timeout expired. A map containing one item, with the key
     * being  the name of the key where an element was popped and the second element  being the value of the popped
     * element, or `error` if an error occurs
     */
    public static Object bLPop(BObject redisClient, int timeOut, BArray keys) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.bLPop(timeOut, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove and get the last element in a list, or block until one is available.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param timeOut     The timeout in seconds
     * @param keys        The keys
     * @return `nil` when no element could be popped and the timeout expired. A map containing one item, with the key
     * being  the name of the key where an element was popped and the second element being the value of the popped
     * element, or `error` if an error occurs
     */
    public static Object bRPop(BObject redisClient, int timeOut, BArray keys) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.bRPop(timeOut, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Prepend one or multiple values to a list.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key
     * @param values      The values to be prepended
     * @return The length of the list after the push operation(s)
     */
    public static Object lPush(BObject redisClient, BString redisKey, BArray values) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.lPush(redisKey.getValue(), createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove and get the first element in a list.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key
     * @return The value of the first element, or nil when key does not exist
     */
    public static Object lPop(BObject redisClient, BString redisKey) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return StringUtils.fromString(executor.lPop(redisKey.getValue()));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get an element from a list by its index.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key
     * @param index       The index from which the element should be retrieved
     * @return The value at the given index
     */
    public static Object lIndex(BObject redisClient, BString redisKey, int index) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return StringUtils.fromString(executor.lIndex(redisKey.getValue(), index));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Insert an element before or after another element in a list.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param key         The key
     * @param before      boolean value representing Whether element should be inserted before or after
     *                    the pivot
     * @param pivot       The pivot
     * @param value       The value
     * @return The length of the list after the insert operation, or -1 when the value pivot not found
     */
    public static Object lInsert(BObject redisClient, BString key, boolean before, BString pivot,
                                 BString value) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.lInsert(key.getValue(), before, pivot.getValue(), value.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get the length of a list.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key
     * @return The length of the list at key
     */
    public static Object lLen(BObject redisClient, BString redisKey) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.lLen(redisKey.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Get a range of elements from a list.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key
     * @param startPos    The begining index of the range
     * @param stopPos     The last index of the range
     * @return Array of elements in the specified range
     */
    public static Object lRange(BObject redisClient, BString redisKey, int startPos, int stopPos) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.lRange(redisKey.getValue(), startPos, stopPos);
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove elements from a list.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key
     * @param count       The number of elements to be removed
     * @param value       The value which the elements to be removed should be equal to
     * @return Number of elements removed
     */
    public static Object lRem(BObject redisClient, BString redisKey, int count, BString value) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.lRem(redisKey.getValue(), count, value.getValue());
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Set the value of an element in a list by its index.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key of the list
     * @param index       The index of the element of which the value needs to be set
     * @param value       The value to be set
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object lSet(BObject redisClient, BString redisKey, int index, BString value) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return StringUtils.fromString(executor.lSet(redisKey.getValue(), index, value.toString()));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Trim a list to the specified range.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key of the list
     * @param startPos    The starting index of the range
     * @param stopPos     The end index of the range
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object lTrim(BObject redisClient, BString redisKey, int startPos, int stopPos) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return StringUtils.fromString(executor.lTrim(redisKey.getValue(), startPos, startPos));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove and get the last element in a list.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param redisKey    The key of the list
     * @return The value of the last element, or `nil` when key does not exist
     */
    public static Object rPop(BObject redisClient, BString redisKey) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return StringUtils.fromString(executor.rPop(redisKey.getValue()));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Remove the last element in a list, append it to another list and return it.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param src         The source key
     * @param destination The destination key
     * @return The element being popped and pushed
     */
    public static Object rPopLPush(BObject redisClient, BString src, BString destination) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return StringUtils.fromString(executor.rPopLPush(src.getValue(), destination.getValue()));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Append one or multiple values to a list.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param key         The key of the list
     * @param values      Array of values to be appended
     * @return The length of the list after the push operation
     */
    public static Object rPush(BObject redisClient, BString key, BArray values) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.rPush(key.getValue(), createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return createBError(e);
        }
    }

    /**
     * Append one or multiple values to a list, only if the list exists.
     *
     * @param redisClient Client from thr Ballerina redis client
     * @param key         The key of the list
     * @param values      Array of values to be appended
     * @return The length of the list after the push operation
     */
    public static Object rPushX(BObject redisClient, BString key, BArray values) {
        try {
            RedisListCommandsExecutor executor = getConnection(redisClient).getListCommandExecutor();
            return executor.rPush(key.getValue(), createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return createBError(e);
        }
    }
}
