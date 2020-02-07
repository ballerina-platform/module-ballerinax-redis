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

package org.ballerinalang.redis.utils;

import redis.embedded.RedisServer;

import java.io.IOException;
import java.nio.file.Paths;

public class RedisDbUtils {
    private static RedisServer redisServer;
    private static final int REDIS_PORT = 6379;

    public static void initServer() throws IOException {
        String executablePath = Paths.get(System.getProperty("user.dir")).resolve("src").resolve("redis").resolve("tests").resolve("resources").resolve("redis-executable").resolve("redis-server-5.0.7").toString();
        redisServer = new CustomRedisServer(executablePath, REDIS_PORT);
        redisServer.start();
    }
}
