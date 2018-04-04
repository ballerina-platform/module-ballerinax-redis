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

package org.ballerinalang.redis.util;

import redis.embedded.RedisExecProvider;
import redis.embedded.RedisServer;
import redis.embedded.util.OS;

import java.io.IOException;

/**
 * {@link CustomRedisServer} was written because in {@link RedisServer} {@link #redisReadyPattern()} returns the
 * pattern of an older redis version. Since latest Redis version is used for tests, it is required to override
 * {@link #redisReadyPattern()}.
 */
public class CustomRedisServer extends RedisServer {

    private static final String REDIS_READY_PATTERN = ".*Ready to accept connections*";

    public CustomRedisServer(String executable, Integer port) throws IOException {
        //TODO: Override this for windows with a suitable executable of the latest Redis version and test
        super(RedisExecProvider.defaultProvider().override(OS.UNIX, executable), port);
    }

    protected String redisReadyPattern() {
        return REDIS_READY_PATTERN;
    }
}
