/*
 * Copyright (c) 2018, WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
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

package org.ballerinalang.redis.endpoint;

import io.ballerina.runtime.internal.values.HandleValue;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.actions.AbstractRedisAction;

/**
 * {@code Close} action is used to close the Redis connection pool.
 *
 * @since 0.5.0
 */

public class Close extends AbstractRedisAction {

    /**
     * Close a redis server connection.
     *
     * @param redisDataSourceHandleValue redis datasource
     */
    public static void close(HandleValue redisDataSourceHandleValue) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        close(redisDataSource);
    }
}
