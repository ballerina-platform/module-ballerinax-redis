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

package org.ballerinalang.redis.actions.list;

import org.ballerinalang.bre.Context;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.redis.Constants;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.redis.RedisDataSourceUtils;
import org.ballerinalang.redis.actions.AbstractRedisAction;
import org.ballerinalang.util.exceptions.BallerinaException;

/**
 * {@code bLPop} Maps with "BLPOP" operation of Redis.
 *
 * @since 0.5.0
 */
@BallerinaFunction(orgName = "wso2",
                   packageName = "redis:0.0.0",
                   functionName = "bLPop",
                   receiver = @Receiver(type = TypeKind.OBJECT,
                                        structType = Constants.CALLER_ACTIONS))
public class BLPop extends AbstractRedisAction {

    @Override
    public void execute(Context context) {
        BMap<String, BValue> bConnector = (BMap<String, BValue>) context.getRefArgument(0);
        RedisDataSource redisDataSource = (RedisDataSource) bConnector.getNativeData(Constants.CALLER_ACTIONS);

        long timeout = (int) context.getIntArgument(0);
        BStringArray keys = (BStringArray) context.getRefArgument(1);
        if (keys == null) {
            throw new BallerinaException("Key array" + MUST_NOT_BE_NULL);
        }
        BMap<String, BString> result = bLPop(timeout, redisDataSource, createArrayFromBStringArray(keys));
        try {
            setNullableReturnValues(result, context);
        } catch (Throwable e) {
            context.setReturnValues(RedisDataSourceUtils.getRedisConnectorError(context, e));
        }
    }
}
