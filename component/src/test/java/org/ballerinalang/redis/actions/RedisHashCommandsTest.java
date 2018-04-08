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
import org.ballerinalang.model.values.BString;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.model.values.BValue;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisHashCommandsTest extends RedisCommandsBaseTest {
    CompileResult compileResult;

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        compileResult = BCompileUtil.compile("samples/redis-hash-command-test.bal");
        setUpDatabase();
    }

    private void setUpDatabase() {
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

    @Test
    public void testHDelCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHDel");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 3);
        boolean allFieldsDeleted = !redisCommands.hexists("testHDelKey", "testHDelField1") && !redisCommands
                .hexists("testHDelKey", "testHDelField2") && !redisCommands.hexists("testHDelKey", "testHDelField3");
        Assert.assertTrue(allFieldsDeleted);
    }

    @Test
    public void testHGetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHGet");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), "testHGetValue1");
    }

    @Test
    public void testHExistsCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHExists");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BBoolean) result[0]).booleanValue(), true);
    }

    @Test
    public void testHGetAllCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHGetAll");
        Assert.assertEquals(result.length, 1);
        BMap<String, BString> bMap = (BMap<String, BString>) result[0];
        Assert.assertEquals(bMap.size(), 2);
        boolean allFieldsRetrieved =
                bMap.get("testHGetAllField1") != null && bMap.get("testHGetAllField1").stringValue()
                        .equals("testHGetAllValue1") && bMap.get("testHGetAllField2") != null && bMap
                        .get("testHGetAllField2").stringValue().equals("testHGetAllValue2");
        Assert.assertTrue(allFieldsRetrieved);
    }

    @Test
    public void testHIncrByCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHIncrBy");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 8);
    }

    @Test
    public void testHIncrByFloatCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHIncrByFloat");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals((float) ((BFloat) result[0]).floatValue(), 7.2f);
    }

    @Test
    public void testHKeysCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHKeys");
        Assert.assertEquals(result.length, 1);
        BStringArray fields = (BStringArray) result[0];
        Assert.assertEquals(fields.size(), 3);

        List<String> fieldsList = new ArrayList<>(3);
        fieldsList.add("testHKeysField1");
        fieldsList.add("testHKeysField2");
        fieldsList.add("testHKeysField3");

        boolean allFieldsRetrieved = true;
        for (int i = 0; i < 3; i++) {
            if (!fieldsList.contains(fields.get(i))) {
                allFieldsRetrieved = false;
                break;
            }
        }
        Assert.assertTrue(allFieldsRetrieved);
    }

    @Test
    public void testHLenCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHLen");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 3);
    }

    @Test
    public void testHMGetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHMGet");
        Assert.assertEquals(result.length, 1);
        BMap<String, BString> bMap = (BMap<String, BString>) result[0];
        Assert.assertEquals(bMap.size(), 3);

        List<String> fieldsList = new ArrayList<>(3);
        fieldsList.add("testHMGetValue1");
        fieldsList.add("testHMGetValue2");
        fieldsList.add("testHMGetValue3");

        boolean allFieldsRetrieved = bMap.get("testHMGetField1") != null && bMap.get("testHMGetField1").stringValue()
                .equals("testHMGetValue1") && bMap.get("testHMGetField2") != null && bMap.get("testHMGetField2")
                .stringValue().equals("testHMGetValue2") && bMap.get("testHMGetField3") != null && bMap
                .get("testHMGetField3").stringValue().equals("testHMGetValue3");
        Assert.assertTrue(allFieldsRetrieved);
    }

    @Test
    public void testHMSetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHMSet");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), OK_RESPONSE);
        boolean allFieldsSet = redisCommands.hexists("testHMSetKey", "testHMSetField1") && redisCommands
                .hget("testHMSetKey", "testHMSetField1").equals("testHMSetValue1") && redisCommands
                .hexists("testHMSetKey", "testHMSetField2") && redisCommands.hget("testHMSetKey", "testHMSetField2")
                .equals("testHMSetValue2");
        Assert.assertTrue(allFieldsSet);
    }

    @Test
    public void testHSetCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHSet");
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue(((BBoolean) result[0]).booleanValue());
        Assert.assertTrue(redisCommands.hexists("testHSetKey", "testHSetField1") && redisCommands
                .hget("testHSetKey", "testHSetField1").equals("testHSetValue1"));
    }

    @Test
    public void testHSetNxCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHSetNx");
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue(((BBoolean) result[0]).booleanValue());
        Assert.assertTrue(redisCommands.hexists("testHSetNxKey", "testHSetNxField1") && redisCommands
                .hget("testHSetNxKey", "testHSetNxField1").equals("testHSetNxValue1"));
    }

    @Test
    public void testHStrlnCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHStrln");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 16);
    }

    @Test
    public void testHValsCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testHVals");
        Assert.assertEquals(result.length, 1);
        BStringArray values = (BStringArray) result[0];
        Assert.assertEquals(values.size(), 3);

        List<String> valueList = new ArrayList<>(3);
        valueList.add("testHValsValue1");
        valueList.add("testHValsValue2");
        valueList.add("testHValsValue3");

        boolean allValuesRetrieved = true;
        for (int i = 0; i < 3; i++) {
            if (!valueList.contains(values.get(i))) {
                allValuesRetrieved = false;
                break;
            }
        }
        Assert.assertTrue(allValuesRetrieved);
    }
}
