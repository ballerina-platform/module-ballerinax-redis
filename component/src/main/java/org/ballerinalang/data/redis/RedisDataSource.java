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

package org.ballerinalang.data.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulConnection;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.ballerinalang.model.types.BType;
import org.ballerinalang.model.values.BStruct;
import org.ballerinalang.model.values.BValue;
import org.ballerinalang.util.exceptions.BallerinaException;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.ballerinalang.data.redis.Constants.DEFAULT_REDIS_PORT;

/**
 * {@code {@link RedisDataSource} Util class for Redis initialization.
 *
 * @param <K> Type of the Key
 * @param <V> Type of the Value
 * @since 0.5.0
 */
public class RedisDataSource<K, V> implements BValue {

    private static final String HOSTS_SEPARATOR = ",";
    private static final String HOST_PORT_SEPARATOR = ":";
    private RedisClient redisClient;
    private RedisClusterClient redisClusterClient;
    private RedisCodec<K, V> codec;
    private RedisCommands<K, V> redisCommands;
    private RedisAdvancedClusterCommands<K, V> redisClusterCommands;
    private boolean isClusterConnection = false;
    private boolean poolingEnabled = false;

    /**
     * Constructor for {@link RedisDataSource}
     *
     * @param codec The codec for transcoding keys/values between the application and the Redis DB.
     *              Instance of {@link RedisCodec}
     * @param isClusterConnection Whether the connection is a cluster connection
     * @param poolingEnabled
     */
    public RedisDataSource(RedisCodec<K, V> codec, boolean isClusterConnection, boolean poolingEnabled) {
        this.codec = codec;
        this.isClusterConnection = isClusterConnection;
        this.poolingEnabled = poolingEnabled;
    }

    /**
     * This method initializes a Redis client with the provided options.
     *
     * @param hosts    The host(s) of the Redis instance/cluster
     * @param password The password required for authentication
     * @param options  The additional options
     */
    public void init(String hosts, String password, BStruct options) {
        List<ServerAddress> serverAddresses = obtainServerAddresses(hosts);
        if (isClusterConnection) {
            setRedisClusterCommands(serverAddresses, options);
        } else {
            setRedisStandaloneCommands(serverAddresses, password, options);
        }
        //TODO: Add support for executing commands in async mode/ reactive mode
    }

    /**
     * Returns {@link RedisCommands}, an interface for Redis commands available on a Redis instance
     *
     * @return a {@link RedisCommands} instance
     */
    public RedisCommands<K, V> getRedisCommands() {
        if (poolingEnabled) {

            StatefulRedisConnection<K, V> statefulRedisConnection = (StatefulRedisConnection<K, V>)
                    getStatefulRedisConnectionFromPool();
            return statefulRedisConnection.sync();
        }
        return redisCommands;
    }

    /**
     * Returns {@link RedisAdvancedClusterCommands}, an interface for Redis commands available on a Redis Cluster
     *
     * @return a {@link RedisAdvancedClusterCommands} instance
     */
    public RedisAdvancedClusterCommands<K, V> getRedisClusterCommands() {
        if (poolingEnabled) {
            StatefulRedisClusterConnection<K, V> statefulRedisClusterConnection =
                    (StatefulRedisClusterConnection<K, V>) getStatefulRedisConnectionFromPool();
            return statefulRedisClusterConnection.sync();
        }
        return redisClusterCommands;
    }

    /**
     * Returns whether the connection made by the datasource is a cluster connection.
     *
     * @return boolean true/false
     */
    public boolean isClusterConnection() {
        return isClusterConnection;
    }

    private void setRedisStandaloneCommands(List<ServerAddress> serverAddresses, String password, BStruct options) {
        if (serverAddresses.size() > 1) {
            throw new BallerinaException("More than one hosts have been provided for a non-cluster connection");
        }
        RedisURI redisUri;
        StatefulRedisConnection<K, V> statefulRedisConnection;
        RedisURI.Builder redisURIBuilder = RedisURI.Builder
                .redis(serverAddresses.get(0).getHost(), serverAddresses.get(0).getPort());
        redisURIBuilder = setOptions(redisURIBuilder, options);
        if (!password.isEmpty()) {
            redisUri = redisURIBuilder.withPassword(password).build();
        } else {
            redisUri = redisURIBuilder.build();
        }
        redisClient = RedisClient.create(redisUri);

        if (!poolingEnabled) {
            statefulRedisConnection = redisClient.connect(codec);
            redisCommands = statefulRedisConnection.sync();
        }
    }

    private RedisURI.Builder setOptions(RedisURI.Builder builder, BStruct options) {
        int database = (int) options.getIntField(ConnectionParam.DATABASE.getIndex());
        int connectionTimeout = (int) options.getIntField(ConnectionParam.CONNECTION_TIMEOUT.getIndex());
        String clientName = options.getStringField(ConnectionParam.CLIENT_NAME.getIndex());

        boolean sslEnabled = options.getBooleanField(ConnectionParam.SSL_ENABLED.getIndex()) != 0;
        boolean startTlsEnabled = options.getBooleanField(ConnectionParam.START_TLS_ENABLED.getIndex()) != 0;
        boolean verifyPeerEnabled = options.getBooleanField(ConnectionParam.VERIFY_PEER_ENABLED.getIndex()) != 0;

        if (database != -1) {
            builder.withDatabase(database);
        }
        if (connectionTimeout != -1) {
            builder.withTimeout(Duration.ofMillis(connectionTimeout));
        }
        if (!clientName.isEmpty()) {
            builder.withClientName(clientName);
        }
        builder.withSsl(sslEnabled);
        builder.withStartTls(startTlsEnabled);
        builder.withVerifyPeer(verifyPeerEnabled);

        return builder;
    }

    private void setRedisClusterCommands(List<ServerAddress> serverAddresses, BStruct options) {
        StatefulRedisClusterConnection<K, V> statefulRedisClusterConnection;
        List<RedisURI> redisURIS = serverAddresses.stream().map(serverAddress -> setOptions(
                RedisURI.Builder.redis(serverAddress.getHost(), serverAddress.getPort()), options).build())
                .collect(Collectors.toList());
        //TODO: Clarify password usage with Redis Clusters and implement cluster authentication.
        redisClusterClient = RedisClusterClient.create(redisURIS);
        if (!poolingEnabled) {
            statefulRedisClusterConnection = redisClusterClient.connect(codec);
            redisClusterCommands = statefulRedisClusterConnection.sync();
        }
    }

    private List<ServerAddress> obtainServerAddresses(String hostStr) {
        String[] hosts = hostStr.split(HOSTS_SEPARATOR);
        List<ServerAddress> result = new ArrayList<>(hosts.length);
        for (String host : hosts) {
            result.add(createServerAddress(host));
        }
        return result;
    }

    private ServerAddress createServerAddress(String hostStr) {
        String[] hostPort = hostStr.split(HOST_PORT_SEPARATOR);
        String host = hostPort[0];
        int port;
        if (hostPort.length > 1) {
            try {
                port = Integer.parseInt(hostPort[1]);
            } catch (NumberFormatException e) {
                throw new BallerinaException("the port of the host string must be an integer: " + hostStr, e);
            }
        } else {
            port = DEFAULT_REDIS_PORT;
        }
        return new ServerAddress(host, port);
    }

    private StatefulConnection<K, V> getStatefulRedisConnectionFromPool() {
        Supplier<StatefulConnection<K, V>> supplier;
        if (isClusterConnection) {
            supplier = () -> redisClusterClient.connect(codec);
        } else {
            supplier = () -> redisClient.connect(codec);
        }
        return obtainStatefulRedisConnection(supplier);
    }

    private <T extends StatefulConnection<K, V>> T obtainStatefulRedisConnection(Supplier<T> supplier) {
        try {
            return ConnectionPoolSupport.createGenericObjectPool(supplier, new GenericObjectPoolConfig())
                    .borrowObject();
        } catch (Exception e) {
            throw new BallerinaException("Error occurred while obtaining connection from the pool");
        }
    }

    @Override
    public String stringValue() {
        return null;
    }

    @Override
    public BType getType() {
        return null;
    }

    @Override
    public BValue copy() {
        return null;
    }

    private enum ConnectionParam {
        //String params
        CLIENT_NAME(0),

        //int params
        DATABASE(0), CONNECTION_TIMEOUT(1),

        //boolean params
        POOLING_ENABLED(0), IS_CLUSTER_CONNECTION(1), SSL_ENABLED(2), START_TLS_ENABLED(3), VERIFY_PEER_ENABLED(4);

        private int index;

        ConnectionParam(int index) {
            this.index = index;
        }

        private int getIndex() {
            return index;
        }
    }

    private static class ServerAddress {
        private String host;
        private int port;

        ServerAddress(String host, int port) {
            this.host = host;
            this.port = port;
        }

        String getHost() {
            return host;
        }

        int getPort() {
            return port;
        }
    }
}
