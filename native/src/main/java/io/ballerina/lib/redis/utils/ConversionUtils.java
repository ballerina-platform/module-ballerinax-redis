/*
 * Copyright (c) 2023 WSO2 LLC. (http://www.wso2.org) All Rights Reserved.
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

package io.ballerina.lib.redis.utils;

import io.ballerina.runtime.api.PredefinedTypes;
import io.ballerina.runtime.api.creators.ErrorCreator;
import io.ballerina.runtime.api.creators.TypeCreator;
import io.ballerina.runtime.api.creators.ValueCreator;
import io.ballerina.runtime.api.utils.StringUtils;
import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BError;
import io.ballerina.runtime.api.values.BMap;
import io.ballerina.runtime.api.values.BString;
import io.lettuce.core.KeyValue;
import io.lettuce.core.ScoredValue;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Utility methods for conversion between Ballerina and Java types.
 *
 * @since 2.6.0
 */
public class ConversionUtils {

    /**
     * Create a Ballerina array value from a Java set.
     *
     * @param set The Java set
     * @return The Ballerina array
     */
    public static BArray createBStringArrayFromSet(Set<String> set) {
        BArray bStringArray = ValueCreator.createArrayValue(TypeCreator.createArrayType(PredefinedTypes.TYPE_STRING));
        for (String item : set) {
            bStringArray.append(StringUtils.fromString(item));
        }
        return bStringArray;
    }

    /**
     * Create a Ballerina array value from a Java list.
     *
     * @param list the Java list
     * @return the Ballerina array
     */
    public static BArray createBStringArrayFromList(List<String> list) {
        BArray bStringArray = ValueCreator.createArrayValue(TypeCreator.createArrayType(PredefinedTypes.TYPE_STRING));
        for (String item : list) {
            bStringArray.append(StringUtils.fromString(item));
        }
        return bStringArray;
    }

    /**
     * Create a Ballerina array value from a Java array.
     *
     * @param array the Java array
     * @return the Ballerina array
     */
    public static BArray createBStringArrayFromJArray(String[] array) {
        BArray bStringArray = ValueCreator.createArrayValue(TypeCreator.createArrayType(PredefinedTypes.TYPE_STRING));
        for (String item : array) {
            bStringArray.append(StringUtils.fromString(item));
        }
        return bStringArray;
    }

    /**
     * Create a Ballerina array value from a redis scored value map value.
     *
     * @param valueScoreMap the redis scored value map
     * @return The Ballerina array
     */
    public static <V> ScoredValue<V>[] createArrayFromScoredValueMap(Map<V, Double> valueScoreMap) {
        ScoredValue<V>[] scoredValues = new ScoredValue[valueScoreMap.size()];
        int i = 0;
        for (Map.Entry<V, Double> entry : valueScoreMap.entrySet()) {
            scoredValues[i] = ScoredValue.just(entry.getValue(), entry.getKey());
            i++;
        }
        return scoredValues;
    }

    /**
     * Create a Ballerina map value from a java map.
     *
     * @param map the java map
     * @return the Ballerina map value
     */
    public static <K> BMap<BString, Object> createBMapFromMap(Map<K, String> map) {
        BMap<BString, Object> bMap = ValueCreator.createMapValue();
        map.forEach((key, value) -> bMap.put(StringUtils.fromString((String) key), StringUtils.fromString(value)));
        return bMap;
    }

    /**
     * Create a Ballerina map value from a key value list.
     *
     * @param list the key value list
     * @return the Ballerina map value
     */
    public static <K> BMap<BString, Object> createBMapFromKeyValueList(List<KeyValue<K, String>> list) {
        BMap<BString, Object> bMap = ValueCreator.createMapValue();
        for (KeyValue<K, String> item : list) {
            String value;
            try {
                value = item.getValue();
            } catch (NoSuchElementException e) {
                value = null;
            }
            bMap.put(StringUtils.fromString((String) item.getKey()), StringUtils.fromString(value));
        }
        return bMap;
    }

    /**
     * Create a Java array from a Ballerina array value.
     *
     * @param bStringArray the Ballerina array value
     * @return the Java array
     */
    public static String[] createStringArrayFromBArray(BArray bStringArray) {
        return bStringArray.getStringArray();
    }

    /**
     * Create a java map from a Ballerina map value.
     *
     * @param bMap the Ballerina map value
     * @return the Java map
     */
    public static Map<String, Object> createMapFromBMap(BMap<BString, Object> bMap) {
        Map<String, Object> map = new LinkedHashMap<>();
        for (Map.Entry<BString, Object> entry : bMap.entrySet()) {
            map.put(entry.getKey().getValue(), entry.getValue().toString());
        }
        return map;
    }

    /**
     * Create a Ballerina array value from a Ballerina map value.
     *
     * @param bMap the Ballerina map value
     * @return the Ballerina array
     */
    public static BArray createBStringArrayFromBMap(BMap<BString, Object> bMap) {
        BArray bStringArray = ValueCreator.createArrayValue(TypeCreator.createArrayType(PredefinedTypes.TYPE_STRING));
        for (Map.Entry<BString, Object> entry : bMap.entrySet()) {
            bStringArray.append(entry.getValue());
        }
        return bStringArray;
    }

    /**
     * Create a BError instance from a throwable.
     *
     * @param e the throwable
     * @return the BError instance
     */
    public static BError createBError(Throwable e) {
        return ErrorCreator.createError(ModuleUtils.getModule(), Constants.REDIS_ERROR_TYPE,
                StringUtils.fromString(e.getMessage()),
                e.getCause() != null ? ErrorCreator.createError(e.getCause()) : null, null);
    }

    /**
     * Returns the map value from a Ballerina map for a given key or, null if a non-map value is found.
     *
     * @param map the Ballerina map value
     * @param key the key
     * @return the map value or null
     */
    public static BMap<BString, Object> getMapValueOrNull(BMap<BString, Object> map, BString key) {
        if (map == null) {
            return null;
        }

        try {
            return (BMap<BString, Object>) map.getMapValue(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns the string value from a Ballerina map for a given key or, null if a non-string value is found.
     *
     * @param map the Ballerina map value
     * @param key the key
     * @return the string value or null
     */
    public static String getStringValueOrNull(BMap<BString, Object> map, BString key) {
        if (map == null) {
            return null;
        }

        try {
            return map.getStringValue(key).getValue();
        } catch (Exception e) {
            return null;
        }
    }
}
