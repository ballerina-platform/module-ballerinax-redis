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

package org.ballerinalang.redis.utils;

import redis.embedded.RedisExecProvider;
import redis.embedded.RedisServer;
import redis.embedded.util.OS;

import java.io.IOException;
import java.util.Locale;

/**
 * A custom redis server.
 */
public class CustomRedisServer extends RedisServer {

    private static final String REDIS_READY_PATTERN = ".*Ready to accept connections*";

    CustomRedisServer(String executable, Integer port) throws IOException {
        super(RedisExecProvider.defaultProvider().override(getOs(), executable), port);
    }

    private static OS getOs() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        if (os.contains("mac")) {
            return OS.MAC_OS_X;
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return OS.UNIX;
        } else {
            return OS.WINDOWS;
        }
    }

    protected String redisReadyPattern() {
        return REDIS_READY_PATTERN;
    }
}
