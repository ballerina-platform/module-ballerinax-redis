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
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.ballerinalang.redis.Constants.DEFAULT_REDIS_PORT;

/**
 * {@code {@link RedisDataSource}} Util class for Redis initialization.
 *
 * @param <K> Type of the Key
 * @param <V> Type of the Value
 * @since 0.5.0
 */
public class RedisDataSource<K, V> {

    private static final String HOSTS_SEPARATOR = ",";
    private static final String HOST_PORT_SEPARATOR = ":";
    private RedisClient redisClient;
    private RedisClusterClient redisClusterClient;
    private RedisCodec<K, V> codec;
    private RedisCommands<K, V> redisCommands;
    private RedisAdvancedClusterCommands<K, V> redisClusterCommands;
    private boolean isClusterConnection;
    private boolean poolingEnabled;
    private GenericObjectPool<StatefulConnection<K, V>> objectPool;

    /**
     * Constructor for {@link RedisDataSource}.
     *
     * @param codec The codec for transcoding keys/values between the application and the Redis DB.
     *              Instance of {@link RedisCodec}
     * @param isClusterConnection Whether the connection is a cluster connection
     * @param poolingEnabled Whether connection pooling is enabled
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
    public void init(String hosts, String password, BMap<BString, Object> options) {
        List<ServerAddress> serverAddresses = obtainServerAddresses(hosts);
        if (isClusterConnection) {
            setRedisClusterCommands(serverAddresses, password, options);
        } else {
            setRedisStandaloneCommands(serverAddresses, password, options);
        }
        //TODO: Add support for executing commands in async mode/ reactive mode
    }

    /**
     * Returns {@link RedisCommands}, an interface for Redis commands available on a Redis instance.
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
     * Returns {@link RedisAdvancedClusterCommands}, an interface for Redis commands available on a Redis Cluster.
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

    /**
     * Returns whether the connection made by the datasource is a cluster connection.
     *
     * @return boolean true/false
     */
    public boolean isPoolingEnabled() {
        return poolingEnabled;
    }

    public void closeConnectionPool() {
        objectPool.close();
    }

    private void setRedisStandaloneCommands(List<ServerAddress> serverAddresses, String password, BMap<BString, Object> options) {
        if (serverAddresses.size() > 1) {
            throw new RuntimeException("More than one hosts have been provided for a non-cluster connection");
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
        } else {
            Supplier<StatefulConnection<K, V>> supplier = () -> redisClient.connect(codec);
            objectPool = ConnectionPoolSupport.createGenericObjectPool(supplier, new GenericObjectPoolConfig());
        }
    }

    private void setRedisClusterCommands(List<ServerAddress> serverAddresses, String password, BMap<BString, Object> options) {
        StatefulRedisClusterConnection<K, V> statefulRedisClusterConnection;
        List<RedisURI> redisURIS;
        if (!password.isEmpty()) {
            redisURIS = serverAddresses.stream().map(serverAddress -> setOptions(
                RedisURI.Builder.redis(serverAddress.getHost(), serverAddress.getPort()), options).withPassword(password).build())
                .collect(Collectors.toList());
        } else {
            redisURIS = serverAddresses.stream().map(serverAddress -> setOptions(
                RedisURI.Builder.redis(serverAddress.getHost(), serverAddress.getPort()), options).build())
                .collect(Collectors.toList());
        }
        redisClusterClient = RedisClusterClient.create(redisURIS);
        if (!poolingEnabled) {
            statefulRedisClusterConnection = redisClusterClient.connect(codec);
            redisClusterCommands = statefulRedisClusterConnection.sync();
        } else {
            Supplier<StatefulConnection<K, V>> supplier = () -> redisClusterClient.connect(codec);
            objectPool = ConnectionPoolSupport.createGenericObjectPool(supplier, new GenericObjectPoolConfig());
        }
    }

    private RedisURI.Builder setOptions(RedisURI.Builder builder, BMap<BString, Object> options) {
        int database = options.getIntValue(StringUtils.fromString(ConnectionParam.DATABASE.getKey())).intValue();
        int connectionTimeout = options.getIntValue(StringUtils.fromString(
                ConnectionParam.CONNECTION_TIMEOUT.getKey())).intValue();
        BString clientName = options.getStringValue(StringUtils.fromString(ConnectionParam.CLIENT_NAME.getKey()));

        boolean sslEnabled = options.getBooleanValue(StringUtils.fromString(ConnectionParam.SSL_ENABLED.getKey()));
        boolean startTlsEnabled = options.getBooleanValue(StringUtils.fromString
                (ConnectionParam.START_TLS_ENABLED.getKey()));
        boolean verifyPeerEnabled = options.getBooleanValue(StringUtils.fromString(
                ConnectionParam.VERIFY_PEER_ENABLED.getKey()));

        if (database != -1) {
            builder.withDatabase(database);
        }
        if (connectionTimeout != -1) {
            builder.withTimeout(Duration.ofMillis(connectionTimeout));
        }
        if (!clientName.toString().equals("")) {
            builder.withClientName(clientName.toString());
        }
        builder.withSsl(sslEnabled);
        builder.withStartTls(startTlsEnabled);
        builder.withVerifyPeer(verifyPeerEnabled);

        return builder;
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
                throw new RuntimeException("the port of the host string must be an integer: " + hostStr, e);
            }
        } else {
            port = DEFAULT_REDIS_PORT;
        }
        return new ServerAddress(host, port);
    }

    private StatefulConnection<K, V> getStatefulRedisConnectionFromPool() {
        try {
            return objectPool.borrowObject();
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while obtaining connection from the pool: " + e);
        }
    }

    private enum ConnectionParam {
        //String params
        CLIENT_NAME("clientName"),

        //int params
        DATABASE("database"), CONNECTION_TIMEOUT("connectionTimeout"),

        //boolean params
        POOLING_ENABLED("poolingEnabled"), IS_CLUSTER_CONNECTION("isClusterConnection"), SSL_ENABLED(
                "ssl"), START_TLS_ENABLED("startTls"), VERIFY_PEER_ENABLED("verifyPeer");

        private String key;

        ConnectionParam(String key) {
            this.key = key;
        }

        private String getKey() {
            return key;
        }
    }

    public void releaseResources(Object redisCommands) {
        if (isClusterConnection) {
            objectPool.returnObject(((RedisAdvancedClusterCommands<K, V>) redisCommands).getStatefulConnection());
        } else {
            objectPool.returnObject(((RedisCommands<K, V>) redisCommands).getStatefulConnection());
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
