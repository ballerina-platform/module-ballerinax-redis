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
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaFunction;
import org.ballerinalang.natives.annotations.Receiver;
import org.ballerinalang.redis.Constants;
import org.ballerinalang.redis.RedisDataSource;
import org.ballerinalang.util.exceptions.BallerinaException;

/**
 * Initiates the data source.
 *
 * @since 0.5.4
 */

@BallerinaFunction(
        orgName = "ballerina", packageName = "redis",
        functionName = "initEndpoint",
        receiver = @Receiver(type = TypeKind.STRUCT, structType = "Client",
                             structPackage = "ballerina.redis"),
        args = {@Argument(name = "epName", type = TypeKind.STRING),
                @Argument(name = "config", type = TypeKind.STRUCT, structType = "ClientEndpointConfiguration")},
        isPublic = true
)
public class InitEndpoint extends BlockingNativeCallableUnit {

    @Override
    public void execute(Context context) {
        Struct clientEndpoint = BLangConnectorSPIUtil.getConnectorEndpointStruct(context);
        Struct clientEndpointConfig = clientEndpoint.getStructField(Constants.CLIENT_ENDPOINT_CONFIG);

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

        BStruct ballerinaClientConnector;
        if (clientEndpoint.getNativeData(Constants.B_CONNECTOR) != null) {
            ballerinaClientConnector = (BStruct) clientEndpoint.getNativeData(Constants.B_CONNECTOR);
        } else {
            ballerinaClientConnector = BLangConnectorSPIUtil
                    .createBStruct(context.getProgramFile(), Constants.REDIS_PACKAGE_PATH, Constants.CLIENT_CONNECTOR,
                            host, password, options, clientEndpointConfig);
            clientEndpoint.addNativeData(Constants.B_CONNECTOR, ballerinaClientConnector);
        }

        ballerinaClientConnector.addNativeData(Constants.CLIENT_CONNECTOR, redisDataSource);
        context.setReturnValues();
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
