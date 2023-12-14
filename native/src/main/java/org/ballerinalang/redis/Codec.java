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

package org.ballerinalang.redis;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum of Codecs which map with classes of type {@link io.lettuce.core.codec.RedisCodec}.
 */
public enum Codec {
    BYTE_ARRAY_CODEC("ByteArrayCodec"),
    STRING_CODEC("StringCodec"),
    UTF8_STRING_CODEC("Utf8StringCodec");

    static final Map<String, Codec> CODEC_MAP = new HashMap<>(3);
    final String codec;

    static {
        Codec[] codecs = values();
        for (Codec codec : codecs) {
            CODEC_MAP.put(codec.getCodecName(), codec);
        }
    }

    Codec(String codec) {
        this.codec = codec;
    }

    public static Codec fromCodecName(String codecName) {
        Codec codec = CODEC_MAP.get(codecName);
        if (codec == null) {
            throw new IllegalArgumentException("Unsupported Codec: " + codecName);
        }
        return codec;
    }

    public String getCodecName() {
        return codec;
    }
}
