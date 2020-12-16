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
import io.lettuce.core.ScoredValue;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.codec.StringCodec;
import io.ballerina.runtime.api.Module;
import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BString;
import redis.embedded.RedisServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.ballerinalang.redis.Constants.REDIS_CONNECTOR_VERSION;
import static org.ballerinalang.redis.Constants.REDIS_EXCEPTION_OCCURRED;

/**
 * Redis database utils to run unit tests.
 */
public class RedisDbUtils {
    private static RedisServer redisServer;
    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static RedisCommands<String, String> redisCommands;
    private static final Module PACKAGE_ID_REDIS = new Module("ballerinax", "redis",
            REDIS_CONNECTOR_VERSION);

    /**
     * Initialize custom redis server.
     *
     * @throws IOException exception
     */
    public static Object initServer() throws IOException {
        String scriptPath = Paths.get(System.getProperty("user.dir")).
                resolve("tests").resolve("resources").resolve("setup.sh").toString();
                        System.out.println(System.getProperty("user.dir"));
                        System.out.println(scriptPath);
        ProcessBuilder processBuilder = new ProcessBuilder();
        System.out.println(processBuilder);
        processBuilder.command("bash", scriptPath);
        System.out.println(processBuilder.command("bash", scriptPath));
        Process process = processBuilder.start();
        System.out.println(process);
        System.out.println(process);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),
                StandardCharsets.UTF_8))) {
            System.out.println("Inside try");
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                String executablePath = Paths.get(System.getProperty("user.dir")).resolve("redis-5.0.7").resolve("src").
                        resolve("redis-server").toString();
                                        System.out.println(executablePath);
                redisServer = new CustomRedisServer(executablePath, REDIS_PORT);
                redisServer.start();
            } else {
                return ErrorCreator.createError(StringUtils.fromString(REDIS_EXCEPTION_OCCURRED), StringUtils.fromString(output.toString()));
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("inside catch");
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
        process.destroy();
        return "OK";
    }

    /**
     * Stop redis server.
     */
    public static Object stopServer() throws IOException {
        redisServer.stop();
        String scriptPath = Paths.get(System.getProperty("user.dir")).
                resolve("tests").resolve("resources").resolve("cleanup.sh").toString();
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", scriptPath);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),
                StandardCharsets.UTF_8))) {
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            int exitVal = process.waitFor();
            if (exitVal != 0) {
                return ErrorCreator.createError(StringUtils.fromString(REDIS_EXCEPTION_OCCURRED), StringUtils.fromString(output.toString()));
            }
        } catch (IOException | InterruptedException e) {
            return ErrorCreator.createDistinctError(REDIS_EXCEPTION_OCCURRED, PACKAGE_ID_REDIS, StringUtils.fromString(e.getMessage()));
        }
        process.destroy();
        return "OK";
    }

    /**
     * Populate string database.
     */
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

    /**
     * Populate key database.
     */
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

    /**
     * Populate list database.
     */
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

    /**
     * Populate hash database.
     */
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

    /**
     * Populate set database.
     */
    public static void setupSetDatabase() {
        setUpClient();
        String[] keyArray = {
                "testSAddKey", "testSDiffKey1", "testSDiffKey2", "testSInterKey1", "testSInterKey2", "testSIsMemberKey",
                "testSMembersKey", "testSPopKey", "testSRandMemberKey", "testSRemKey", "testUnionKey1", "testUnionKey2"

        };
        String[][] valueArray = {
                { "testSAddValue1", "testSAddValue2" }, { "One", "Two", "Three", "Four" }, { "One", "Two" },
                { "One", "Two", "Three", "Four" }, { "One", "Two" }, { "testSIsMemberValue" },
                { "testSMembersValue1", "testSMembersValue2", "testSMembersValue3" },
                { "testSPopValue1", "testSPopValue2", "testSPopValue3" },
                { "testSRandMemberValue1", "testSRandMemberValue2", "testSRandMemberValue3" },
                { "testSRemValue1", "testSRemValue2", "testSRemValue3" }, { "testUnionValue1", "testUnionValue2" },
                { "testUnionValue2", "testUnionValue3", "testUnionValue4" }
        };
        for (int i = 0; i < keyArray.length; i++) {
            redisCommands.sadd(keyArray[i], valueArray[i]);
        }
    }

    /**
     * Populate sorted set database.
     */
    public static void setupSortedSetDatabase() {
        setUpClient();
        String[] keyArray = {
                "testZAddKey", "testZCardKey", "testZCountKey", "testZIncrByKey", "testZInterStoreKey1",
                "testZInterStoreKey2", "testZInterStoreDestKey", "testZLexCountKey", "testZRangeKey", "testZRankKey",
                "testZRemKey", "testZRemRangeByLexKey", "testZRemRangeByRankKey", "testZRemRangeByScoreKey",
                "testZRangeByLexKey", "testZScoreKey", "testZUnionStoreKey1", "testZUnionStoreKey2"
        };

        ScoredValue<String> testZAddValue1 = ScoredValue.fromNullable(1, "testZAddValue1");

        ScoredValue<String> testZCardValue1 = ScoredValue.fromNullable(1, "testZCardValue1");
        ScoredValue<String> testZCardValue2 = ScoredValue.fromNullable(1, "testZCardValue2");

        ScoredValue<String> testZCountValue1 = ScoredValue.fromNullable(1, "testZCountValue1");
        ScoredValue<String> testZCountValue2 = ScoredValue.fromNullable(2, "testZCountValue2");
        ScoredValue<String> testZCountValue3 = ScoredValue.fromNullable(2, "testZCountValue3");
        ScoredValue<String> testZCountValue4 = ScoredValue.fromNullable(4, "testZCountValue4");

        ScoredValue<String> testZIncrByValue1 = ScoredValue.fromNullable(6, "testZIncrByValue1");

        ScoredValue<String> testZInterStoreValue1 = ScoredValue.fromNullable(1, "One");
        ScoredValue<String> testZInterStoreValue2 = ScoredValue.fromNullable(6, "Six");
        ScoredValue<String> testZInterStoreValue3 = ScoredValue.fromNullable(3, "Three");

        ScoredValue<String> testZInterStoreValue4 = ScoredValue.fromNullable(1, "One");
        ScoredValue<String> testZInterStoreValue5 = ScoredValue.fromNullable(2, "One");
        ScoredValue<String> testZInterStoreValue6 = ScoredValue.fromNullable(6, "Six");

        ScoredValue<String> testZInterStoreValue7 = ScoredValue.fromNullable(7, "Seven");

        ScoredValue<String> testZLexCountValue1 = ScoredValue.fromNullable(1, "a");
        ScoredValue<String> testZLexCountValue2 = ScoredValue.fromNullable(1, "b");
        ScoredValue<String> testZLexCountValue3 = ScoredValue.fromNullable(1, "c");
        ScoredValue<String> testZLexCountValue4 = ScoredValue.fromNullable(1, "d");
        ScoredValue<String> testZLexCountValue5 = ScoredValue.fromNullable(1, "e");
        ScoredValue<String> testZLexCountValue6 = ScoredValue.fromNullable(1, "f");
        ScoredValue<String> testZLexCountValue7 = ScoredValue.fromNullable(1, "g");

        ScoredValue<String> testZRangeValue1 = ScoredValue.fromNullable(1, "a");
        ScoredValue<String> testZRangeValue2 = ScoredValue.fromNullable(2, "b");
        ScoredValue<String> testZRangeValue3 = ScoredValue.fromNullable(5, "c");
        ScoredValue<String> testZRangeValue4 = ScoredValue.fromNullable(1, "d");
        ScoredValue<String> testZRangeValue5 = ScoredValue.fromNullable(3, "e");
        ScoredValue<String> testZRangeValue6 = ScoredValue.fromNullable(4, "f");
        ScoredValue<String> testZRangeValue7 = ScoredValue.fromNullable(8, "g");

        ScoredValue<String> testZRankValue1 = ScoredValue.fromNullable(1, "a");
        ScoredValue<String> testZRankValue2 = ScoredValue.fromNullable(2, "b");
        ScoredValue<String> testZRankValue3 = ScoredValue.fromNullable(5, "c");

        ScoredValue<String> testZRemValue1 = ScoredValue.fromNullable(1, "a");
        ScoredValue<String> testZRemValue2 = ScoredValue.fromNullable(2, "b");
        ScoredValue<String> testZRemValue3 = ScoredValue.fromNullable(5, "c");

        ScoredValue<String> testZRemRangeByLexValue1 = ScoredValue.fromNullable(1, "foo");
        ScoredValue<String> testZRemRangeByLexValue2 = ScoredValue.fromNullable(1, "zap");
        ScoredValue<String> testZRemRangeByLexValue3 = ScoredValue.fromNullable(1, "zip");
        ScoredValue<String> testZRemRangeByLexValue4 = ScoredValue.fromNullable(1, "ALPHA");
        ScoredValue<String> testZRemRangeByLexValue5 = ScoredValue.fromNullable(1, "alpha");
        ScoredValue<String> testZRemRangeByLexValue6 = ScoredValue.fromNullable(1, "aaaa");
        ScoredValue<String> testZRemRangeByLexValue7 = ScoredValue.fromNullable(1, "b");
        ScoredValue<String> testZRemRangeByLexValue8 = ScoredValue.fromNullable(1, "c");
        ScoredValue<String> testZRemRangeByLexValue9 = ScoredValue.fromNullable(1, "d");
        ScoredValue<String> testZRemRangeByLexValue10 = ScoredValue.fromNullable(1, "e");

        ScoredValue<String> testZRemRangeByRankValue1 = ScoredValue.fromNullable(1, "One");
        ScoredValue<String> testZRemRangeByRankValue2 = ScoredValue.fromNullable(2, "Two");
        ScoredValue<String> testZRemRangeByRankValue3 = ScoredValue.fromNullable(2, "Three");
        ScoredValue<String> testZRemRangeByRankValue4 = ScoredValue.fromNullable(3, "Four");
        ScoredValue<String> testZRemRangeByRankValue5 = ScoredValue.fromNullable(5, "Five");
        ScoredValue<String> testZRemRangeByRankValue6 = ScoredValue.fromNullable(6, "Six");

        ScoredValue<String> testZRemRangeByScoreValue1 = ScoredValue.fromNullable(1, "One");
        ScoredValue<String> testZRemRangeByScoreValue2 = ScoredValue.fromNullable(2, "Two");
        ScoredValue<String> testZRemRangeByScoreValue3 = ScoredValue.fromNullable(2, "Three");
        ScoredValue<String> testZRemRangeByScoreValue4 = ScoredValue.fromNullable(3, "Four");
        ScoredValue<String> testZRemRangeByScoreValue5 = ScoredValue.fromNullable(5, "Five");
        ScoredValue<String> testZRemRangeByScoreValue6 = ScoredValue.fromNullable(6, "Six");

        ScoredValue<String> testZRangeByLexValue1 = ScoredValue.fromNullable(1, "a");
        ScoredValue<String> testZRangeByLexValue2 = ScoredValue.fromNullable(1, "b");
        ScoredValue<String> testZRangeByLexValue3 = ScoredValue.fromNullable(1, "c");
        ScoredValue<String> testZRangeByLexValue4 = ScoredValue.fromNullable(1, "d");
        ScoredValue<String> testZRangeByLexValue5 = ScoredValue.fromNullable(1, "e");
        ScoredValue<String> testZRangeByLexValue6 = ScoredValue.fromNullable(1, "f");
        ScoredValue<String> testZRangeByLexValue7 = ScoredValue.fromNullable(1, "g");

        ScoredValue<String> testZScoreValue1 = ScoredValue.fromNullable(1.2, "a");
        ScoredValue<String> testZScoreValue2 = ScoredValue.fromNullable(2.3, "b");

        ScoredValue<String> testZUnionStoreValue1 = ScoredValue.fromNullable(1, "a");
        ScoredValue<String> testZUnionStoreValue2 = ScoredValue.fromNullable(2, "b");
        ScoredValue<String> testZUnionStoreValue3 = ScoredValue.fromNullable(5, "c");
        ScoredValue<String> testZUnionStoreValue4 = ScoredValue.fromNullable(1, "d");

        ScoredValue<String> testZUnionStoreValue5 = ScoredValue.fromNullable(1, "a");
        ScoredValue<String> testZUnionStoreValue6 = ScoredValue.fromNullable(4, "f");
        ScoredValue<String> testZUnionStoreValue7 = ScoredValue.fromNullable(3, "g");

        ScoredValue[][] valueArray = new ScoredValue[][] {
                new ScoredValue[] { testZAddValue1 }, new ScoredValue[] { testZCardValue1, testZCardValue2 },
                new ScoredValue[] { testZCountValue1, testZCountValue2, testZCountValue3, testZCountValue4 },
                new ScoredValue[] { testZIncrByValue1 },
                new ScoredValue[] { testZInterStoreValue1, testZInterStoreValue2, testZInterStoreValue3 },
                new ScoredValue[] { testZInterStoreValue4, testZInterStoreValue5, testZInterStoreValue6 },
                new ScoredValue[] { testZInterStoreValue7 }, new ScoredValue[] {
                testZLexCountValue1, testZLexCountValue2, testZLexCountValue3, testZLexCountValue4, testZLexCountValue5,
                testZLexCountValue6, testZLexCountValue7
        }, new ScoredValue[] {
                testZRangeValue1, testZRangeValue2, testZRangeValue3, testZRangeValue4, testZRangeValue5,
                testZRangeValue6, testZRangeValue7
        }, new ScoredValue[] { testZRankValue1, testZRankValue2, testZRankValue3 },
                new ScoredValue[] { testZRemValue1, testZRemValue2, testZRemValue3 }, new ScoredValue[] {
                testZRemRangeByLexValue1, testZRemRangeByLexValue2, testZRemRangeByLexValue3, testZRemRangeByLexValue4,
                testZRemRangeByLexValue5, testZRemRangeByLexValue6, testZRemRangeByLexValue7, testZRemRangeByLexValue8,
                testZRemRangeByLexValue9, testZRemRangeByLexValue10
        }, new ScoredValue[] {
                testZRemRangeByRankValue1, testZRemRangeByRankValue2, testZRemRangeByRankValue3,
                testZRemRangeByRankValue4, testZRemRangeByRankValue5, testZRemRangeByRankValue6
        }, new ScoredValue[] {
                testZRemRangeByScoreValue1, testZRemRangeByScoreValue2, testZRemRangeByScoreValue3,
                testZRemRangeByScoreValue4, testZRemRangeByScoreValue5, testZRemRangeByScoreValue6
        }, new ScoredValue[] {
                testZRangeByLexValue1, testZRangeByLexValue2, testZRangeByLexValue3, testZRangeByLexValue4,
                testZRangeByLexValue5, testZRangeByLexValue6, testZRangeByLexValue7
        }, new ScoredValue[] { testZScoreValue1, testZScoreValue2 }, new ScoredValue[] {
                testZUnionStoreValue1, testZUnionStoreValue2, testZUnionStoreValue3, testZUnionStoreValue4
        }, new ScoredValue[] { testZUnionStoreValue5, testZUnionStoreValue6, testZUnionStoreValue7 }
        };

        for (int i = 0; i < keyArray.length; i++) {
            redisCommands.zadd(keyArray[i], valueArray[i]);
        }
    }

    /**
     * Create a redis database client.
     */
    private static void setUpClient() {
        StatefulRedisConnection<String, String> statefulRedisConnection;
        RedisURI redisURI = RedisURI.Builder.redis(REDIS_HOST, REDIS_PORT).build();
        RedisClient redisClient = RedisClient.create(redisURI);
        statefulRedisConnection = redisClient.connect(StringCodec.UTF8);
        redisCommands = statefulRedisConnection.sync();
    }

    /**
     * Get value for a given key.
     *
     * @param key key
     * @return value
     */
    public static BString getValue(String key) {
        if (redisCommands.get(key) != null) {
            return StringUtils.fromString(redisCommands.get(key));
        } else {
            return StringUtils.fromString("");
        }
    }

    /**
     * Check if set contains a member.
     *
     * @param key key
     * @param value member
     * @return True if member exists in set, false if not
     */
    public static boolean sisMember(String key, String value) {
        return redisCommands.sismember(key, value);
    }

    /**
     * Check if hash exists.
     *
     * @param key key
     * @param field field
     * @return True if hash exists, false if not
     */
    public static boolean hexists(String key, String field) {
        return redisCommands.hexists(key, field);
    }

    /**
     * Get a value of a hash.
     *
     * @param key key
     * @param field field
     * @return hash value
     */
    public static String hget(String key, String field) {
        return redisCommands.hget(key, field);
    }

    /**
     * Check if key exists.
     *
     * @param key key
     * @return 1 if key exists, 0 if not
     */
    public static long exist(String key) {
        return redisCommands.exists(key);
    }

    /**
     * Make a key expire after a given time.
     *
     * @param key key
     * @param duration duration
     */
    public static void pexpire(String key, int duration) {
        redisCommands.pexpire(key, duration);
    }

    /**
     * Get the time to live for a key.
     *
     * @param key key
     * @return time to live
     */
    public static long pttl(String key) {
        return redisCommands.pttl(key);
    }

    /**
     * Get the value using an index of a set.
     *
     * @param key key
     * @param index index
     * @return value
     */
    public static BString lindex(String key, int index) {
        return StringUtils.fromString(redisCommands.lindex(key, index));
    }
}
