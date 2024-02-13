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

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;

/**
 * This class maps the Ballerina Redis client config to the Java Redis client config.
 *
 * @since 3.0.0
 */
public final class ConfigMapper {

    // Redis client config names as Ballerina string objects
    public static final BString CONFIG_CONNECTION = StringUtils.fromString("connection");
    public static final BString CONFIG_URI = StringUtils.fromString("uri");
    public static final BString CONFIG_HOST = StringUtils.fromString("host");
    public static final BString CONFIG_PORT = StringUtils.fromString("port");
    public static final BString CONFIG_PASSWORD = StringUtils.fromString("password");
    public static final BString CONFIG_OPTIONS = StringUtils.fromString("options");
    public static final BString IS_CLUSTER_CONNECTION = StringUtils.fromString("isClusterConnection");
    public static final BString CONFIG_POOLING_ENABLED = StringUtils.fromString("connectionPooling");
    public static final BString CONFIG_CLIENT_NAME = StringUtils.fromString("clientName");
    public static final BString CONFIG_DATABASE = StringUtils.fromString("database");
    public static final BString CONFIG_CONNECTION_TIMEOUT = StringUtils.fromString("connectionTimeout");
    public static final BString CONFIG_SSL_ENABLED = StringUtils.fromString("ssl");
    public static final BString CONFIG_START_TLS_ENABLED = StringUtils.fromString("startTls");
    public static final BString CONFIG_VERIFY_PEER_ENABLED = StringUtils.fromString("verifyPeer");

    private ConfigMapper() {
    }

    public static ConnectionConfig from(BMap<BString, Object> config) {
        boolean isClusterConnection = config.getBooleanValue(IS_CLUSTER_CONNECTION);
        boolean poolingEnabled = config.getBooleanValue(CONFIG_POOLING_ENABLED);

        BMap<BString, Object> connection = (BMap<BString, Object>) config.getMapValue(CONFIG_CONNECTION);
        if (isConnectionStringConfig(connection)) {
            return new ConnectionString(connection.getStringValue(CONFIG_URI).getValue(), isClusterConnection,
                    poolingEnabled);
        } else {
            String host = connection.getStringValue(CONFIG_HOST).getValue();
            int port = connection.getIntValue(CONFIG_PORT).intValue();
            BMap<BString, Object> options = (BMap<BString, Object>) connection.getMapValue(CONFIG_OPTIONS);
            return new ConnectionParams(host, port, isClusterConnection, poolingEnabled,
                    getConnectionOptionsFromBObject(options));
        }
    }

    private static Options getConnectionOptionsFromBObject(BMap<BString, Object> connection) {
        boolean sslEnabled = connection.getBooleanValue(CONFIG_SSL_ENABLED);
        boolean startTls = connection.getBooleanValue(CONFIG_START_TLS_ENABLED);
        boolean verifyPeer = connection.getBooleanValue(CONFIG_VERIFY_PEER_ENABLED);
        int database = connection.getIntValue(CONFIG_DATABASE).intValue();
        int connectionTimeout = connection.getIntValue(CONFIG_CONNECTION_TIMEOUT).intValue();

        BString passwordBStr = connection.getStringValue(CONFIG_PASSWORD);
        String password = passwordBStr != null ? passwordBStr.getValue() : "";

        BString clientNameBStr = connection.getStringValue(CONFIG_CLIENT_NAME);
        String clientName = clientNameBStr != null ? clientNameBStr.getValue() : "";

        return new Options(password, sslEnabled, startTls, verifyPeer, clientName, database, connectionTimeout);
    }

    private static boolean isConnectionStringConfig(BMap<BString, Object> connection) {
        return connection.containsKey(StringUtils.fromString("uri"));
    }
}
