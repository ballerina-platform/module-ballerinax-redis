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
package io.ballerina.lib.redis.utils;

import io.ballerina.lib.redis.config.ConfigMapper;
import io.ballerina.lib.redis.config.ConnectionConfig;
import io.ballerina.lib.redis.connection.RedisConnectionManager;
import io.ballerina.lib.redis.exceptions.RedisConnectorException;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.codec.Utf8StringCodec;

import static io.ballerina.lib.redis.utils.Codec.STRING_CODEC;
import static io.ballerina.lib.redis.utils.Constants.CONN_OBJ;
import static io.ballerina.lib.redis.utils.ConversionUtils.createBError;

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
    public static Object initClient(BObject client, BMap<BString, Object> config) {
        try {
            ConnectionConfig connectionConfig = ConfigMapper.from(config);
            RedisCodec<?, ?> codec = retrieveRedisCodec(STRING_CODEC);
            RedisConnectionManager<?, ?> connectionManager = new RedisConnectionManager<>(codec);
            connectionManager.init(connectionConfig);
            client.addNativeData(CONN_OBJ, connectionManager);
            return null;
        } catch (Throwable e) {
            String errMsg = "Error while initializing the redis client: " + e.getMessage();
            return createBError(new RedisConnectorException(errMsg, e.getCause()));
        }
    }

    /**
     * Retrieve redis codec.
     *
     * @return redis codec
     */
    public static RedisCodec<?, ?> retrieveRedisCodec(Codec codec) {
        return switch (codec) {
            case BYTE_ARRAY_CODEC -> new ByteArrayCodec();
            case STRING_CODEC -> new StringCodec();
            case UTF8_STRING_CODEC -> new Utf8StringCodec();
        };
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
