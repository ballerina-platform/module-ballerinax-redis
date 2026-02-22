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
import io.lettuce.core.SslVerifyMode;

import static io.ballerina.lib.redis.utils.ConversionUtils.getMapValueOrNull;
import static io.ballerina.lib.redis.utils.ConversionUtils.getStringValueOrNull;

/**
 * This class maps the Ballerina Redis client config to the Java Redis client config.
 *
 * @since 3.0.0
 */
public final class ConfigMapper {

    // Redis client config names as Ballerina string objects
    private static final BString CONFIG_CONNECTION = StringUtils.fromString("connection");
    private static final BString CONFIG_HOST = StringUtils.fromString("host");
    private static final BString CONFIG_PORT = StringUtils.fromString("port");
    private static final BString CONFIG_USERNAME = StringUtils.fromString("username");
    private static final BString CONFIG_PASSWORD = StringUtils.fromString("password");
    private static final BString CONFIG_IS_CLUSTER_CONNECTION = StringUtils.fromString("isClusterConnection");
    private static final BString CONFIG_POOLING_ENABLED = StringUtils.fromString("connectionPooling");

    private static final BString CONFIG_OPTIONS = StringUtils.fromString("options");
    private static final BString CONFIG_CLIENT_NAME = StringUtils.fromString("clientName");
    private static final BString CONFIG_DATABASE = StringUtils.fromString("database");
    private static final BString CONFIG_CONNECTION_TIMEOUT = StringUtils.fromString("connectionTimeout");
    private static final BString CONFIG_KEEP_ALIVE_INTERVAL = StringUtils.fromString("keepAliveIntervalInSeconds");

    private static final BString CONFIG_SECURE_SOCKET = StringUtils.fromString("secureSocket");
    private static final BString CONFIG_CERT = StringUtils.fromString("cert");
    private static final BString CONFIG_KEY = StringUtils.fromString("key");
    private static final BString CONFIG_TRUST_STORE_PATH = StringUtils.fromString("path");
    private static final BString CONFIG_TRUST_STORE_PASSWORD = StringUtils.fromString("password");
    private static final BString CONFIG_KEY_STORE_PATH = StringUtils.fromString("path");
    private static final BString CONFIG_KEY_STORE_PASSWORD = StringUtils.fromString("password");
    private static final BString CONFIG_CERT_FILE = StringUtils.fromString("certFile");
    private static final BString CONFIG_KEY_FILE = StringUtils.fromString("keyFile");
    private static final BString CONFIG_KEY_PASSWORD = StringUtils.fromString("keyPassword");
    private static final BString CONFIG_PROTOCOLS = StringUtils.fromString("protocols");
    private static final BString CONFIG_CIPHERS = StringUtils.fromString("ciphers");
    private static final BString VERIFY_MODE = StringUtils.fromString("verifyMode");
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
        SecureSocket secureSocket = getSecureSocketFromBObject(config);

        Object connection = config.get(CONFIG_CONNECTION);
        if (connection instanceof BString connectionUri) {
            return new ConnectionURI(connectionUri.getValue(), isClusterConnection, poolingEnabled, secureSocket);
        } else if (connection instanceof BMap<?, ?> connectionParamsMap) {
            BMap<BString, Object> connectionParams = (BMap<BString, Object>) connectionParamsMap;
            String host = getStringValueOrNull(connectionParams, CONFIG_HOST);
            int port = connectionParams.getIntValue(CONFIG_PORT).intValue();
            String username = getStringValueOrNull(connectionParams, CONFIG_USERNAME);
            String password = getStringValueOrNull(connectionParams, CONFIG_PASSWORD);
            BMap<BString, Object> options = getMapValueOrNull(connectionParams, CONFIG_OPTIONS);

            return new ConnectionParams(host, port, username, password, isClusterConnection, poolingEnabled,
                    secureSocket, getConnectionOptionsFromBObject(options));
        } else {
            throw new IllegalArgumentException("Unsupported connection configuration type found");
        }
    }

    private static Options getConnectionOptionsFromBObject(BMap<BString, Object> connection) {
        int database = connection.getIntValue(CONFIG_DATABASE).intValue();
        int connectionTimeout = connection.getIntValue(CONFIG_CONNECTION_TIMEOUT).intValue();
        int keepAliveIntervalInSeconds = connection.getIntValue(CONFIG_KEEP_ALIVE_INTERVAL).intValue();
        String clientName = getStringValueOrNull(connection, CONFIG_CLIENT_NAME);

        return new Options(clientName, database, connectionTimeout, keepAliveIntervalInSeconds);
    }

    private static SecureSocket getSecureSocketFromBObject(BMap<BString, Object> connection) {
        BMap<BString, Object> secureSocket = getMapValueOrNull(connection, CONFIG_SECURE_SOCKET);
        if (secureSocket == null) {
            return null;
        }

        String certPath = getStringValueOrNull(secureSocket, CONFIG_CERT);
        BMap<BString, Object> trustStoreMap = getMapValueOrNull(secureSocket, CONFIG_CERT);
        TrustStore trustStore = trustStoreMap != null ? getTrustStoreFromBObject(trustStoreMap) : null;

        KeyStore keyStore = null;
        CertKey certKey = null;
        BMap<BString, Object> keyMap = getMapValueOrNull(secureSocket, CONFIG_KEY);
        if (isKeyStoreConfig(keyMap)) {
            keyStore = getKeyStoreFromBObject(keyMap);
        } else if (isCertKeyConfig(keyMap)) {
            certKey = getCertKeyFromBObject(keyMap);
        }

        BArray protocolsBArr = secureSocket.getArrayValue(CONFIG_PROTOCOLS);
        String[] protocols = protocolsBArr != null ? ConversionUtils.createStringArrayFromBArray(protocolsBArr) : null;

        BArray ciphersBArr = secureSocket.getArrayValue(CONFIG_CIPHERS);
        String[] ciphers = ciphersBArr != null ? ConversionUtils.createStringArrayFromBArray(ciphersBArr) : null;

        String verifyModeStr = getStringValueOrNull(secureSocket, VERIFY_MODE);
        SslVerifyMode verifyMode = verifyModeStr != null ? SslVerifyMode.valueOf(verifyModeStr) : null;
        Boolean startTLS = secureSocket.getBooleanValue(CONFIG_START_TLS_ENABLED);
        startTLS = startTLS != null ? startTLS : false;

        return new SecureSocket(trustStore, certPath, keyStore, certKey, protocols, ciphers, verifyMode, startTLS);
    }

    private static CertKey getCertKeyFromBObject(BMap<BString, Object> keyMap) {
        String certFile = getStringValueOrNull(keyMap, CONFIG_CERT_FILE);
        String keyFile = getStringValueOrNull(keyMap, CONFIG_KEY_FILE);
        String keyPassword = getStringValueOrNull(keyMap, CONFIG_KEY_PASSWORD);

        return new CertKey(certFile, keyFile, keyPassword);
    }

    private static boolean isCertKeyConfig(BMap<BString, Object> keyMap) {
        return keyMap != null && keyMap.containsKey(CONFIG_CERT_FILE);
    }

    private static boolean isKeyStoreConfig(BMap<BString, Object> keyMap) {
        return keyMap != null && keyMap.containsKey(CONFIG_KEY_STORE_PATH);
    }

    private static TrustStore getTrustStoreFromBObject(BMap<BString, Object> trustStoreObj) {
        if (trustStoreObj == null) {
            return null;
        }
        String path = getStringValueOrNull(trustStoreObj, CONFIG_TRUST_STORE_PATH);
        String password = getStringValueOrNull(trustStoreObj, CONFIG_TRUST_STORE_PASSWORD);

        return new TrustStore(path, password);
    }

    private static KeyStore getKeyStoreFromBObject(BMap<BString, Object> keyStoreObj) {
        if (keyStoreObj == null) {
            return null;
        }
        String path = getStringValueOrNull(keyStoreObj, CONFIG_KEY_STORE_PATH);
        String password = getStringValueOrNull(keyStoreObj, CONFIG_KEY_STORE_PASSWORD);

        return new KeyStore(path, password);
    }
}
