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

/**
 * Constants for Redis Connector.
 *
 * @since 0.5.0
 */
public class Constants {

    public static final String REDIS_ERROR_TYPE = "Error";
    static final int DEFAULT_REDIS_PORT = 6379;

    /**
     * Endpoint configuration constants.
     */
    public static class EndpointConfig {

        public static final String HOST = "host";
        public static final String PASSWORD = "password";
        public static final String OPTIONS = "options";
        public static final String CLUSTERING_ENABLED = "isClusterConnection";
        public static final String POOLING_ENABLED = "connectionPooling";
    }
}
