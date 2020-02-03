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
import org.ballerinalang.jvm.values.HandleValue;
import org.ballerinalang.jvm.values.MapValue;
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
public class InitRedisClient  {

//    @Override
//    public void execute(Context context) {
//        BMap<String, BValue> configBStruct = (BMap<String, BValue>) context.getRefArgument(1);
//        Struct clientEndpointConfig = BLangConnectorSPIUtil.toStruct(configBStruct);
//
//        //Extract parameters from the endpoint config
//        String host = clientEndpointConfig.getStringField(Constants.EndpointConfig.HOST);
//        String password = clientEndpointConfig.getStringField(Constants.EndpointConfig.PASSWORD);
//        Struct options = clientEndpointConfig.getStructField(Constants.EndpointConfig.OPTIONS);
//
//        // TODO: Dynamic codecs
//        RedisCodec<String, String> codec = retrieveRedisCodec(Constants.Codec.STRING_CODEC.getCodecName());
//        boolean clusteringEnabled = options.getBooleanField(Constants.EndpointConfig.CLUSTERING_ENABLED);
//        boolean poolingEnabled = options.getBooleanField(Constants.EndpointConfig.POOLING_ENABLED);
//
//        RedisDataSource<String, String> redisDataSource;
//        redisDataSource = new RedisDataSource<>(codec, clusteringEnabled, poolingEnabled);
//        redisDataSource.init(host, password, options);
//
//        BMap<String, BValue> redisClient = (BMap<String, BValue>) context.getRefArgument(0);
//        redisClient.addNativeData(Constants.CLIENT, redisDataSource);
//        context.setReturnValues(redisClient);
//
//    }

    public static HandleValue initClient(MapValue config) {
        String host = config.getStringValue(Constants.EndpointConfig.HOST);
        String password = config.getStringValue(Constants.EndpointConfig.PASSWORD);
        MapValue options = config.getMapValue(Constants.EndpointConfig.OPTIONS);

        RedisCodec<String, String> codec = retrieveRedisCodec(Constants.Codec.STRING_CODEC.getCodecName());
        boolean clusteringEnabled = options.getBooleanValue(Constants.EndpointConfig.CLUSTERING_ENABLED);
        boolean poolingEnabled = options.getBooleanValue(Constants.EndpointConfig.POOLING_ENABLED);

        RedisDataSource dataSource = new RedisDataSource<>(codec, clusteringEnabled, poolingEnabled);
        dataSource.init(host, password, options);

        return new HandleValue(dataSource);
    }


    private static RedisCodec retrieveRedisCodec(String codecString) {
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

    protected static Constants.Codec retrieveCodec(String codecString) {
        try {
            return Constants.Codec.fromCodecName(codecString);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Unsupported Codec: " + codecString);
        }
    }
}
