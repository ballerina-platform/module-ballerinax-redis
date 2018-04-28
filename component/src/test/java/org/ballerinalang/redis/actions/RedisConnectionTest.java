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
import org.ballerinalang.model.values.BValue;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RedisConnectionTest extends RedisCommandsBaseTest {
    CompileResult compileResult;

    @BeforeClass(alwaysRun = true)
    public void setup() throws Exception {
        compileResult = BCompileUtil.compile("samples/redis-connection-test.bal");
    }

    @Test
    public void testInitWithConnectionParams() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testInitWithConnectionParam");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue().toUpperCase(), PONG);
    }

    @Test
    public void testEcho() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testEcho");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue(), "Manuri");
    }

    @Test
    public void testPing() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testPing");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue().toUpperCase(), PONG);
    }

    @Test
    public void testConnectionRelease() throws Exception {
        BValue[] result = BRunUtil.invoke(compileResult, "testConnectionRelease");
        Assert.assertEquals(result.length, 1);
        Assert.assertEquals(result[0].stringValue().toUpperCase(), PONG);
    }
}
