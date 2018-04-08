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

import io.lettuce.core.ScoredValue;
import org.ballerinalang.launcher.util.BCompileUtil;
import org.ballerinalang.launcher.util.BRunUtil;
import org.ballerinalang.launcher.util.CompileResult;
import org.ballerinalang.model.values.BFloat;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.model.values.BValue;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;

public class RedisSortedSetCommandTest extends RedisCommandsBaseTest {
    CompileResult compileResult;

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        compileResult = BCompileUtil.compile("samples/redis-sorted-set-command-test.bal");
        setUpDatabase();
    }

    private void setUpDatabase() {
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

    @Test
    public void testZAddCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZAdd");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 1);
    }

    @Test
    public void testZCardCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZCard");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 2);
    }

    @Test
    public void testZCountCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZCount");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 3);
    }

    @Test
    public void testZIncrByCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZIncrBy");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals((float) ((BFloat) result[0]).floatValue(), 7f);
    }

    @Test
    public void testZInterStoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZInterStore");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 2);
        List<String> resultList = redisCommands.zrange("testZInterStoreDestKey", 0, -1);
        String[] memberArray = { "One", "Six" };
        boolean allElementsPresentInDest = true;
        for (int i = 0; i < 2; i++) {
            allElementsPresentInDest = resultList.contains(memberArray[i]);
            if (!allElementsPresentInDest) {
                break;
            }
        }
        Assert.assertTrue(allElementsPresentInDest);
    }

    @Test
    public void testZLexCountCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZLexCount");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 5);
    }

    @Test
    public void testZRangeCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRange");
        Assert.assertEquals(result.length, 1);
        BStringArray resultingElements = (BStringArray) result[0];
        Assert.assertEquals(resultingElements.size(), 3);
        String element1 = resultingElements.get(0);
        String element2 = resultingElements.get(1);
        String element3 = resultingElements.get(2);
        String[] memberArray = { "d", "b", "e" };
        Set<String> set = createSetFromArray(memberArray);
        Assert.assertTrue(set.contains(element1) && set.contains(element2) && set.contains(element3));
    }

    @Test
    public void testZRangeByLexCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRangeByLex");
        Assert.assertEquals(result.length, 1);
        BStringArray resultingElements = (BStringArray) result[0];
        Assert.assertEquals(resultingElements.size(), 6);
        String[] memberArray = { "b", "c", "d", "e", "f", "g" };
        boolean correctOrder = true;
        for (int i = 0; i < 6; i++) {
            correctOrder = memberArray[i].equals(resultingElements.get(i));
            if (!correctOrder) {
                break;
            }
        }
        Assert.assertTrue(correctOrder);
    }

    @Test
    public void testZRangeByScoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRangeByScore");
        Assert.assertEquals(result.length, 1);
        BStringArray resultingElements = (BStringArray) result[0];
        Assert.assertEquals(resultingElements.size(), 3);
        String[] memberArray = { "b", "e", "f" };
        boolean correctOrder = true;
        for (int i = 0; i < 3; i++) {
            correctOrder = memberArray[i].equals(resultingElements.get(i));
            if (!correctOrder) {
                break;
            }
        }
        Assert.assertTrue(correctOrder);
    }

    @Test
    public void testZRankCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRank");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 0);
    }

    @Test
    public void testZRemCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRem");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 2);
    }

    @Test
    public void testZRemRangeByLexCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRemRangeByLex");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 6);
    }

    @Test
    public void testZRemRangeByRankCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRemRangeByRank");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 4);
    }

    @Test
    public void testZRemRangeByScoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRemRangeByScore");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 4);
    }

    @Test
    public void testZRevRangeCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRevRange");
        Assert.assertEquals(result.length, 1);
        BStringArray resultingElements = (BStringArray) result[0];
        Assert.assertEquals(resultingElements.size(), 3);
        String element1 = resultingElements.get(0);
        String element2 = resultingElements.get(1);
        String element3 = resultingElements.get(2);
        String[] memberArray = { "f", "e", "c" };
        Set<String> set = createSetFromArray(memberArray);
        Assert.assertTrue(set.contains(element1) && set.contains(element2) && set.contains(element3));
    }

    @Test
    public void testZRevRangeByLexCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRevRangeByLex");
        Assert.assertEquals(result.length, 1);
        BStringArray resultingElements = (BStringArray) result[0];
        Assert.assertEquals(resultingElements.size(), 6);
        String[] memberArray = { "g", "f", "e", "d", "c", "b" };
        boolean correctOrder = true;
        for (int i = 0; i < 6; i++) {
            correctOrder = memberArray[i].equals(resultingElements.get(i));
            if (!correctOrder) {
                break;
            }
        }
        Assert.assertTrue(correctOrder);
    }

    @Test
    public void testZRevRangeByScoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRevRangeByScore");
        Assert.assertEquals(result.length, 1);
        BStringArray resultingElements = (BStringArray) result[0];
        Assert.assertEquals(resultingElements.size(), 3);
        String[] memberArray = { "b", "e", "f" };
        boolean correctOrder = true;
        for (int i = 0; i < 3; i++) {
            correctOrder = memberArray[i].equals(resultingElements.get(i));
            if (!correctOrder) {
                break;
            }
        }
        Assert.assertTrue(correctOrder);
    }

    @Test
    public void testZRevRankCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZRevRank");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 0);
    }

    @Test
    public void testZScoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZScore");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals((float) ((BFloat) result[0]).floatValue(), 1.2f);

    }

    @Test
    public void testZUnionStoreCommand() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testZUnionStore");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(((BInteger) result[0]).intValue(), 6);
        List<String> resultList = redisCommands.zrange("testZUnionStoreDestKey", 0, -1);
        String[] memberArray = { "a", "b", "c", "d", "f", "g" };
        boolean allElementsPresentInDest = true;
        for (int i = 0; i < 6; i++) {
            allElementsPresentInDest = resultList.contains(memberArray[i]);
            if (!allElementsPresentInDest) {
                break;
            }
        }
        Assert.assertTrue(allElementsPresentInDest);
    }
}
