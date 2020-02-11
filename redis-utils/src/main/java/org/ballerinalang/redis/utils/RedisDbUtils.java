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

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import redis.embedded.RedisServer;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class RedisDbUtils {
    private static RedisServer redisServer;
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    protected static RedisCommands<String, String> redisCommands;

    public static void initServer() throws IOException {
        String executablePath = Paths.get(System.getProperty("user.dir")).resolve("src").resolve("redis").
                resolve("tests").resolve("resources").resolve("redis-executable").resolve("redis-server-5.0.7").toString();
        redisServer = new CustomRedisServer(executablePath, REDIS_PORT);
        redisServer.start();
    }

    public static void stopServer() {
        redisServer.stop();
    }

    public static void setupStringDatabase() {
        setUpClient();
        String[] keyArray = {
                "GetTestKey", "AppendTestKey", "BitCountTestKey", "testBitOpKey1", "testBitOpKey2", "testDecrKey",
                "testDecrByKey", "testGetBitKey", "testGetRangekey", "testGetSetKey", "testIncrKey", "testIncrByKey",
                "testIncrByFloatKey", "testSetBitKey", "testSetExKey", "testSetRangeKey", "testStrlnKey",
                "testMGetKey1", "testMGetKey2", "testMSetNxKey1", "testPSetExKey"
        };

        String[] valueArray = {
                "GetTestValue", "AppendTestValue", "BitCountTestValue", "101", "100", "8", "10", "testGetBitValue",
                "1234567", "testGetSetValue", "6", "5", "7", "10101", "testSetExValue", "testSetRangeValue",
                "testStrlnValue", "testMGetValue1", "testMGetValue2", "testMSetNxValue1", "testSetExValue"
        };

        for (int i = 0; i < keyArray.length; i++) {
            redisCommands.set(keyArray[i], valueArray[i]);
        }
    }

    public static void setupKeyDatabase() {
        setUpClient();
        String[] keyArray = {
                "testDelKey1", "testDelKey2", "testDelKey3", "testExistsKey", "testExpireKey", "testKeysKey1",
                "testKeysKey2", "testKeysKey3", "testMoveKey", "testPersistKey", "testPExpireKey", "testPTtlKey",
                "testRenameKey", "testRenameNxKey", "testRenameNxKey1", "testRenameNxKeyExisting", "testTypeKey"
        };
        String[] valueArray = {
                "testDelValue1", "testDelValue2", "testDelValue3", "testExistsValue", "testExpireValue",
                "testKeysValue1", "testKeysValue2", "testKeysValue3", "testMoveValue4", "testPersistValue",
                "testPExpireValue", "testPTtlValue", "testRenameValue", "testRenameNxValue", "testRenameNxValue1",
                "testRenameNxValueExisting", "testTypeValue"
        };

        for (int i = 0; i < keyArray.length; i++) {
            redisCommands.set(keyArray[i], valueArray[i]);
        }

        String listKey = "testSortKey";
        String[] listValues = { "2", "3", "1", "4", "8", "0" };
        redisCommands.lpush(listKey, listValues);
    }

    public static void setupListDatabase() {
        setUpClient();
        String[] keyArray = {
                "testBLPopKey", "testBRPopKey", "testLIndexKey", "testLInsertKey", "testLLenKey", "testLPopKey",
                "testLPushKey", "testLPushXKey", "testLRangeKey", "testLRemKey", "testLSetKey", "testLTrimKey",
                "testRPopKey", "testRPopLPushKey1", "testRPopLPushKey2", "testRPushKey", "testRPushXKey"
        };
        String[][] valueArray = {
                { "testBLPopValue1", "testBLPopValue2" }, { "testBRPopValue1", "testBRPopValue2" },
                { "testLIndexValue1", "testLIndexValue2" },
                { "testLInsertValue1", "testLInsertValue2", "pivotValue", "testLInsertValue4" },
                { "testLLenValue1", "testLLenValue2", "testLLenValue3" }, { "testLPopValue1", "testLPopValue2" },
                { "testLPushValue1" }, { "testLPushXValue1" }, { "0", "1", "2", "3", "4" },
                { "testLRemValue1", "toBeRemovedValue", "toBeRemovedValue", "testLRemValue4" },
                { "testLSetValue1", "testLSetValue2", "testLSetValue3" }, { "One", "Two", "Three" },
                { "testRPopValue1", "testRPopValue2" }, { "One", "Two", "Three" }, { "One", "Two" },
                { "testRPushValue1" }, {"testRPushXValue1"}
        };

        for (int i = 0; i < keyArray.length; i++) {
            redisCommands.lpush(keyArray[i], valueArray[i]);
        }
    }

    public static void setupHashDatabase() {
        setUpClient();
        String[] hashKeyArray = {
                "testHDelKey", "testHGetKey", "testHExistsKey", "testHGetAllKey", "testHIncrByKey",
                "testHIncrByFloatKey", "testHKeysKey", "testHLenKey", "testHMGetKey", "testHStrlnKey", "testHValsKey"
        };

        String[][] hashFieldArray = {
                { "testHDelField1", "testHDelField2", "testHDelField3" }, { "testHGetField1", "testHGetField2" },
                { "testHExistsField1", "testHExistsField2" }, { "testHGetAllField1", "testHGetAllField2" },
                { "testHIncrByField1" }, { "testHIncrByFloatField1" },
                { "testHKeysField1", "testHKeysField2", "testHKeysField3" },
                { "testHLenField1", "testHLenField2", "testHLenField3" },
                { "testHMGetField1", "testHMGetField2", "testHMGetField3" }, { "testHStrlnField1" },
                { "testHValsField1", "testHValsField2", "testHValsField3" }
        };

        String[][] hashValueArray = {
                { "testHDelValue1", "testHDelValue2", "testHDelValue3" }, { "testHGetValue1", "testHGetValue2" },
                { "testHExistsValue1", "testHExistsValue2" }, { "testHGetAllValue1", "testHGetAllValue2" }, { "6" },
                { "7" }, { "testHKeysValue1", "testHKeysValue2", "testHKeysValue3" },
                { "testHLenValue1", "testHLenValue2", "testHLenValue3" },
                { "testHMGetValue1", "testHMGetValue2", "testHMGetValue3" }, { "testHStrlnValue1" },
                { "testHValsValue1", "testHValsValue2", "testHValsValue3" }
        };

        for (int i = 0; i < hashKeyArray.length; i++) {
            Map<String, String> map = new HashMap<>(hashFieldArray[i].length);
            for (int j = 0; j < hashFieldArray[i].length; j++) {
                map.put(hashFieldArray[i][j], hashValueArray[i][j]);
            }
            redisCommands.hmset(hashKeyArray[i], map);
        }
    }

    private static void setUpClient() {
        StatefulRedisConnection<String, String> statefulRedisConnection;
        RedisURI redisURI = RedisURI.Builder.redis(REDIS_HOST, REDIS_PORT).build();
        RedisClient redisClient = RedisClient.create(redisURI);
        statefulRedisConnection = redisClient.connect(StringCodec.UTF8);
        redisCommands = statefulRedisConnection.sync();
    }

//    public static BString get(String key) {
//       return new BString("bbb");
//    }
}
