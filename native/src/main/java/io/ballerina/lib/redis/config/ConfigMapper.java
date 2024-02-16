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

import io.ballerina.lib.redis.utils.ConversionUtils;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;

import static io.ballerina.lib.redis.utils.Constants.EMPTY_STRING;
import static io.ballerina.lib.redis.utils.ConversionUtils.getStringValueOrDefault;

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
    public static final BString CONFIG_USERNAME = StringUtils.fromString("username");
    public static final BString CONFIG_PASSWORD = StringUtils.fromString("password");
    public static final BString CONFIG_IS_CLUSTER_CONNECTION = StringUtils.fromString("isClusterConnection");
    public static final BString CONFIG_POOLING_ENABLED = StringUtils.fromString("connectionPooling");

    public static final BString CONFIG_OPTIONS = StringUtils.fromString("options");
    public static final BString CONFIG_CLIENT_NAME = StringUtils.fromString("clientName");
    public static final BString CONFIG_DATABASE = StringUtils.fromString("database");
    public static final BString CONFIG_CONNECTION_TIMEOUT = StringUtils.fromString("connectionTimeout");

    public static final BString CONFIG_SECURE_SOCKET = StringUtils.fromString("secureSocket");
    public static final BString CONFIG_CERT = StringUtils.fromString("cert");
    public static final BString CONFIG_KEY = StringUtils.fromString("key");
    public static final BString CONFIG_TRUST_STORE_PATH = StringUtils.fromString("path");
    public static final BString CONFIG_TRUST_STORE_PASSWORD = StringUtils.fromString("password");
    public static final BString CONFIG_KEY_STORE_PATH = StringUtils.fromString("path");
    public static final BString CONFIG_KEY_STORE_PASSWORD = StringUtils.fromString("password");
    public static final BString CONFIG_CERT_FILE = StringUtils.fromString("certFile");
    public static final BString CONFIG_KEY_FILE = StringUtils.fromString("keyFile");
    public static final BString CONFIG_KEY_PASSWORD = StringUtils.fromString("keyPassword");
    private static final BString CONFIG_PROTOCOLS = StringUtils.fromString("protocols");
    private static final BString CONFIG_CIPHERS = StringUtils.fromString("ciphers");
    private static final BString CONFIG_VERIFY_PEER_ENABLED = StringUtils.fromString("verifyPeer");
    private static final BString CONFIG_START_TLS_ENABLED = StringUtils.fromString("startTls");

    private ConfigMapper() {
    }

    /**
     * Maps the Ballerina Redis client config to the Java Redis client config object.
     *
     * @param config Ballerina Redis client config
     * @return Java Redis client config
     */
    public static ConnectionConfig from(BMap<BString, Object> config) {
        boolean isClusterConnection = config.getBooleanValue(CONFIG_IS_CLUSTER_CONNECTION);
        boolean poolingEnabled = config.getBooleanValue(CONFIG_POOLING_ENABLED);

        BMap<BString, Object> connection = (BMap<BString, Object>) config.getMapValue(CONFIG_CONNECTION);
        if (isConnectionStringConfig(connection)) {
            return new ConnectionString(connection.getStringValue(CONFIG_URI).getValue(), isClusterConnection,
                    poolingEnabled);
        } else {
            String host = connection.getStringValue(CONFIG_HOST).getValue();
            int port = connection.getIntValue(CONFIG_PORT).intValue();
            String userName = getStringValueOrDefault(connection.getStringValue(CONFIG_USERNAME), EMPTY_STRING);
            String password = getStringValueOrDefault(connection.getStringValue(CONFIG_PASSWORD), EMPTY_STRING);
            BMap<BString, Object> options = (BMap<BString, Object>) connection.getMapValue(CONFIG_OPTIONS);
            return new ConnectionParams(host, port, userName, password, isClusterConnection, poolingEnabled,
                    getConnectionOptionsFromBObject(options));
        }
    }

    private static Options getConnectionOptionsFromBObject(BMap<BString, Object> connection) {
        int database = connection.getIntValue(CONFIG_DATABASE).intValue();
        int connectionTimeout = connection.getIntValue(CONFIG_CONNECTION_TIMEOUT).intValue();
        String clientName = getStringValueOrDefault(connection.getStringValue(CONFIG_CLIENT_NAME), EMPTY_STRING);

        return new Options(clientName, database, connectionTimeout, getSecureSocketFromBObject(connection));
    }

    private static SecureSocket getSecureSocketFromBObject(BMap<BString, Object> connection) {
        BMap<BString, Object> secureSocket = (BMap<BString, Object>) connection.getMapValue(CONFIG_SECURE_SOCKET);
        if (secureSocket == null) {
            return null;
        }

        String certPath = getStringValueOrDefault(secureSocket.getStringValue(CONFIG_CERT), null);
        BMap<BString, Object> trustStoreMap = (BMap<BString, Object>) secureSocket.getMapValue(CONFIG_CERT);
        TrustStore trustStore = trustStoreMap != null ? getTrustStoreFromBObject(trustStoreMap) : null;

        KeyStore keyStore = null;
        CertKey certKey = null;
        BMap<BString, Object> keyMap = (BMap<BString, Object>) secureSocket.getMapValue(CONFIG_KEY);
        if (isKeyStoreConfig(keyMap)) {
            keyStore = getKeyStoreFromBObject(keyMap);
        } else if (isCertKeyConfig(keyMap)) {
            certKey = getCertKeyFromBObject(keyMap);
        }

        BArray protocolsBArr = secureSocket.getArrayValue(CONFIG_PROTOCOLS);
        String[] protocols = protocolsBArr != null ? ConversionUtils.createStringArrayFromBArray(protocolsBArr) : null;

        BArray ciphersBArr = secureSocket.getArrayValue(CONFIG_CIPHERS);
        String[] ciphers = ciphersBArr != null ? ConversionUtils.createStringArrayFromBArray(ciphersBArr) : null;

        Boolean verifyPeer = secureSocket.getBooleanValue(CONFIG_VERIFY_PEER_ENABLED);
        verifyPeer = verifyPeer != null ? verifyPeer : false;
        Boolean startTLS = secureSocket.getBooleanValue(CONFIG_START_TLS_ENABLED);
        startTLS = startTLS != null ? startTLS : false;

        return new SecureSocket(trustStore, certPath, keyStore, certKey, protocols, ciphers, verifyPeer, startTLS);
    }

    private static CertKey getCertKeyFromBObject(BMap<BString, Object> keyMap) {
        BString certFile = keyMap.getStringValue(CONFIG_CERT_FILE);
        BString keyFile = keyMap.getStringValue(CONFIG_KEY_FILE);
        BString keyPassword = keyMap.getStringValue(CONFIG_KEY_PASSWORD);
        return new CertKey(certFile.getValue(), keyFile.getValue(), keyPassword.getValue());
    }

    private static boolean isCertKeyConfig(BMap<BString, Object> keyMap) {
        return keyMap != null && keyMap.containsKey(CONFIG_CERT_FILE);
    }

    private static boolean isKeyStoreConfig(BMap<BString, Object> keyMap) {
        return keyMap != null && keyMap.containsKey(CONFIG_KEY_STORE_PATH);
    }

    private static TrustStore getTrustStoreFromBObject(BMap<BString, Object> trustStoreObj) {
        BString path = trustStoreObj.getStringValue(CONFIG_TRUST_STORE_PATH);
        BString password = trustStoreObj.getStringValue(CONFIG_TRUST_STORE_PASSWORD);
        return new TrustStore(path.getValue(), password.getValue());
    }

    private static KeyStore getKeyStoreFromBObject(BMap<BString, Object> keyStoreObj) {
        if (keyStoreObj == null) {
            return null;
        }
        BString path = keyStoreObj.getStringValue(CONFIG_KEY_STORE_PATH);
        BString password = keyStoreObj.getStringValue(CONFIG_KEY_STORE_PASSWORD);
        return new KeyStore(path.getValue(), password.getValue());
    }

    private static boolean isConnectionStringConfig(BMap<BString, Object> connection) {
        return connection.containsKey(StringUtils.fromString("uri"));
    }
}
