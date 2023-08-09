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
import io.ballerina.runtime.api.values.BHandle;
import io.ballerina.runtime.api.values.BArray;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.utils.ModuleUtils;

import static org.ballerinalang.redis.Constants.REDIS_EXCEPTION_OCCURRED;

/**
 * Redis list actions.
 */
public class ListActions extends AbstractRedisAction {

    /**
     * Prepend one or multiple values to a list, only if the list exists.
     * 
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key
     * @param values The values to be prepended
     * @return The length of the list after the push operation(s)
     */
    public static Object lPushX(BHandle redisDataSourceHandleValue, String redisKey, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lPushX(redisKey, redisDataSource, createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove and get the first element in a list, or block until one is available.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param timeOut The timeout in seconds
     * @param keys The keys
     * @return nil` when no element could be popped and the timeout expired. A map containing one item, with the
     *         key being  the name of the key where an element was popped and the second element  being the value of the
     *         popped element, or `error` if an error occurs
     */
    public static Object bLPop(BHandle redisDataSourceHandleValue, int timeOut, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bLPop(timeOut, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove and get the last element in a list, or block until one is available.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param timeOut The timeout in seconds
     * @param keys The keys
     * @return `nil` when no element could be popped and the timeout expired. A map containing one item, with the
     *          key being  the name of the key where an element was popped and the second element being the value of the
     *          popped element, or `error` if an error occurs
     */
    public static Object bRPop(BHandle redisDataSourceHandleValue, int timeOut, BArray keys) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return bRPop(timeOut, redisDataSource, createStringArrayFromBArray(keys));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Prepend one or multiple values to a list.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key
     * @param values The values to be prepended
     * @return The length of the list after the push operation(s)
     */
    public static Object lPush(BHandle redisDataSourceHandleValue, String redisKey, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lPush(redisKey, redisDataSource, createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove and get the first element in a list.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key
     * @return The value of the first element, or nil when key does not exist
     */
    public static Object lPop(BHandle redisDataSourceHandleValue, String redisKey) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lPop(redisKey, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get an element from a list by its index.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key
     * @param index The index from which the element should be retrieved
     * @return The value at the given index
     */
    public static Object lIndex(BHandle redisDataSourceHandleValue, String redisKey, int index) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lIndex(redisKey, index, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Insert an element before or after another element in a list.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key
     * @param before boolean value representing Whether element should be inserted before or after the pivot
     * @param pivot The pivot
     * @param value The value
     * @return The length of the list after the insert operation, or -1 when the value pivot not found
     */
    public static Object lInsert(BHandle redisDataSourceHandleValue, String key, boolean before, String pivot,
                                      String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lInsert(key, before, pivot, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get the length of a list.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key
     * @return The length of the list at key
     */
    public static Object lLen(BHandle redisDataSourceHandleValue, String redisKey) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lLen(redisKey, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Get a range of elements from a list.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key
     * @param startPos The begining index of the range
     * @param stopPos The last index of the range
     * @return Array of elements in the specified range
     */
    public static Object lRange(BHandle redisDataSourceHandleValue, String redisKey, int startPos, int stopPos) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lRange(redisKey, startPos, stopPos, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove elements from a list.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key
     * @param count The number of elements to be removed
     * @param value The value which the elements to be removed should be equal to
     * @return Number of elements removed
     */
    public static Object lRem(BHandle redisDataSourceHandleValue, String redisKey, int count, String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lRem(redisKey, count, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Set the value of an element in a list by its index.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key of the list
     * @param index The index of the element of which the value needs to be set
     * @param value The value to be set
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object lSet(BHandle redisDataSourceHandleValue, String redisKey, int index, String value) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lSet(redisKey, index, value, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Trim a list to the specified range.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key of the list
     * @param startPos The starting index of the range
     * @param stopPos The end index of the range
     * @return A string with the value `OK` if the operation was successful
     */
    public static Object lTrim(BHandle redisDataSourceHandleValue, String redisKey, int startPos, int stopPos) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return lTrim(redisKey, startPos, startPos, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove and get the last element in a list.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param redisKey The key of the list
     * @return The value of the last element, or `nil` when key does not exist
     */
    public static Object rPop(BHandle redisDataSourceHandleValue, String redisKey) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rPop(redisKey, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Remove the last element in a list, append it to another list and return it.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param src The source key
     * @param destination The destination key
     * @return The element being popped and pushed
     */
    public static Object rPopLPush(BHandle redisDataSourceHandleValue, String src, String destination) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rPopLPush(src, destination, redisDataSource);
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Append one or multiple values to a list.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the list
     * @param values Array of values to be appended
     * @return The length of the list after the push operation
     */
    public static Object rPush(BHandle redisDataSourceHandleValue, String key, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rPush(key, redisDataSource, createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }

    /**
     * Append one or multiple values to a list, only if the list exists.
     *
     * @param redisDataSourceHandleValue redis datasource
     * @param key The key of the list
     * @param values Array of values to be appended
     * @return The length of the list after the push operation
     */
    public static Object rPushX(BHandle redisDataSourceHandleValue, String key, BArray values) {
        try {
            RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
            return rPush(key, redisDataSource, createStringArrayFromBArray(values));
        } catch (Throwable e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, ModuleUtils.getModule(), StringUtils.fromString(e.getMessage()));
        }
    }
}
