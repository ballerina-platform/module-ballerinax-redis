/*
 * Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org)
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

package io.ballerina.lib.redis.connection;

import io.ballerina.lib.redis.config.CertKey;
import io.ballerina.lib.redis.config.ConnectionConfig;
import io.ballerina.lib.redis.config.ConnectionParams;
import io.ballerina.lib.redis.config.ConnectionURI;
import io.ballerina.lib.redis.config.KeyStore;
import io.ballerina.lib.redis.config.Options;
import io.ballerina.lib.redis.config.SecureSocket;
import io.ballerina.lib.redis.config.TrustStore;
import io.ballerina.lib.redis.exceptions.RedisConnectorException;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SslOptions;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.BaseRedisCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.api.sync.RedisHashCommands;
import io.lettuce.core.api.sync.RedisKeyCommands;
import io.lettuce.core.api.sync.RedisListCommands;
import io.lettuce.core.api.sync.RedisSetCommands;
import io.lettuce.core.api.sync.RedisSortedSetCommands;
import io.lettuce.core.api.sync.RedisStringCommands;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.File;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Connection manager implementation for Redis connections.
 *
 * @param <K> Type of the Key
 * @param <V> Type of the Value
 * @since 2.6.0
 */
public class RedisConnectionManager<K, V> {

    // Lettuce connection objects
    private RedisCommands<K, V> redisCommands;
    private RedisAdvancedClusterCommands<K, V> redisClusterCommands;
    private GenericObjectPool<StatefulConnection<K, V>> objectPool;
    private final RedisCodec<K, V> codec;
    private boolean isClusterConnection;
    private boolean poolingEnabled;

    // Command executors
    private RedisConnectionCommandExecutor connectionCommandExecutor;
    private RedisStringCommandExecutor stringCommandExecutor;
    private RedisKeyCommandExecutor keyCommandExecutor;
    private RedisHashCommandExecutor hashCommandExecutor;
    private RedisSetCommandExecutor setCommandExecutor;
    private RedisListCommandsExecutor listCommandsExecutor;
    private RedisSortedSetCommandExecutor sortedSetCommandExecutor;

    public RedisConnectionManager(RedisCodec<K, V> codec) {
        this.codec = codec;
    }

    /**
     * Initializes a Redis client with the provided options.
     *
     * @param connectionConfig Redis connection configurations
     */
    public void init(ConnectionConfig connectionConfig) throws RedisConnectorException {
        this.isClusterConnection = connectionConfig.isClusterConnection();
        this.poolingEnabled = connectionConfig.poolingEnabled();
        if (isClusterConnection) {
            setRedisClusterCommands(connectionConfig);
        } else {
            setRedisStandaloneCommands(connectionConfig);
        }

        // TODO: Add support for executing commands in async mode/ reactive mode?
    }

    public RedisConnectionCommandExecutor getConnectionCommandExecutor() {
        if (connectionCommandExecutor == null) {
            connectionCommandExecutor = new RedisConnectionCommandExecutor(this);
        }
        return connectionCommandExecutor;
    }

    public RedisStringCommandExecutor getStringCommandExecutor() {
        if (stringCommandExecutor == null) {
            stringCommandExecutor = new RedisStringCommandExecutor(this);
        }
        return stringCommandExecutor;
    }

    public RedisKeyCommandExecutor getKeyCommandExecutor() {
        if (keyCommandExecutor == null) {
            keyCommandExecutor = new RedisKeyCommandExecutor(this);
        }
        return keyCommandExecutor;
    }

    public RedisHashCommandExecutor getHashCommandExecutor() {
        if (hashCommandExecutor == null) {
            hashCommandExecutor = new RedisHashCommandExecutor(this);
        }
        return hashCommandExecutor;
    }

    public RedisSetCommandExecutor getSetCommandExecutor() {
        if (setCommandExecutor == null) {
            setCommandExecutor = new RedisSetCommandExecutor(this);
        }
        return setCommandExecutor;
    }

    public RedisListCommandsExecutor getListCommandExecutor() {
        if (listCommandsExecutor == null) {
            listCommandsExecutor = new RedisListCommandsExecutor(this);
        }
        return listCommandsExecutor;
    }

    public RedisSortedSetCommandExecutor getSortedSetCommandExecutor() {
        if (sortedSetCommandExecutor == null) {
            sortedSetCommandExecutor = new RedisSortedSetCommandExecutor(this);
        }
        return sortedSetCommandExecutor;
    }

    public BaseRedisCommands<K, V> getConnectionCommandConnection() throws RedisConnectorException {
        return isClusterConnection() ? getRedisClusterCommands() : getRedisCommands();
    }

    public RedisStringCommands<K, V> getStringCommandConnection() throws RedisConnectorException {
        return isClusterConnection() ? getRedisClusterCommands() : getRedisCommands();
    }

    public RedisListCommands<K, V> getListCommandConnection() throws RedisConnectorException {
        return isClusterConnection() ? getRedisClusterCommands() : getRedisCommands();
    }

    public RedisSetCommands<K, V> getSetCommandConnection() throws RedisConnectorException {
        return isClusterConnection() ? getRedisClusterCommands() : getRedisCommands();
    }

    public RedisSortedSetCommands<K, V> getSortedSetCommandConnection() throws RedisConnectorException {
        return isClusterConnection() ? getRedisClusterCommands() : getRedisCommands();
    }

    public RedisHashCommands<K, V> getHashCommandConnection() throws RedisConnectorException {
        return isClusterConnection() ? getRedisClusterCommands() : getRedisCommands();
    }

    public RedisKeyCommands<K, V> getKeyCommandConnection() throws RedisConnectorException {
        return isClusterConnection() ? getRedisClusterCommands() : getRedisCommands();
    }

    /**
     * Returns {@link RedisCommands}, an interface for Redis commands available on a Redis instance.
     *
     * @return a {@link RedisCommands} instance
     */
    public RedisCommands<K, V> getRedisCommands() throws RedisConnectorException {
        if (poolingEnabled) {
            return ((StatefulRedisConnection<K, V>) getStatefulRedisConnectionFromPool()).sync();
        }
        return redisCommands;
    }

    /**
     * Returns {@link RedisAdvancedClusterCommands}, an interface for Redis commands available on a Redis Cluster.
     *
     * @return a {@link RedisAdvancedClusterCommands} instance
     */
    public RedisAdvancedClusterCommands<K, V> getRedisClusterCommands() throws RedisConnectorException {
        if (poolingEnabled) {
            return ((StatefulRedisClusterConnection<K, V>) getStatefulRedisConnectionFromPool()).sync();
        }
        return redisClusterCommands;
    }

    /**
     * Returns whether the connection made by the connection manager is a cluster connection.
     *
     * @return boolean true/false
     */
    public boolean isClusterConnection() {
        return isClusterConnection;
    }

    /**
     * Returns whether the connection made by the connection manager is a pooled connection.
     *
     * @return boolean true/false
     */
    public boolean isPoolingEnabled() {
        return poolingEnabled;
    }

    public void closeConnectionPool() {
        objectPool.close();
    }

    private void setRedisStandaloneCommands(ConnectionConfig connectionConfig) throws RedisConnectorException {
        RedisURI redisURI;
        RedisClient redisClient;
        if (connectionConfig instanceof ConnectionURI uriConfig) {
            redisURI = RedisURI.create(uriConfig.uri());
            redisClient = RedisClient.create(redisURI);
        } else if (connectionConfig instanceof ConnectionParams connectionParams) {
            redisURI = constructRedisUri(connectionParams);
            SecureSocket secureSocket = connectionParams.secureSocket();
            if (secureSocket != null) {
                redisURI.setSsl(true);
                redisURI.setVerifyPeer(secureSocket.verifyPeer());
                redisURI.setStartTls(secureSocket.startTls());
                redisClient = RedisClient.create(redisURI);

                SslOptions sslOptions = constructSslOptions(secureSocket);
                ClientOptions clientOptions = ClientOptions.builder().sslOptions(sslOptions).build();
                redisClient.setOptions(clientOptions);
            } else {
                redisClient = RedisClient.create(redisURI);
            }
        } else {
            throw new RedisConnectorException("Invalid connection configuration provided");
        }

        if (poolingEnabled) {
            Supplier<StatefulConnection<K, V>> supplier = () -> redisClient.connect(codec);
            objectPool = ConnectionPoolSupport.createGenericObjectPool(supplier, new GenericObjectPoolConfig<>());
        } else {
            StatefulRedisConnection<K, V> statefulRedisConnection = redisClient.connect(codec);
            redisCommands = statefulRedisConnection.sync();
        }
    }

    private void setRedisClusterCommands(ConnectionConfig connectionConfig) throws RedisConnectorException {
        RedisURI redisURI;
        RedisClusterClient redisClusterClient;
        if (connectionConfig instanceof ConnectionURI uriConfig) {
            redisURI = RedisURI.create(uriConfig.uri());
            redisClusterClient = RedisClusterClient.create(redisURI);
        } else if (connectionConfig instanceof ConnectionParams connectionParams) {
            redisURI = constructRedisUri(connectionParams);
            SecureSocket secureSocket = connectionParams.secureSocket();
            if (secureSocket != null) {
                redisURI.setSsl(true);
                redisURI.setVerifyPeer(secureSocket.verifyPeer());
                redisURI.setStartTls(secureSocket.startTls());
                redisClusterClient = RedisClusterClient.create(redisURI);

                SslOptions sslOptions = constructSslOptions(secureSocket);
                ClusterClientOptions clientOptions = ClusterClientOptions.builder().sslOptions(sslOptions).build();
                redisClusterClient.setOptions(clientOptions);
            } else {
                redisClusterClient = RedisClusterClient.create(redisURI);
            }
        } else {
            throw new RedisConnectorException("Invalid connection configuration provided");
        }

        if (poolingEnabled) {
            Supplier<StatefulConnection<K, V>> supplier = () -> redisClusterClient.connect(codec);
            objectPool = ConnectionPoolSupport.createGenericObjectPool(supplier, new GenericObjectPoolConfig<>());
        } else {
            redisClusterCommands = redisClusterClient.connect(codec).sync();
        }
    }

    private RedisURI constructRedisUri(ConnectionParams connectionParams) {
        Options options = connectionParams.options();

        RedisURI.Builder builder = RedisURI.builder()
                .withHost(connectionParams.host())
                .withPort(connectionParams.port());

        int database = options.database();
        if (database >= 0) {
            builder.withDatabase(database);
        }

        int connectionTimeout = options.connectionTimeout();
        if (connectionTimeout != -1) {
            builder.withTimeout(Duration.ofSeconds(connectionTimeout));
        }

        String clientName = options.clientName();
        if (clientName != null && !clientName.isBlank()) {
            builder.withClientName(clientName);
        }

        String username = connectionParams.username();
        String password = connectionParams.password();
        if (username != null && !username.isBlank()) {
            builder.withAuthentication(username, password);
        } else if (password != null && !password.isBlank()) {
            builder.withPassword(password);
        }

        return builder.build();
    }

    public void releaseResources(Object redisCommands) {
        if (!isPoolingEnabled() || Objects.isNull(redisCommands)) {
            return;
        }

        if (isClusterConnection) {
            objectPool.returnObject(((RedisAdvancedClusterCommands<K, V>) redisCommands).getStatefulConnection());
        } else {
            objectPool.returnObject(((RedisCommands<K, V>) redisCommands).getStatefulConnection());
        }
    }

    private StatefulConnection<K, V> getStatefulRedisConnectionFromPool() throws RedisConnectorException {
        try {
            return objectPool.borrowObject();
        } catch (Exception e) {
            throw new RedisConnectorException("Error occurred while obtaining connection from the pool: " + e);
        }
    }

    private BaseRedisCommands<K, V> getCommandConnection() throws RedisConnectorException {
        if (isClusterConnection()) {
            return getRedisClusterCommands();
        } else {
            return getRedisCommands();
        }
    }

    private SslOptions constructSslOptions(SecureSocket secureSocket) {

        SslOptions.Builder sslOptionsBuilder = SslOptions.builder();

        TrustStore trustStore = secureSocket.trustStoreCert();
        if (trustStore != null) {
            sslOptionsBuilder.truststore(new File(trustStore.trustStorePath()), trustStore.trustStorePassword());
        }

        String certPath = secureSocket.strCert();
        if (certPath != null) {
            sslOptionsBuilder.truststore(new File(certPath));
        }

        KeyStore keyStore = secureSocket.keyStore();
        if (keyStore != null) {
            sslOptionsBuilder.keystore(new File(keyStore.keyStorePath()), keyStore.keyStorePassword().toCharArray());
        }

        CertKey certKey = secureSocket.CertKey();
        if (certKey != null) {
            sslOptionsBuilder.keyManager(new File(certKey.certFile()), new File(certKey.keyFile()),
                    certKey.keyPassword() != null ? certKey.keyPassword().toCharArray() : null);
        }

        String[] protocols = secureSocket.protocols();
        if (protocols != null) {
            sslOptionsBuilder.protocols(protocols);
        }

        String[] ciphers = secureSocket.ciphers();
        if (ciphers != null) {
            sslOptionsBuilder.cipherSuites(ciphers);
        }

        return sslOptionsBuilder.build();
    }
}
