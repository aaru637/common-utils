/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dk;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;

/**
 * Utility class providing common helper methods for JSON serialization and deserialization
 * using the Gson library. It supports custom date formats and pretty printing.
 *
 * <p>Note: This class is not intended to be instantiated.</p>
 * <p>All methods are static and can be called directly on the class.</p>
 *
 * <p>This class is part of the org.dk package, which provides utility methods for various JSON operations.</p>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @see org.dk.JsonUtils
 * @since 1.0
 * <p>Created at : 2025-08-23</p>
 */
public class JsonUtils {

    /**
     * Private constructor to prevent instantiation.
     */
    private JsonUtils() {
        // Private constructor to prevent instantiation
    }

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    private static final GsonBuilder gson = new GsonBuilder();

    /**
     * Serializes an object to a JSON string using the default date format.
     *
     * @param object the object to serialize
     * @return the JSON string or null if the object is null
     */
    public static String toJson(Object object) {
        if (CommonUtils.isNull(object)) {
            return null;
        }
        return gson.setDateFormat(DEFAULT_DATE_FORMAT).create().toJson(object);
    }

    /**
     * Serializes an object to a JSON string using a custom date format.
     *
     * @param object     the object to serialize
     * @param dateFormat the date format to use
     * @return the JSON string or null if the object is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static String toJson(Object object, String dateFormat) {
        if (CommonUtils.isNull(object)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        return gson.setDateFormat(dateFormat).create().toJson(object);
    }

    /**
     * Serializes an object to a pretty-printed JSON string using the default date format.
     *
     * @param object the object to serialize
     * @return the pretty-printed JSON string or null if the object is null
     */
    public static String toJsonPretty(Object object) {
        if (CommonUtils.isNull(object)) {
            return null;
        }
        return gson.setDateFormat(DEFAULT_DATE_FORMAT).setPrettyPrinting().create().toJson(object);
    }

    /**
     * Serializes an object to a pretty-printed JSON string using a custom date format.
     *
     * @param object     the object to serialize
     * @param dateFormat the date format to use
     * @return the pretty-printed JSON string or null if the object is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static String toJsonPretty(Object object, String dateFormat) {
        if (CommonUtils.isNull(object)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        return gson.setDateFormat(dateFormat).setPrettyPrinting().create().toJson(object);
    }

    /**
     * Deserializes a JSON string to an object of the specified class using the default date format.
     *
     * @param json  the JSON string
     * @param clazz the class of the object
     * @param <T>   the type of the object
     * @return the deserialized object or null if the JSON is null
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        return gson.setDateFormat(DEFAULT_DATE_FORMAT).create().fromJson(json, clazz);
    }

    /**
     * Deserializes a JSON string to an object of the specified class using the default date format,
     * with optional pretty printing.
     *
     * @param json   the JSON string
     * @param clazz  the class of the object
     * @param pretty whether to enable pretty printing
     * @param <T>    the type of the object
     * @return the deserialized object or null if the JSON is null
     */
    public static <T> T fromJson(String json, Class<T> clazz, boolean pretty) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        GsonBuilder builder = gson.setDateFormat(DEFAULT_DATE_FORMAT);
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create().fromJson(json, clazz);
    }

    /**
     * Deserializes a JSON string to an object of the specified class using a custom date format.
     *
     * @param json       the JSON string
     * @param clazz      the class of the object
     * @param dateFormat the date format to use
     * @param <T>        the type of the object
     * @return the deserialized object or null if the JSON is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static <T> T fromJson(String json, Class<T> clazz, String dateFormat) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        return gson.setDateFormat(dateFormat).create().fromJson(json, clazz);
    }

    /**
     * Deserializes a JSON string to an object of the specified class using a custom date format,
     * with optional pretty printing.
     *
     * @param json       the JSON string
     * @param clazz      the class of the object
     * @param dateFormat the date format to use
     * @param pretty     whether to enable pretty printing
     * @param <T>        the type of the object
     * @return the deserialized object or null if the JSON is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static <T> T fromJson(String json, Class<T> clazz, String dateFormat, boolean pretty) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        GsonBuilder builder = gson.setDateFormat(dateFormat);
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create().fromJson(json, clazz);
    }

    /**
     * Deserializes a JSON string to a list of objects of the specified class using the default date format.
     *
     * @param json  the JSON string
     * @param clazz the class of the list elements
     * @param <T>   the type of the list elements
     * @return the deserialized list or null if the JSON is null
     */
    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.setDateFormat(DEFAULT_DATE_FORMAT).create().fromJson(json, type);
    }

    /**
     * Deserializes a JSON string to a list of objects of the specified class using a custom date format.
     *
     * @param json       the JSON string
     * @param clazz      the class of the list elements
     * @param dateFormat the date format to use
     * @param <T>        the type of the list elements
     * @return the deserialized list or null if the JSON is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static <T> List<T> fromJsonList(String json, Class<T> clazz, String dateFormat) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.setDateFormat(dateFormat).create().fromJson(json, type);
    }

    /**
     * Deserializes a JSON string to a list of objects of the specified class using the default date format,
     * with optional pretty printing.
     *
     * @param json   the JSON string
     * @param clazz  the class of the list elements
     * @param pretty whether to enable pretty printing
     * @param <T>    the type of the list elements
     * @return the deserialized list or null if the JSON is null
     */
    public static <T> List<T> fromJsonList(String json, Class<T> clazz, boolean pretty) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        GsonBuilder builder = gson.setDateFormat(DEFAULT_DATE_FORMAT);
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create().fromJson(json, type);
    }

    /**
     * Deserializes a JSON string to a list of objects of the specified class using a custom date format,
     * with optional pretty printing.
     *
     * @param json       the JSON string
     * @param clazz      the class of the list elements
     * @param dateFormat the date format to use
     * @param pretty     whether to enable pretty printing
     * @param <T>        the type of the list elements
     * @return the deserialized list or null if the JSON is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static <T> List<T> fromJsonList(String json, Class<T> clazz, String dateFormat, boolean pretty) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        GsonBuilder builder = gson.setDateFormat(dateFormat);
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create().fromJson(json, type);
    }

    /**
     * Deserializes a JSON string to a map with keys and values of the specified class using the default date format.
     *
     * @param json       the JSON string
     * @param keyClass   the class of the map keys
     * @param valueClass the class of the map values
     * @param <T>        the type of the map keys and values
     * @return the deserialized map or null if the JSON is null
     */
    public static <T> Map<T, T> fromJsonMap(String json, Class<T> keyClass, Class<T> valueClass) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        Type type = TypeToken.getParameterized(Map.class, keyClass, valueClass).getType();
        return gson.setDateFormat(DEFAULT_DATE_FORMAT).create().fromJson(json, type);
    }

    /**
     * Deserializes a JSON string to a map with keys and values of the specified class using a custom date format.
     *
     * @param json       the JSON string
     * @param keyClass   the class of the map keys
     * @param valueClass the class of the map values
     * @param dateFormat the date format to use
     * @param <T>        the type of the map keys and values
     * @return the deserialized map or null if the JSON is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static <T> Map<T, T> fromJsonMap(String json, Class<T> keyClass, Class<T> valueClass, String dateFormat) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        Type type = TypeToken.getParameterized(Map.class, keyClass, valueClass).getType();
        return gson.setDateFormat(dateFormat).create().fromJson(json, type);
    }

    /**
     * Deserializes a JSON string to a map with keys and values of the specified class using the default date format,
     * with optional pretty printing.
     *
     * @param json       the JSON string
     * @param keyClass   the class of the map keys
     * @param valueClass the class of the map values
     * @param pretty     whether to enable pretty printing
     * @param <T>        the type of the map keys and values
     * @return the deserialized map or null if the JSON is null
     */
    public static <T> Map<T, T> fromJsonMap(String json, Class<T> keyClass, Class<T> valueClass, boolean pretty) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        Type type = TypeToken.getParameterized(Map.class, keyClass, valueClass).getType();
        GsonBuilder builder = gson.setDateFormat(DEFAULT_DATE_FORMAT);
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create().fromJson(json, type);
    }

    /**
     * Deserializes a JSON string to a map with keys and values of the specified class using a custom date format,
     * with optional pretty printing.
     *
     * @param json       the JSON string
     * @param keyClass   the class of the map keys
     * @param valueClass the class of the map values
     * @param dateFormat the date format to use
     * @param pretty     whether to enable pretty printing
     * @param <T>        the type of the map keys and values
     * @return the deserialized map or null if the JSON is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static <T> Map<T, T> fromJsonMap(String json, Class<T> keyClass, Class<T> valueClass, String dateFormat, boolean pretty) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        Type type = TypeToken.getParameterized(Map.class, keyClass, valueClass).getType();
        GsonBuilder builder = gson.setDateFormat(dateFormat);
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create().fromJson(json, type);
    }

    /**
     * Deserializes a JSON string to a String using the default date format.
     *
     * @param json the JSON string
     * @return the deserialized String or null if the JSON is null
     */
    public static String fromJsonToString(String json) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        return gson.setDateFormat(DEFAULT_DATE_FORMAT).create().fromJson(json, String.class);
    }

    /**
     * Deserializes a JSON string to a String using a custom date format.
     *
     * @param json       the JSON string
     * @param dateFormat the date format to use
     * @return the deserialized String or null if the JSON is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static String fromJsonToString(String json, String dateFormat) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        return gson.setDateFormat(dateFormat).create().fromJson(json, String.class);
    }

    /**
     * Deserializes a JSON string to a String using the default date format,
     * with optional pretty printing.
     *
     * @param json   the JSON string
     * @param pretty whether to enable pretty printing
     * @return the deserialized String or null if the JSON is null
     */
    public static String fromJsonToString(String json, boolean pretty) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        GsonBuilder builder = gson.setDateFormat(DEFAULT_DATE_FORMAT);
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create().fromJson(json, String.class);
    }

    /**
     * Deserializes a JSON string to a String using a custom date format,
     * with optional pretty printing.
     *
     * @param json       the JSON string
     * @param dateFormat the date format to use
     * @param pretty     whether to enable pretty printing
     * @return the deserialized String or null if the JSON is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static String fromJsonToString(String json, String dateFormat, boolean pretty) {
        if (CommonUtils.isNull(json)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        GsonBuilder builder = gson.setDateFormat(dateFormat);
        if (pretty) {
            builder.setPrettyPrinting();
        }
        return builder.create().fromJson(json, String.class);
    }

    /**
     * Serializes a list to a JSON string using the default date format.
     *
     * @param list the list to serialize
     * @return the JSON string or null if the list is null
     */
    public static String fromListToJson(List<?> list) {
        if (CommonUtils.isNull(list)) {
            return null;
        }
        return gson.setDateFormat(DEFAULT_DATE_FORMAT).create().toJson(list);
    }

    /**
     * Serializes a list to a JSON string using a custom date format.
     *
     * @param list       the list to serialize
     * @param dateFormat the date format to use
     * @return the JSON string or null if the list is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static String fromListToJson(List<?> list, String dateFormat) {
        if (CommonUtils.isNull(list)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        return gson.setDateFormat(dateFormat).create().toJson(list);
    }

    /**
     * Serializes a list to a pretty-printed JSON string using the default date format.
     *
     * @param list the list to serialize
     * @return the pretty-printed JSON string or null if the list is null
     */
    public static String fromListToJsonPretty(List<?> list) {
        if (CommonUtils.isNull(list)) {
            return null;
        }
        return gson.setDateFormat(DEFAULT_DATE_FORMAT).setPrettyPrinting().create().toJson(list);
    }

    /**
     * Serializes a list to a pretty-printed JSON string using a custom date format.
     *
     * @param list       the list to serialize
     * @param dateFormat the date format to use
     * @return the pretty-printed JSON string or null if the list is null
     * @throws IllegalArgumentException if the date format is invalid
     */
    public static String fromListToJsonPretty(List<?> list, String dateFormat) {
        if (CommonUtils.isNull(list)) {
            return null;
        }
        if (CommonUtils.isNull(dateFormat)) {
            dateFormat = DateTimeUtils.DEFAULT_DATE_FORMAT;
        }
        if (!DateTimeUtils.isValidDateFormat(dateFormat)) {
            throw new IllegalArgumentException("Invalid date format: " + dateFormat);
        }
        return gson.setDateFormat(dateFormat).setPrettyPrinting().create().toJson(list);
    }
}
