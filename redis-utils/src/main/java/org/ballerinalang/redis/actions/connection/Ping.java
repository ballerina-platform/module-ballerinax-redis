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

package org.ballerinalang.redis.actions.connection;

import org.ballerinalang.bre.Context;
import org.ballerinalang.jvm.values.HandleValue;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.redis.Constants;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.actions.AbstractRedisAction;
import org.ballerinalang.util.exceptions.BallerinaException;

/**
 * {@code {@link Ping}} Maps with "PING" operation of Redis.
 *
 * @since 0.5.0
 */
public class Ping extends AbstractRedisAction {

    public static void ping(HandleValue redisDataSourceHandleValue) {
        RedisDataSource redisDataSource = (RedisDataSource) redisDataSourceHandleValue.getValue();
        try {
            ping(redisDataSource);
        } catch (Throwable e) {
            // Todo throw proper exception
            throw new BallerinaException("fail");
        }
    }
}
