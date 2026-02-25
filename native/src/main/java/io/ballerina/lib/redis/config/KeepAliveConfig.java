/*
 * Copyright (c) 2026 WSO2 LLC. (http://www.wso2.org)
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
 * Represents TCP keep-alive configuration for detecting stale connections.
 *
 * @param idle     time in seconds the connection must be idle before the first keep-alive probe
 * @param interval time in seconds between individual keep-alive probes
 * @param count    maximum number of keep-alive probes before the connection is considered dead
 * @since 3.0.0
 */
public record KeepAliveConfig(int idle, int interval, int count) {

}
