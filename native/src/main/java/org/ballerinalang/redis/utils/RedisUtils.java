package org.ballerinalang.redis.utils;

import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import io.lettuce.core.codec.Utf8StringCodec;
import org.ballerinalang.redis.Codec;
import org.ballerinalang.redis.Constants;
import org.ballerinalang.redis.RedisDataSource;

/**
 * Redis utility methods.
 *
 * @since 3.0.0
 */
public class RedisUtils {

    /**
     * Initialize the redis client.
     *
     * @param config configuration map
     */
    public static void initClient(BObject client, BMap<?, ?> config) {
        BString host = config.getStringValue(StringUtils.fromString(Constants.EndpointConfig.HOST));
        BString password = config.getStringValue(StringUtils.fromString(Constants.EndpointConfig.PASSWORD));
        BString strOptions = StringUtils.fromString(Constants.EndpointConfig.OPTIONS);
        BMap<BString, Object> options = (BMap<BString, Object>) config.getMapValue(strOptions);

        RedisCodec<?, ?> codec = retrieveRedisCodec(Codec.STRING_CODEC.getCodecName());
        boolean clusteringEnabled = options.getBooleanValue(StringUtils.fromString(
                Constants.EndpointConfig.CLUSTERING_ENABLED));
        boolean poolingEnabled = options.getBooleanValue(StringUtils.fromString(
                Constants.EndpointConfig.POOLING_ENABLED));

        RedisDataSource<?, ?> dataSource = new RedisDataSource<>(codec, clusteringEnabled, poolingEnabled);
        dataSource.init(host.toString(), password.toString(), options);
        client.addNativeData("DATA_SOURCE", dataSource);
    }

    /**
     * Retrieve redis codec.
     *
     * @param codecString codec string
     * @return redis codec
     */
    public static RedisCodec<?, ?> retrieveRedisCodec(String codecString) {
        Codec codec = retrieveCodec(codecString);
        return switch (codec) {
            case BYTE_ARRAY_CODEC -> new ByteArrayCodec();
            case STRING_CODEC -> new StringCodec();
            case UTF8_STRING_CODEC -> new Utf8StringCodec();
        };
    }

    /**
     * Retreive a codec name.
     *
     * @param codecString codec string
     * @return codec name
     */
    private static Codec retrieveCodec(String codecString) {
        try {
            return Codec.fromCodecName(codecString);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unsupported Codec: " + codecString);
        }
    }
}
