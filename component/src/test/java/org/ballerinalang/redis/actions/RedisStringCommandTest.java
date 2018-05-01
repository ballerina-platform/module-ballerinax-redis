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

import org.ballerinalang.launcher.util.BCompileUtil;
import org.ballerinalang.launcher.util.BRunUtil;
import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.model.values.BBoolean;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BValue;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RedisStringCommandTest extends RedisCommandsBaseTest {
    CompileResult compileResult;

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        compileResult = BCompileUtil.compile("samples/redis-string-command-test.bal");
        setUpDatabase();
    }

    private void setUpDatabase() {
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

    @Test
    public void testAppendCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testAppend");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 24);
    }

    @Test
    public void testBitCountCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testBitCount");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 69);
    }

    @Test
    public void testBitOpAndCommand() {
        BValue[] result = BRunUtil.invoke(compileResult, "tesBitOpAnd");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 3);
        Assert.assertEquals(redisCommands.get("tesBitOpAndDest"), "100");
    }

    @Test
    public void testBitOpOrCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "tesBitOpOr");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 3);
        Assert.assertEquals(redisCommands.get("tesBitOpOrDest"), "101");
    }

    @Test
    public void testBitOpNotCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "tesBitOpNot");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 3);
    }

    @Test
    public void testBitOpXorCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "tesBitOpXor");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 3);
    }

    @Test
    public void testDecrCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testDecr");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 7);
    }

    @Test
    public void testDecrByCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testDecrBy");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 7);
    }

    @Test
    public void testGetBitCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testGetBit");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 0);
    }

    @Test
    public void testGetRangeCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testGetRange");
        Assert.assertEquals(result[0].stringValue(), "3456");
    }

    @Test
    public void testGetSetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testGetSet");
        Assert.assertEquals(result[0].stringValue(), "testGetSetValue");
        Assert.assertEquals(redisCommands.get("testGetSetKey"), "testGetSetNewValue");
    }

    @Test
    public void testGetSetCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testGetSetNilOutput");
        assertNil(result);
    }

    @Test
    public void testIncrCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testIncr");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 7);
    }

    @Test
    public void testIncrByCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testIncrBy");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 7);
    }

    @Test
    public void testIncrByFloatCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testIncrByFloat");
        Assert.assertEquals((float) ((BFloat) result[0]).floatValue(), 7.2f);
    }

    @Test
    public void testSetBitCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSetBit");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 0);
    }

    @Test
    public void testSetExCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSetEx");
        Assert.assertEquals(result[0].stringValue(), OK_RESPONSE);
        Assert.assertEquals(redisCommands.get("testSetExKey"), "testSetExNewValue");
        // In order to wait until key expires (expiry time is 5 seconds)
        Thread.sleep(6000);
        Assert.assertEquals(redisCommands.get("testSetExKey"), null);
    }

    @Test
    public void testMGetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testMGet");
        Assert.assertEquals(result.length, 1);
        BMap bMap = (BMap) result[0];
        Assert.assertEquals(bMap.size(), 3);
        Assert.assertEquals(bMap.get("testMGetKey1").stringValue(), "testMGetValue1");
        Assert.assertEquals(bMap.get("testMGetKey2").stringValue(), "testMGetValue2");
        Assert.assertEquals(bMap.get("testMGetKey3").stringValue(), null);
    }

    @Test
    public void testMSetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testMSet");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), OK_RESPONSE);
        Assert.assertEquals(redisCommands.get("testMSetKey1"), "testMSetValue1");
        Assert.assertEquals(redisCommands.get("testMSetKey2"), "testMSetValue2");
        Assert.assertEquals(redisCommands.get("testMSetKey3"), "testMSetValue3");
    }

    @Test
    public void testMSetNxCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testMSetNx");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BBoolean) result[0]).booleanValue(), false);
        Assert.assertEquals(redisCommands.get("testMSetNxKey1"), "testMSetNxValue1");
        Assert.assertEquals(redisCommands.get("testMSetNxKey2"), null);
        Assert.assertEquals(redisCommands.get("testMSetNxKey3"), null);
    }

    @Test
    public void testPSetExCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testPSetEx");
        Assert.assertEquals(result[0].stringValue(), OK_RESPONSE);
        Assert.assertEquals(redisCommands.get("testPSetExKey"), "testPSetExNewValue");
        // In order to wait until key expires (expiry time is 5000 milli seconds)
        Thread.sleep(6000);
        Assert.assertEquals(redisCommands.get("testPSetExKey"), null);
    }

    @Test
    public void testSetNxCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSetNx");
        Assert.assertEquals(result[0].stringValue(), "true");
        Assert.assertEquals(redisCommands.get("testSetNxKey"), "testSetNxValue");
    }

    @Test
    public void testSetRangeCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSetRange");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 17);
        Assert.assertEquals(redisCommands.get("testSetRangeKey"), "te!!!etRangeValue");
    }

    @Test
    public void testStrlnCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testStrln");
        Assert.assertEquals(((BInteger) result[0]).intValue(), 14);
    }

    @Test
    public void testSetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSet");
        Assert.assertEquals(result[0].stringValue(), OK_RESPONSE);
    }

    @Test
    public void testGetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testGet");
        Assert.assertEquals(result[0].stringValue(), "GetTestValue");
    }

    @Test
    public void testGetCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testGetNilOutput");
        assertNil(result);
    }
}
