/*
 * Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.redis.utils;

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.codec.Utf8StringCodec;
import org.ballerinalang.redis.connection.RedisConnectionManager;
import org.ballerinalang.redis.exceptions.RedisConnectorException;

import static org.ballerinalang.redis.utils.Constants.CONFIG_CLUSTERING_ENABLED;
import static org.ballerinalang.redis.utils.Constants.CONFIG_HOST;
import static org.ballerinalang.redis.utils.Constants.CONFIG_OPTIONS;
import static org.ballerinalang.redis.utils.Constants.CONFIG_PASSWORD;
import static org.ballerinalang.redis.utils.Constants.CONFIG_POOLING_ENABLED;
import static org.ballerinalang.redis.utils.Constants.CONN_OBJ;
import static org.ballerinalang.redis.utils.Constants.EMPTY_STRING;
import static org.ballerinalang.redis.utils.Constants.LOCALHOST;
import static org.ballerinalang.redis.utils.ConversionUtils.createBError;

/**
 * Redis utility methods.
 *
 * @since 2.6.0
 */
public class RedisUtils {

    /**
     * Initialize the redis client.
     *
     * @param config redis client configuration as a map
     */
    @SuppressWarnings("unused")
    public static Object initClient(BObject client, BMap<?, ?> config) {
        try {
            BString host = config.getStringValue(StringUtils.fromString(CONFIG_HOST));
            BString password = config.getStringValue(StringUtils.fromString(CONFIG_PASSWORD));

            BString strOptions = StringUtils.fromString(CONFIG_OPTIONS);
            BMap<BString, Object> options = (BMap<BString, Object>) config.getMapValue(strOptions);

            RedisCodec<?, ?> codec = retrieveRedisCodec(Codec.STRING_CODEC.getCodecName());
            boolean clusteringEnabled = options.getBooleanValue(StringUtils.fromString(CONFIG_CLUSTERING_ENABLED));
            boolean poolingEnabled = options.getBooleanValue(StringUtils.fromString(CONFIG_POOLING_ENABLED));

            RedisConnectionManager<?, ?> connectionManager = new RedisConnectionManager<>(codec, clusteringEnabled,
                    poolingEnabled);
            String hostStr = host != null ? host.getValue() : LOCALHOST;
            String passwordStr = password != null ? password.getValue() : EMPTY_STRING;
            connectionManager.init(hostStr, passwordStr, options);
            client.addNativeData(CONN_OBJ, connectionManager);
            return null;
        } catch (Throwable e) {
            String errMsg = "Error while initializing the redis client: " + e.getMessage();
            return createBError(new RedisConnectorException(errMsg, e));
        }
    }

    /**
     * Retrieve redis codec.
     *
     * @param codecString codec string
     * @return redis codec
     */
    public static RedisCodec<?, ?> retrieveRedisCodec(String codecString) {
        Codec codec = retrieveCodec(codecString);
        return switch (codec) {
            case BYTE_ARRAY_CODEC -> new ByteArrayCodec();
            case STRING_CODEC -> new StringCodec();
            case UTF8_STRING_CODEC -> new Utf8StringCodec();
        };
    }

    /**
     * Retreive a codec name.
     *
     * @param codecString codec string
     * @return codec name
     */
    private static Codec retrieveCodec(String codecString) {
        try {
            return Codec.fromCodecName(codecString);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported Codec: " + codecString);
        }
    }

    /**
     * Retrieve the connection manager from the Ballerina redis client object.
     *
     * @param redisClient Client from the Ballerina redis client
     * @return RedisConnectionManager
     */
    @SuppressWarnings("unchecked")
    public static <K, V> RedisConnectionManager<K, V> getConnection(BObject redisClient) {
        return (RedisConnectionManager<K, V>) redisClient.getNativeData(CONN_OBJ);
    }
}
