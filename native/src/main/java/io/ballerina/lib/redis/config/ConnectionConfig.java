/*
 * Copyright (c) 2024 WSO2 LLC. (http://www.wso2.org)
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.lib.redis.config;

/**
 * Contract for the connection configurations.
 *
 * @since 3.0.0
 */
public interface ConnectionConfig {

    /**
     * Check whether the connection is a cluster connection.
     *
     * @return true if the connection is a cluster connection, false otherwise
     */
    boolean isClusterConnection();

    /**
     * Check whether pooling is enabled.
     *
     * @return true if pooling is enabled, false otherwise
     */
    boolean poolingEnabled();
}
