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
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.model.values.BValue;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Set;

public class RedisSetCommandTest extends RedisCommandsBaseTest {
    CompileResult compileResult;

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        compileResult = BCompileUtil.compile("samples/redis-set-command-test.bal");
        setUpDatabase();
    }

    private void setUpDatabase() {
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

    @Test
    public void testSAddCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSAdd");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(Integer.parseInt(result[0].stringValue()), 3);
        Assert.assertTrue(redisCommands.sismember("testSAddKey", "testSAddValue3"));
        Assert.assertTrue(redisCommands.sismember("testSAddKey", "testSAddValue4"));
        Assert.assertTrue(redisCommands.sismember("testSAddKey", "testSAddValue5"));
    }

    @Test
    public void testSDiffCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSDiff");
        Assert.assertEquals(result.length, 1);
        BStringArray resultingElements = (BStringArray) result[0];
        Assert.assertEquals(resultingElements.size(), 2);
        String element1 = resultingElements.get(0);
        String element2 = resultingElements.get(1);
        Assert.assertTrue("Three".equals(element1) && "Four".equals(element2) || "Four".equals(element2) && "Three"
                .equals(element1));
    }

    @Test
    public void testSDiffStoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSDiffStore");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 2);
        redisCommands.sismember("testSDiffStoreDestKey", "Three");
        redisCommands.sismember("testSDiffStoreDestKey", "Four");
    }

    @Test
    public void testSInterCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSInter");
        Assert.assertEquals(result.length, 1);
        BStringArray resultingElements = (BStringArray) result[0];
        Assert.assertEquals(resultingElements.size(), 2);
        String element1 = resultingElements.get(0);
        String element2 = resultingElements.get(1);
        Assert.assertTrue(
                "Two".equals(element1) && "One".equals(element2) || "Two".equals(element2) && "One".equals(element1));
    }

    @Test
    public void testSInterStoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSInterStore");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 2);
        redisCommands.sismember("testSInterStoreDestKey", "Three");
        redisCommands.sismember("testSInterStoreDestKey", "Four");
    }

    @Test
    public void testSIsMemberCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSIsMember");
        Assert.assertEquals(result.length, 1);
        Assert.assertTrue((((BBoolean) result[0]).booleanValue()));
    }

    @Test
    public void testSMembersCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSMembers");
        Assert.assertEquals(result.length, 1);
        BStringArray members = (BStringArray) result[0];
        Assert.assertEquals(members.size(), 3);

        String[] memberArray = { "testSMembersValue1", "testSMembersValue2", "testSMembersValue3" };
        Set<String> set = createSetFromArray(memberArray);

        Assert.assertTrue(set.contains(members.get(0)) && set.contains(members.get(1)) && set.contains(members.get(2)));
    }

    @Test
    public void testSPopCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSPop");
        Assert.assertEquals(result.length, 1);
        BStringArray members = (BStringArray) result[0];
        Assert.assertEquals(members.size(), 2);

        String[] memberArray = { "testSPopValue1", "testSPopValue2", "testSPopValue3" };
        Set<String> set = createSetFromArray(memberArray);

        Assert.assertTrue(set.contains(members.get(0)) && set.contains(members.get(1)));
        Assert.assertFalse(redisCommands.sismember("testSPopKey", members.get(0)));
        Assert.assertFalse(redisCommands.sismember("testSPopKey", members.get(1)));
    }

    @Test
    public void testSPopCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSPopNilOutput");
        assertNil(result);
    }

    @Test
    public void testRandMemberCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSRandMember");
        Assert.assertEquals(result.length, 1);
        BStringArray members = (BStringArray) result[0];
        Assert.assertEquals(members.size(), 2);

        String[] memberArray = { "testSRandMemberValue1", "testSRandMemberValue2", "testSRandMemberValue3" };
        Set<String> set = createSetFromArray(memberArray);
        Assert.assertTrue(set.contains(members.get(0)) && set.contains(members.get(1)));
    }

    @Test
    public void testRandMemberCommandNilOutput() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSRandMemberNilOutput");
        assertNil(result);
    }

    @Test
    public void testSRemCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSRem");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 2);
        Assert.assertFalse(redisCommands.sismember("testSRemKey", "testSRemValue1"));
        Assert.assertFalse(redisCommands.sismember("testSRemKey", "testSRemValue3"));
    }

    @Test
    public void testSUnionCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSUnion");
        Assert.assertEquals(result.length, 1);
        BStringArray members = (BStringArray) result[0];
        Assert.assertEquals(members.size(), 4);

        String[] memberArray = { "testUnionValue1", "testUnionValue2", "testUnionValue3", "testUnionValue4" };
        Set<String> set = createSetFromArray(memberArray);
        Assert.assertTrue(
                set.contains(members.get(0)) && set.contains(members.get(1)) && set.contains(members.get(2)) && set
                        .contains(members.get(3)));
    }

    @Test
    public void testSUnionStoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testSUnionStore");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 4);
        Assert.assertTrue(redisCommands.sismember("testSUnionStoreDestKey", "testUnionValue1"));
        Assert.assertTrue(redisCommands.sismember("testSUnionStoreDestKey", "testUnionValue2"));
        Assert.assertTrue(redisCommands.sismember("testSUnionStoreDestKey", "testUnionValue3"));
        Assert.assertTrue(redisCommands.sismember("testSUnionStoreDestKey", "testUnionValue4"));
    }
}
