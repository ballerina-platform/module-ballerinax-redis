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
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.model.values.BValue;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class RedisListCommandTest extends RedisCommandsBaseTest {
    CompileResult compileResult;

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        compileResult = BCompileUtil.compile("samples/redis-list-command-test.bal");
        setUpDatabase();
    }

    private void setUpDatabase() {
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

    @Test
    public void testBLPopCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testBLPop");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BMap) result[0]).get("testBLPopKey").stringValue(), "testBLPopValue2");

    }

    @Test
    public void testBLPopCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testBLPopNilOutput");
        assertNil(result);
    }

    @Test
    public void testBRPopCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testBRPop");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BMap) result[0]).get("testBRPopKey").stringValue(), "testBRPopValue1");
    }

    @Test
    public void testBRPopCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testBRPopNilOutput");
        assertNil(result);
    }

    @Test
    public void testLIndexCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLIndex");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), "testLIndexValue2");
    }

    @Test
    public void testLInsertCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLInsert");
        Assert.assertEquals(Integer.parseInt(result[0].stringValue()), 5);
    }

    @Test
    public void testLLenCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLLen");
        Assert.assertEquals(Integer.parseInt(result[0].stringValue()), 3);
    }

    @Test
    public void testLPopCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLPop");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), "testLPopValue2");
    }

    @Test
    public void testLPopCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLPopNilOutput");
        assertNil(result);
    }

    @Test
    public void testLPushCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLPush");
        Assert.assertEquals(Integer.parseInt(result[0].stringValue()), 3);
    }

    @Test
    public void testLPushXCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLPushX");
        Assert.assertEquals(Integer.parseInt(result[0].stringValue()), 3);
    }

    @Test
    public void testLRangeCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLRange");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BStringArray) result[0]).size(), 3);
    }

    @Test
    public void testLRemCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLRem");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(Integer.parseInt(result[0].stringValue()), 2);
    }

    @Test
    public void testLSetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLSet");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), OK_RESPONSE);
        Assert.assertEquals(redisCommands.lindex("testLSetKey", 1), "testLSetValue2New");
    }

    @Test
    public void testLTrimCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testLTrim");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), OK_RESPONSE);
        List<String> list = redisCommands.lrange("testLTrimKey", 0, -1);
        Assert.assertEquals(list.size(), 2);
        Assert.assertEquals(list.get(0), "Two");
        Assert.assertEquals(list.get(1), "One");
    }

    @Test
    public void testRPopCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRPop");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), "testRPopValue1");
    }

    @Test
    public void testRPopCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRPopNilOutput");
        assertNil(result);
    }

    @Test
    public void testRPopLPushCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRPopLPush");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), "One");
    }

    @Test
    public void testRPushCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRPush");
        Assert.assertEquals(Integer.parseInt(result[0].stringValue()), 3);
    }

    @Test
    public void testRPushXCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testRPushX");
        Assert.assertEquals(Integer.parseInt(result[0].stringValue()), 3);
    }
}
