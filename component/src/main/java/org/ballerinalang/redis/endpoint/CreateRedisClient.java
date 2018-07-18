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

import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.codec.Utf8StringCodec;
import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BlockingNativeCallableUnit;
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.connector.api.Struct;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.redis.Constants;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.util.exceptions.BallerinaException;

/**
 * Creates a Redis client.
 *
 * @since 0.5.0
 */
@BallerinaFunction(
        orgName = "wso2",
        packageName = "redis:0.0.0",
        functionName = "createClient",
        args = {
                @Argument(name = "clientEndpointConfig",
                          type = TypeKind.RECORD,
                          structType = "ClientEndpointConfiguration")
        },
        isPublic = true
)
public class CreateRedisClient extends BlockingNativeCallableUnit {

    @Override
    public void execute(Context context) {
        BMap<String, BValue> configBStruct = (BMap<String, BValue>) context.getRefArgument(0);
        Struct clientEndpointConfig = BLangConnectorSPIUtil.toStruct(configBStruct);

        //Extract parameters from the endpoint config
        String host = clientEndpointConfig.getStringField(Constants.EndpointConfig.HOST);
        String password = clientEndpointConfig.getStringField(Constants.EndpointConfig.PASSWORD);
        Struct options = clientEndpointConfig.getStructField(Constants.EndpointConfig.OPTIONS);

        // TODO: Dynamic codecs
        RedisCodec<String, String> codec = retrieveRedisCodec(Constants.Codec.STRING_CODEC.getCodecName());
        boolean clusteringEnabled = options.getBooleanField(Constants.EndpointConfig.CLUSTERING_ENABLED);
        boolean poolingEnabled = options.getBooleanField(Constants.EndpointConfig.POOLING_ENABLED);

        RedisDataSource<String, String> redisDataSource;
        redisDataSource = new RedisDataSource<>(codec, clusteringEnabled, poolingEnabled);
        redisDataSource.init(host, password, options);

        BMap<String, BValue> redisClient = BLangConnectorSPIUtil
                .createBStruct(context.getProgramFile(), Constants.REDIS_PACKAGE_PATH, Constants.CALLER_ACTIONS);
        redisClient.addNativeData(Constants.CALLER_ACTIONS, redisDataSource);
        context.setReturnValues(redisClient);

    }


    private RedisCodec retrieveRedisCodec(String codecString) {
        Constants.Codec codec = retrieveCodec(codecString);
        switch (codec) {
        case BYTE_ARRAY_CODEC:
            return new ByteArrayCodec();
        case STRING_CODEC:
            return new StringCodec();
        case UTF8_STRING_CODEC:
            return new Utf8StringCodec();
        default:
            throw new UnsupportedOperationException("Support for RedisCodec " + codec + " is not implemented yet");
        }
    }

    protected Constants.Codec retrieveCodec(String codecString) {
        try {
            return Constants.Codec.fromCodecName(codecString);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Unsupported Codec: " + codecString);
        }
    }
}
