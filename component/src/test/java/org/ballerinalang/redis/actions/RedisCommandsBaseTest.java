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

package org.ballerinalang.redis.actions;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.redis.util.CustomRedisServer;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Base test class of Redis Command test classes.
 */
public class RedisCommandsBaseTest {
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    protected static final String OK_RESPONSE = "OK";
    protected static final String PONG = "PONG";
    protected static RedisCommands<String, String> redisCommands;
    private static RedisServer redisServer;

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        setUpServer();
        setUpClient();
    }

    private void setUpServer() throws IOException {
        redisServer = new CustomRedisServer("src/test/resources/redis-executable/redis-server-4.0.7", REDIS_PORT);
        redisServer.start();
    }

    private void setUpClient() {
        StatefulRedisConnection<String, String> statefulRedisConnection;
        RedisURI redisURI = RedisURI.Builder.redis(REDIS_HOST, REDIS_PORT).build();
        RedisClient redisClient = RedisClient.create(redisURI);
        statefulRedisConnection = redisClient.connect(StringCodec.UTF8);
        redisCommands = statefulRedisConnection.sync();
    }

    protected RedisCommands<String, String> connectToDB(int db) {
        StatefulRedisConnection<String, String> statefulRedisConnection;
        RedisURI redisURI = RedisURI.Builder.redis(REDIS_HOST, REDIS_PORT).withDatabase(db).build();
        RedisClient redisClient = RedisClient.create(redisURI);
        statefulRedisConnection = redisClient.connect(StringCodec.UTF8);
        return statefulRedisConnection.sync();
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUp() {
        redisServer.stop();
    }

    protected Set<String> createSetFromArray(String[] array) {
        Set<String> set = new HashSet<>(array.length);
        Collections.addAll(set, array);
        return set;
    }

    protected void assertNil(BValue[] result) {
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0], null);
    }
}
