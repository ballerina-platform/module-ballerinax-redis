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

package org.ballerinalang.redis;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants for Redis Connector.
 *
 * @since 0.5.0
 */
public class Constants {
    public static final int DEFAULT_REDIS_PORT = 6379;
    public static final String REDIS_PACKAGE_PATH = "wso2/redis:0.0.0";
    public static final String CALLER_ACTIONS = "CallerActions";
    public static final String REDIS_EXCEPTION_OCCURRED = "Exception Occurred while executing Redis action";
    public static final String ERROR_MESSAGE_FIELD = "message";

    /**
     * Endpoint configuration constants.
     */
    public static class EndpointConfig {
        public static final String HOST = "host";
        public static final String PASSWORD = "password";
        public static final String OPTIONS = "options";
        public static final String CLUSTERING_ENABLED = "isClusterConnection";
        public static final String POOLING_ENABLED = "connectionPooling";
    }

    /**
     * Enum of Codecs which map with classes of type {@link io.lettuce.core.codec.RedisCodec}
     */
    public enum Codec {
        BYTE_ARRAY_CODEC("ByteArrayCodec"), STRING_CODEC("StringCodec"), UTF8_STRING_CODEC("Utf8StringCodec");

        String codec;

        static Map<String, Codec> codecMap = new HashMap<>(3);

        static {
            Codec[] codecs = values();
            for (Codec codec : codecs) {
                codecMap.put(codec.getCodecName(), codec);
            }
        }

        Codec(String codec) {
            this.codec = codec;
        }

        public String getCodecName() {
            return codec;
        }

        public static Codec fromCodecName(String codecName) {
            Codec codec = codecMap.get(codecName);
            if (codec == null) {
                throw new IllegalArgumentException("Unsupported Codec: " + codecName);
            }
            return codec;
        }
    }
}
