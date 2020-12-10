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

package org.ballerinalang.redis.endpoint;

import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.codec.Utf8StringCodec;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.internal.values.HandleValue;
import io.ballerina.runtime.internal.values.MapValueImpl;
import io.ballerina.runtime.api.values.BString;
import io.ballerina.runtime.api.values.BMap;
import org.ballerinalang.redis.Constants;
import org.ballerinalang.redis.RedisDataSource;
import io.ballerina.runtime.internal.util.exceptions.BallerinaException;

/**
 * Creates a Redis client.
 *
 * @since 0.5.0
 */
public class InitRedisClient  {

    /**
     * Initialize a redis client.
     *
     * @param config configuration map
     * @return a handle value
     */
    public static HandleValue initClient(MapValueImpl config) {
        BString host = config.getStringValue(StringUtils.fromString(Constants.EndpointConfig.HOST));
        BString password = config.getStringValue(StringUtils.fromString(Constants.EndpointConfig.PASSWORD));
        MapValueImpl options = (MapValueImpl) config.getMapValue(StringUtils.fromString(Constants.EndpointConfig.OPTIONS));
        

        RedisCodec<String, String> codec = retrieveRedisCodec(Constants.Codec.STRING_CODEC.getCodecName());
        boolean clusteringEnabled = options.getBooleanValue(StringUtils.fromString(
                Constants.EndpointConfig.CLUSTERING_ENABLED));
        boolean poolingEnabled = options.getBooleanValue(StringUtils.fromString(
                Constants.EndpointConfig.POOLING_ENABLED));

        RedisDataSource dataSource = new RedisDataSource<>(codec, clusteringEnabled, poolingEnabled);
        dataSource.init(host.toString(), password.toString(), options);

        return new HandleValue(dataSource);
    }


    /**
     * Retrieve redis codec.
     *
     * @param codecString codec string
     * @return redis codec
     */
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

    /**
     * Retreive a codec name.
     *
     * @param codecString codec string
     * @return codec name
     */
    private static Constants.Codec retrieveCodec(String codecString) {
        try {
            return Constants.Codec.fromCodecName(codecString);
        } catch (IllegalArgumentException e) {
            throw new BallerinaException("Unsupported Codec: " + codecString);
        }
    }
}
