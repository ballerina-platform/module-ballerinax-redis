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
import org.ballerinalang.connector.api.BLangConnectorSPIUtil;
import org.ballerinalang.model.types.BTypes;
import org.ballerinalang.model.values.BError;
import org.ballerinalang.model.values.BMap;
import org.ballerinalang.model.values.BValue;

/**
 * This class contains utility methods for Redis package.
 */
public class RedisDataSourceUtils {
    public static BError getRedisConnectorError(Context context, Throwable throwable) {
        String detailedErrorMessage =
                throwable.getMessage() != null ? throwable.getMessage() : Constants.REDIS_EXCEPTION_OCCURRED;
        BMap<String, BValue> sqlClientErrorDetailRecord = BLangConnectorSPIUtil
                .createBStruct(context, Constants.REDIS_PACKAGE_PATH, Constants.DATABASE_ERROR_DATA_RECORD_NAME,
                        detailedErrorMessage);
        return BLangVMErrors.createError(context, true, BTypes.typeError, Constants.DATABASE_ERROR_CODE,
                sqlClientErrorDetailRecord);
    }
}
