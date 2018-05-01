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

package org.ballerinalang.redis;

import org.ballerinalang.bre.Context;
import org.ballerinalang.bre.bvm.BLangVMErrors;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.util.codegen.PackageInfo;
import org.ballerinalang.util.codegen.StructInfo;

import static org.ballerinalang.bre.bvm.BLangVMErrors.PACKAGE_BUILTIN;

/**
 * This class contains utility methods for Redis package.
 */
public class RedisDataSourceUtils {
    public static BStruct getRedisConnectorError(Context context, Throwable throwable) {
        PackageInfo builtinPackage = context.getProgramFile().getPackageInfo(PACKAGE_BUILTIN);
        StructInfo errorStructInfo = builtinPackage.getStructInfo(BLangVMErrors.STRUCT_GENERIC_ERROR);
        BStruct redisConnectorError = new BStruct(errorStructInfo.getType());
        if (throwable.getMessage() == null) {
            redisConnectorError.setStringField(0, Constants.REDIS_EXCEPTION_OCCURRED);
        } else {
            redisConnectorError.setStringField(0, throwable.getMessage());
        }
        return redisConnectorError;
    }
}
