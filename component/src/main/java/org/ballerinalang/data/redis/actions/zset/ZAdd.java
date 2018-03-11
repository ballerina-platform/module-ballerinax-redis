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

package org.ballerinalang.data.redis.actions.zset;

import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.data.redis.Constants;
import org.ballerinalang.data.redis.RedisDataSource;
import org.ballerinalang.data.redis.actions.AbstractRedisAction;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.util.exceptions.BallerinaException;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code {@link ZAdd}} Maps with "ZADD" operation of Redis.
 *
 * @since 0.5.0
 */
@BallerinaAction(packageName = "ballerina.data.redis",
                 actionName = "zAdd",
                 connectorName = Constants.CONNECTOR_NAME,
                 args = {
                         @Argument(name = "c",
                                   type = TypeKind.CONNECTOR)
                 })
public class ZAdd extends AbstractRedisAction {

    @Override
    public ConnectorFuture execute(Context context) {
        BConnector bConnector = (BConnector) getRefArgument(context, 0);
        RedisDataSource<String, String> redisDataSource = getDataSource(bConnector);

        String key =  getStringArgument(context, 0);
        BMap<String, BFloat> bMap = (BMap<String, BFloat>) getRefArgument(context, 1);
        if (bMap == null) {
            throw new BallerinaException("Member Map " + MUST_NOT_BE_NULL);
        }
        Map<String, Double> valueScoreMap = new HashMap<>(bMap.size());
        bMap.keySet().forEach(value -> valueScoreMap.put(value, (bMap.get(value).floatValue())));

        BInteger result = zAdd(key, redisDataSource, valueScoreMap);
        context.getControlStack().getCurrentFrame().returnValues[0] = result;
        return getConnectorFuture();
    }
}
