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

import io.lettuce.core.api.sync.RedisCommands;
import org.ballerinalang.launcher.util.BCompileUtil;
import org.ballerinalang.launcher.util.BRunUtil;
import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.model.values.BValue;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class RedisKeyCommandTest extends RedisCommandsBaseTest {
    CompileResult compileResult;
    RedisCommands<String, String> redisCommandsDb1;

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        compileResult = BCompileUtil.compile("samples/redis-key-command-test.bal");
        redisCommandsDb1 = connectToDB(1);
        setUpDatabase();
    }

    private void setUpDatabase() {
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

    @Test
    public void testDelCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testDel");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 3);
        Assert.assertEquals(
                (long) redisCommands.exists("testDelValue1", "testDelValue2", "testDelValue3"), 0);
    }

    @Test
    public void testExistsCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testExists");
        Assert.assertEquals(result.length, 2);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 1);
        Assert.assertEquals(((BInteger) result[1]).intValue(), 0);
    }

    @Test
    public void testExpireCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testExpire");
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue(((BBoolean) (result[0])).booleanValue());
        // Wait for at least 3 seconds for the key to expire
        Thread.sleep(3000);
        Assert.assertEquals((long) redisCommands.exists("testExpireKey"), 0);
    }

    @Test
    public void testKeysCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testKeys");
        Assert.assertEquals(result.length, 1);
        BStringArray keys = (BStringArray) result[0];
        Assert.assertEquals(keys.size(), 3);
        List<String> matchingKeyList = new ArrayList<>(3);
        matchingKeyList.add("testKeysKey1");
        matchingKeyList.add("testKeysKey2");
        matchingKeyList.add("testKeysKey3");

        boolean allMatchingKeysRetrieved = true;
        for (int i = 0; i < 3; i++) {
            allMatchingKeysRetrieved = matchingKeyList.contains(keys.get(i));
            if (!allMatchingKeysRetrieved) {
                break;
            }
        }
        Assert.assertTrue(allMatchingKeysRetrieved);
    }

    @Test
    public void testMoveCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testMove");
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue(((BBoolean) result[0]).booleanValue());
        Assert.assertEquals((long) redisCommands.exists("testMoveKey"), 0);
        Assert.assertEquals((long) redisCommandsDb1.exists("testMoveKey"), 1);
    }

    @Test
    public void testPersistCommand() throws Exception {
        //Make the key expire in 3 seconds
        redisCommands.expire("testPersistKey", 3);
        BValue[] result = BRunUtil.invoke(compileResult, "testPersist");
        //Sleep at least 3 seconds so that original expiry period will pass
        Thread.sleep(3000);
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue(((BBoolean) result[0]).booleanValue());
        Assert.assertEquals((long) redisCommands.exists("testPersistKey"), 1);
    }

    @Test
    public void testPExpireCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testPExpire");
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue(((BBoolean) (result[0])).booleanValue());
        // Wait for at least 3 seconds for the key to expire
        Thread.sleep(3000);
        Assert.assertEquals((long) redisCommands.exists("testPExpireKey"), 0);
    }

    @Test
    public void testPTtlCommand() throws Exception {
        //Make the key expire in 10 seconds
        redisCommands.pexpire("testPTtlKey", 10000);
        BValue[] result = BRunUtil.invoke(compileResult, "testPTtl");
        long pttl = redisCommands.pttl("testPTtlKey");
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue(((BInteger) result[0]).intValue() >= pttl && ((BInteger) result[0]).intValue() < 10000);
    }

    @Test
    public void testRandomKeyCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRandomKey");
        Assert.assertEquals(result.length, 1);
    }

    @Test
    public void testRandomKeyCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRandomKeyNilOutput");
        assertNil(result);
    }

    @Test
    public void testRenameCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRename");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), OK_RESPONSE);
        Assert.assertEquals((long) redisCommands.exists("testRenameKey"), 0);
        Assert.assertEquals((long) redisCommands.exists("testRenameKey1"), 1);
    }

    @Test
    public void testRenameNxCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRenameNx");
        Assert.assertEquals(result.length, 2);
        Assert.assertTrue(((BBoolean) result[0]).booleanValue());
        Assert.assertFalse(((BBoolean) result[1]).booleanValue());

        Assert.assertEquals((long) redisCommands.exists("testRenameNxKey"), 0);
        Assert.assertEquals((long) redisCommands.exists("testRenameNxKeyRenamed"), 1);

        Assert.assertEquals((long) redisCommands.exists("testRenameNxKey1"), 1);
    }

    @Test
    public void testSortCommand() throws Exception {
        BValue[] args = {};
        BValue[] result = BRunUtil.invokeFunction(compileResult, "testSort", args);
        Assert.assertEquals(result.length, 1);
        BStringArray sortedArray = (BStringArray) result[0];
        String[] listValues = { "0", "1", "2", "3", "4", "8" };
        boolean elementsInOrder = true;
        for (int i = 0; i < 6; i++) {
            elementsInOrder = listValues[i].equals(sortedArray.get(i));
            if (!elementsInOrder) {
                break;
            }
        }
        Assert.assertTrue(elementsInOrder);
    }

    @Test
    public void testTtlCommand() throws Exception {
        //Make the key expire in 10 seconds
        redisCommands.pexpire("testTtlKey", 10);
        BValue[] result = BRunUtil.invoke(compileResult, "testTtl");
        long ttl = redisCommands.ttl("testTtlKey");
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue(((BInteger) result[0]).intValue() >= ttl && ((BInteger) result[0]).intValue() < 10);
    }

    @Test
    public void testTypeCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testType");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue().toUpperCase(), "STRING");
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        redisCommandsDb1.quit();
    }

}
