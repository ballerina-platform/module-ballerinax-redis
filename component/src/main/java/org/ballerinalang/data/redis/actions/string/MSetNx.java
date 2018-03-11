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

package org.ballerinalang.data.redis.actions.string;

import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.data.redis.Constants;
import org.ballerinalang.data.redis.RedisDataSource;
import org.ballerinalang.data.redis.actions.AbstractRedisAction;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BString;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;

/**
 * {@code mSetnx} Maps with "MSETNX" operation of Redis.
 *
 * @since 0.5.0
 */
@BallerinaAction(packageName = "ballerina.data.redis",
                 actionName = "mSetNx",
                 connectorName = Constants.CONNECTOR_NAME,
                 args = {
                         @Argument(name = "c",
                                   type = TypeKind.CONNECTOR)
                 })
public class MSetNx extends AbstractRedisAction {

    @Override
    public ConnectorFuture execute(Context context) {
        BConnector bConnector = (BConnector) getRefArgument(context, 0);
        RedisDataSource<String, String> redisDataSource = getDataSource(bConnector);

        BMap<String, BString> bMap = (BMap<String, BString>) getRefArgument(context, 1);
        BBoolean result = mSetnx(createMapFromBMap(bMap), redisDataSource);
        context.getControlStack().getCurrentFrame().returnValues[0] = result;
        return getConnectorFuture();
    }
}
