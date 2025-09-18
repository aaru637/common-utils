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

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Utility class providing common helper methods for null checks, string operations,
 * collection/map checks, random generation, and mathematical calculations.
 *
 * <p>This class is not intended to be instantiated. All methods are {@code static}
 * and should be called directly.</p>
 *
 * <p>This class is part of the {@code org.dk} package.</p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>
 * CommonUtils.isNull("test");            // returns false
 * CommonUtils.isEmpty("test");           // returns false
 * CommonUtils.randomString(5);           // returns a random string of length 5
 * CommonUtils.capitalize("hello.world"); // returns "Hello.World"
 * </pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @since 1.0
 * <p>Created at : 2025-08-23</p>
 */
public class CommonUtils {

    private CommonUtils() {
        // Prevent instantiation
    }

    private static final String FULL_STOP = "\\.";

    /**
     * An empty string constant.
     */
    public static final String EMPTY_STRING = "";
    private static final int DEFAULT_RANDOM_STRING_LENGTH = 10;
    private static final int DEFAULT_RANDOM_INT_MIN = 0;
    private static final int DEFAULT_RANDOM_INT_MAX = 10000;

    /**
     * Checks if the given string is {@code null}.
     *
     * @param str the string to check
     * @return {@code true} if string is {@code null}, {@code false} otherwise
     */
    public static boolean isNull(String str) {
        return str == null;
    }

    /**
     * Checks if the given object is {@code null}.
     *
     * @param data the object to check
     * @param <T>  the type of the object
     * @return {@code true} if object is {@code null}, {@code false} otherwise
     */
    public static <T> boolean isNull(T data) {
        return data == null;
    }

    /**
     * Checks if the given string is not {@code null}.
     *
     * @param str the string to check
     * @return {@code true} if string is not {@code null}, {@code false} otherwise
     */
    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    /**
     * Checks if the given object is not {@code null}.
     *
     * @param data the object to check
     * @param <T>  the type of the object
     * @return {@code true} if object is not {@code null}, {@code false} otherwise
     */
    public static <T> boolean isNotNull(T data) {
        return !isNull(data);
    }

    /**
     * Checks if the given string is {@code null} or empty.
     *
     * @param str the string to check
     * @return {@code true} if string is {@code null} or empty, {@code false} otherwise
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || str.isEmpty();
    }

    /**
     * Checks if the given collection is {@code null} or empty.
     *
     * @param data the collection to check
     * @param <T>  the type of the collection
     * @return {@code true} if collection is {@code null} or empty, {@code false} otherwise
     */
    public static <T extends Collection<?>> boolean isEmpty(T data) {
        return isNull(data) || data.isEmpty();
    }

    /**
     * Checks if the given map is {@code null} or empty.
     *
     * @param data the map to check
     * @param <T>  the type of the map
     * @return {@code true} if map is {@code null} or empty, {@code false} otherwise
     */
    public static <T extends Map<?, ?>> boolean isEmpty(T data) {
        return isNull(data) || data.isEmpty();
    }

    /**
     * Checks if the given {@code CharSequence} is {@code null} or empty.
     *
     * @param data the CharSequence to check
     * @param <T>  the type of the CharSequence
     * @return {@code true} if CharSequence is {@code null} or empty, {@code false} otherwise
     */
    public static <T extends CharSequence> boolean isEmpty(T data) {
        return isNull(data) || data.isEmpty();
    }

    /**
     * Checks if the given array is {@code null} or has zero length.
     *
     * @param data the array to check
     * @return {@code true} if array is {@code null} or empty, {@code false} otherwise
     */
    public static boolean isEmpty(Object[] data) {
        return isNull(data) || data.length == 0;
    }

    /**
     * Checks if the given string is not {@code null} and not empty.
     *
     * @param str the string to check
     * @return {@code true} if string is not {@code null} and not empty, {@code false} otherwise
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * Checks if the given collection is not {@code null} and not empty.
     *
     * @param data the collection to check
     * @param <T>  the type of the collection
     * @return {@code true} if collection is not {@code null} and not empty, {@code false} otherwise
     */
    public static <T extends Collection<?>> boolean isNotEmpty(T data) {
        return !isEmpty(data);
    }

    /**
     * Checks if the given map is not {@code null} and not empty.
     *
     * @param data the map to check
     * @param <T>  the type of the map
     * @return {@code true} if map is not {@code null} and not empty, {@code false} otherwise
     */
    public static <T extends Map<?, ?>> boolean isNotEmpty(T data) {
        return !isEmpty(data);
    }

    /**
     * Checks if the given {@code CharSequence} is not {@code null} and not empty.
     *
     * @param data the CharSequence to check
     * @param <T>  the type of the CharSequence
     * @return {@code true} if CharSequence is not {@code null} and not empty, {@code false} otherwise
     */
    public static <T extends CharSequence> boolean isNotEmpty(T data) {
        return !isEmpty(data);
    }

    /**
     * Checks if the given array is not {@code null} and not empty.
     *
     * @param data the array to check
     * @return {@code true} if array is not {@code null} and not empty, {@code false} otherwise
     */
    public static boolean isNotEmpty(Object[] data) {
        return !isEmpty(data);
    }

    /**
     * Returns {@code null} if the given string is {@code null} or empty, otherwise returns the string itself.
     *
     * @param str the string to check
     * @return {@code null} if {@code str} is {@code null} or empty, otherwise {@code str}
     */
    public static String isEmptyReturnNull(String str) {
        return isEmpty(str) ? null : str.trim();
    }

    /**
     * Returns the default value if the given string is {@code null} or empty, otherwise returns the string itself.
     *
     * @param str          the string to check
     * @param defaultValue the value to return if {@code str} is {@code null} or empty
     * @return {@code defaultValue} if {@code str} is {@code null} or empty, otherwise {@code str}
     */
    public static String isEmpty(String str, String defaultValue) {
        return isEmpty(str) ? defaultValue : str;
    }

    /**
     * Compares two strings for equality after trimming whitespace.
     * Both strings must be non-{@code null}.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if both strings are non-{@code null} and equal after trimming, {@code false} otherwise
     */
    public static boolean isStringsEqual(String s1, String s2) {
        if (isNull(s1) || isNull(s2)) {
            return false;
        }
        return s1.trim().equals(s2.trim());
    }

    /**
     * Checks if two strings are not equal after trimming whitespace.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if the strings are not equal after trimming, {@code false} otherwise
     */
    public static boolean isStringsNotEqual(String s1, String s2) {
        return !isStringsEqual(s1, s2);
    }

    /**
     * Compares two strings for equality, ignoring case and trimming whitespace.
     * Both strings must be non-{@code null}.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if both strings are non-{@code null} and equal ignoring case after trimming, {@code false} otherwise
     */
    public static boolean isStringsEqualWithIgnoreCase(String s1, String s2) {
        if (isNull(s1) || isNull(s2)) {
            return false;
        }
        return s1.trim().equalsIgnoreCase(s2.trim());
    }

    /**
     * Checks if two strings are not equal, ignoring case and trimming whitespace.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return {@code true} if the strings are not equal ignoring case after trimming, {@code false} otherwise
     */
    public static boolean isStringsNotEqualWithIgnoreCase(String s1, String s2) {
        return !isStringsEqualWithIgnoreCase(s1, s2);
    }

    /**
     * Checks if the source string contains the search string.
     * Both must be non-{@code null}.
     *
     * @param source the string to search in
     * @param search the string to search for
     * @return {@code true} if {@code source} contains {@code search}, {@code false} otherwise
     */
    public static boolean contains(String source, String search) {
        if (isNull(source) || isNull(search)) {
            return false;
        }
        return source.contains(search);
    }

    /**
     * Checks if the given collection contains the specified element.
     *
     * @param data    the collection to check
     * @param element the element to search for
     * @param <T>     the collection type
     * @return {@code true} if the collection contains the element, {@code false} otherwise
     */
    public static <T extends Collection<?>> boolean contains(T data, Object element) {
        if (isNull(data) || isNull(element)) {
            return false;
        }
        return data.contains(element);
    }

    /**
     * Checks if the given map contains the specified key.
     *
     * @param data the map to check
     * @param key  the key to search for
     * @param <T>  the map type
     * @return {@code true} if the map contains the key, {@code false} otherwise
     */
    public static <T extends Map<?, ?>> boolean containsKey(T data, Object key) {
        if (isNull(data) || isNull(key)) {
            return false;
        }
        return data.containsKey(key);
    }

    /**
     * Checks if the given map contains the specified value.
     *
     * @param data  the map to check
     * @param value the value to search for
     * @param <T>   the map type
     * @return {@code true} if the map contains the value, {@code false} otherwise
     */
    public static <T extends Map<?, ?>> boolean containsValue(T data, Object value) {
        if (isNull(data) || isNull(value)) {
            return false;
        }
        return data.containsValue(value);
    }

    /**
     * Checks if the given array contains the specified element.
     *
     * @param data    the array to check
     * @param element the element to search for
     * @return {@code true} if the array contains the element, {@code false} otherwise
     */
    public static boolean contains(Object[] data, Object element) {
        if (isNull(data) || isNull(element)) {
            return false;
        }
        for (Object item : data) {
            if (item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given list contains the specified element.
     *
     * @param data    the list to check
     * @param element the element to search for
     * @return {@code true} if the list contains the element, {@code false} otherwise
     */
    public static boolean contains(List<Object> data, Object element) {
        if (isNull(data) || isNull(element)) {
            return false;
        }
        for (Object item : data) {
            if (item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given list of strings contains the specified string, ignoring case.
     *
     * @param data    the list of strings to check
     * @param element the string to search for
     * @return {@code true} if the list contains the string ignoring case, {@code false} otherwise
     */
    public static boolean containsIgnoreCase(List<String> data, String element) {
        if (isNull(data) || isNull(element)) {
            return false;
        }
        for (String item : data) {
            if (item.equalsIgnoreCase(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the source string does not contain the search string.
     *
     * @param source the string to search in
     * @param search the string to search for
     * @return {@code true} if {@code source} does not contain {@code search}, {@code false} otherwise
     */
    public static boolean isNotContains(String source, String search) {
        return !contains(source, search);
    }

    /**
     * Checks if the given collection does not contain the specified element.
     *
     * @param data    the collection to check
     * @param element the element to search for
     * @param <T>     the collection type
     * @return {@code true} if the collection does not contain the element, {@code false} otherwise
     */
    public static <T extends Collection<?>> boolean isNotContains(T data, Object element) {
        return !contains(data, element);
    }

    /**
     * Checks if the given map does not contain the specified key.
     *
     * @param data the map to check
     * @param key  the key to search for
     * @param <T>  the map type
     * @return {@code true} if the map does not contain the key, {@code false} otherwise
     */
    public static <T extends Map<?, ?>> boolean isNotContainsKey(T data, Object key) {
        return !containsKey(data, key);
    }

    /**
     * Checks if the given map does not contain the specified value.
     *
     * @param data  the map to check
     * @param value the value to search for
     * @param <T>   the map type
     * @return {@code true} if the map does not contain the value, {@code false} otherwise
     */
    public static <T extends Map<?, ?>> boolean isNotContainsValue(T data, Object value) {
        return !containsValue(data, value);
    }

    /**
     * Checks if the given array does not contain the specified element.
     *
     * @param data    the array to check
     * @param element the element to search for
     * @return {@code true} if the array does not contain the element, {@code false} otherwise
     */
    public static boolean isNotContains(Object[] data, Object element) {
        return !contains(data, element);
    }

    /**
     * Checks if the source string contains the search string, ignoring case.
     *
     * @param source the string to search in
     * @param search the string to search for
     * @return {@code true} if {@code source} contains {@code search} ignoring case, {@code false} otherwise
     */
    public static boolean containsIgnoreCase(String source, String search) {
        if (isNull(source) || isNull(search)) {
            return false;
        }
        return source.toLowerCase().contains(search.toLowerCase());
    }

    /**
     * Checks if the source string does not contain the search string, ignoring case.
     *
     * @param source the string to search in
     * @param search the string to search for
     * @return {@code true} if {@code source} does not contain {@code search} ignoring case, {@code false} otherwise
     */
    public static boolean isNotContainsIgnoreCase(String source, String search) {
        return !containsIgnoreCase(source, search);
    }

    /**
     * Capitalizes the first character of each part of the string split by a full stop.
     *
     * @param str the string to capitalize
     * @return the capitalized string, or the input if it is empty or {@code null}
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder result = new StringBuilder(str.length());
        for (String s : str.split(FULL_STOP)) {
            int index = findFirstStringIndex(s);
            if (index == -1) {
                result.append(s);
                continue;
            }
            result.append(s, 0, index)
                    .append(s.substring(index, index + 1).toUpperCase())
                    .append(s.substring(index + 1));
        }
        return result.toString();
    }

    /**
     * Finds the index of the first letter character in the given string.
     *
     * @param source the string to search
     * @return the index of the first letter character, or -1 if none found or {@code source} is {@code null}
     */
    public static int findFirstStringIndex(String source) {
        if (isNull(source)) {
            return -1;
        }
        for (int i = 0; i < source.length(); i++) {
            if (Character.isLetter(source.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the index of the first occurrence of the search string in the source string.
     *
     * @param source the string to search in
     * @param search the string to search for
     * @return the index, or -1 if not found or either input is {@code null}
     */
    public static int findFirstIndex(String source, String search) {
        if (isNull(source) || isNull(search)) {
            return -1;
        }
        return source.indexOf(search);
    }

    /**
     * Capitalizes the first character of each part of the string split by the given separator.
     *
     * @param str       the string to capitalize
     * @param separator the separator to split the string
     * @return the capitalized string, or the input if it is empty or {@code null}
     */
    public static String capitalize(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        StringBuilder result = new StringBuilder(str.length());
        for (String s : str.split(separator)) {
            result.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
        }
        return result.toString();
    }

    /**
     * Reverses the given string.
     *
     * @param str the string to reverse
     * @return the reversed string, or the input if it is empty or {@code null}
     */
    public static String reverse(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Converts a {@code null} string to an empty string.
     *
     * @param data the string to convert
     * @return an empty string if {@code data} is {@code null}, otherwise {@code data}
     */
    public static String convertIfNullToEmpty(String data) {
        return isNull(data) ? EMPTY_STRING : data;
    }

    /**
     * Converts an empty string or {@code null} to {@code null}, otherwise returns the string itself.
     *
     * @param data the string to convert
     * @return {@code null} if {@code data} is {@code null} or empty, otherwise {@code data}
     */
    public static String convertIfEmptyToNull(String data) {
        return isEmpty(data) ? null : data;
    }

    // ===== Random string and integer generation =====

    /**
     * Generates a random lowercase string of default length.
     *
     * @return a random string
     */
    public static String randomString() {
        return randomString(DEFAULT_RANDOM_STRING_LENGTH);
    }

    /**
     * Generates a random lowercase string of the specified length.
     *
     * @param length the length of the random string
     * @return a random string of the given length
     */
    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomChar = (int) (Math.random() * 26) + 'a'; // Random lowercase letter
            sb.append((char) randomChar);
        }
        return sb.toString();
    }

    /**
     * Generates a random lowercase string of the specified length with a prefix.
     *
     * @param length the length of the random string (excluding prefix)
     * @param prefix the prefix to add (if {@code null}, treated as empty)
     * @return a random string with the given prefix
     */
    public static String randomString(int length, String prefix) {
        if (isNull(prefix)) {
            prefix = EMPTY_STRING;
        }
        return prefix + randomString(length);
    }

    /**
     * Generates a random lowercase string of the specified length with a prefix and suffix.
     *
     * @param length the length of the random string (excluding prefix and suffix)
     * @param prefix the prefix to add (if {@code null}, treated as empty)
     * @param suffix the suffix to add (if {@code null}, treated as empty)
     * @return a random string with the given prefix and suffix
     */
    public static String randomString(int length, String prefix, String suffix) {
        if (isNull(prefix)) {
            prefix = EMPTY_STRING;
        }
        if (isNull(suffix)) {
            suffix = EMPTY_STRING;
        }
        return prefix + randomString(length) + suffix;
    }

    /**
     * Generates a random integer between min (inclusive) and max (exclusive).
     *
     * @param min the minimum value (inclusive)
     * @param max the maximum value (exclusive)
     * @return a random integer between min and max
     * @throws IllegalArgumentException if min or max are negative, or min &gt;= max
     */
    public static int randomInt(int min, int max) {
        if (min < DEFAULT_RANDOM_INT_MIN || max < 0) {
            throw new IllegalArgumentException("Min and Max must be non-negative");
        }
        if (min >= max) {
            throw new IllegalArgumentException("Min must be less than Max");
        }
        return (int) (Math.random() * (max - min)) + min;
    }

    /**
     * Generates a random integer between 0 (inclusive) and max (exclusive).
     *
     * @param max the maximum value (exclusive)
     * @return a random integer between 0 and max
     */
    public static int randomInt(int max) {
        return randomInt(DEFAULT_RANDOM_INT_MIN, max);
    }

    /**
     * Generates a random integer between 0 (inclusive) and the default max value (exclusive).
     *
     * @return a random integer between 0 and the default max value
     */
    public static int randomInt() {
        return randomInt(DEFAULT_RANDOM_INT_MIN, DEFAULT_RANDOM_INT_MAX);
    }

    // ===== Integer and Double math utilities =====

    /**
     * Returns the sum of Integer arguments, ignoring {@code null}s.
     *
     * @param numbers Integers to sum
     * @return the sum of arguments
     */
    public static Integer sum(Integer... numbers) {
        int sum = 0;
        for (Integer number : numbers) {
            if (isNotNull(number)) {
                sum += number;
            }
        }
        return sum;
    }

    /**
     * Returns the subtraction of two Integers.
     *
     * @param a minuend (not {@code null})
     * @param b subtrahend (not {@code null})
     * @return a minus b
     * @throws IllegalArgumentException if either argument is {@code null}
     */
    public static Integer subtract(Integer a, Integer b) {
        if (isNull(a) || isNull(b)) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        return a - b;
    }

    /**
     * Returns the multiplication of two Integers.
     *
     * @param a multiplier (not {@code null})
     * @param b multiplicand (not {@code null})
     * @return product of a and b
     * @throws IllegalArgumentException if either argument is {@code null}
     */
    public static Integer multiply(Integer a, Integer b) {
        if (isNull(a) || isNull(b)) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        return a * b;
    }

    /**
     * Returns the division of two Integers.
     *
     * @param a dividend (not {@code null})
     * @param b divisor (not {@code null}, not zero)
     * @return quotient of a divided by b
     * @throws IllegalArgumentException if either argument is {@code null}
     * @throws ArithmeticException      if divisor is zero
     */
    public static Integer divide(Integer a, Integer b) {
        if (isNull(a) || isNull(b)) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }

    /**
     * Returns the modulus of two Integers.
     *
     * @param a dividend (not {@code null})
     * @param b divisor (not {@code null}, not zero)
     * @return remainder of a divided by b
     * @throws IllegalArgumentException if either argument is {@code null}
     * @throws ArithmeticException      if divisor is zero
     */
    public static Integer modulus(Integer a, Integer b) {
        if (isNull(a) || isNull(b)) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a % b;
    }

    /**
     * Returns the maximum value in an Integer array, ignoring {@code null}s.
     *
     * @param numbers array of Integers (not {@code null} or empty, with at least one non-{@code null})
     * @return the maximum Integer value
     * @throws IllegalArgumentException if no non-{@code null} values found or array is {@code null}/empty
     */
    public static Integer max(Integer... numbers) {
        if (isEmpty(numbers)) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        Integer max = null;
        for (Integer number : numbers) {
            if (isNotNull(number) && (isNull(max) || number > max)) {
                max = number;
            }
        }
        if (isNull(max)) {
            throw new IllegalArgumentException("Array must contain at least one non-null value");
        }
        return max;
    }

    /**
     * Returns the sum of Double arguments, ignoring {@code null}s.
     *
     * @param numbers Doubles to sum
     * @return the sum of arguments
     */
    public static Double sum(Double... numbers) {
        double sum = 0;
        for (Double number : numbers) {
            if (isNotNull(number)) {
                sum += number;
            }
        }
        return sum;
    }

    /**
     * Returns the subtraction of two Doubles.
     *
     * @param a minuend (not {@code null})
     * @param b subtrahend (not {@code null})
     * @return a minus b
     * @throws IllegalArgumentException if either argument is {@code null}
     */
    public static Double subtract(Double a, Double b) {
        if (isNull(a) || isNull(b)) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        return a - b;
    }

    /**
     * Returns the multiplication of two Doubles.
     *
     * @param a multiplier (not {@code null})
     * @param b multiplicand (not {@code null})
     * @return product of a and b
     * @throws IllegalArgumentException if either argument is {@code null}
     */
    public static Double multiply(Double a, Double b) {
        if (isNull(a) || isNull(b)) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        return a * b;
    }

    /**
     * Returns the division of two Doubles.
     *
     * @param a dividend (not {@code null})
     * @param b divisor (not {@code null}, not zero)
     * @return quotient of a divided by b
     * @throws IllegalArgumentException if either argument is {@code null}
     * @throws ArithmeticException      if divisor is zero
     */
    public static Double divide(Double a, Double b) {
        if (isNull(a) || isNull(b)) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        if (b == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }

    /**
     * Returns the modulus of two Doubles.
     *
     * @param a dividend (not {@code null})
     * @param b divisor (not {@code null}, not zero)
     * @return remainder of a divided by b
     * @throws IllegalArgumentException if either argument is {@code null}
     * @throws ArithmeticException      if divisor is zero
     */
    public static Double modulus(Double a, Double b) {
        if (isNull(a) || isNull(b)) {
            throw new IllegalArgumentException("Arguments must not be null");
        }
        if (b == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a % b;
    }

    /**
     * Returns the maximum value in a Double array, ignoring {@code null}s.
     *
     * @param numbers array of Doubles (not {@code null} or empty, with at least one non-{@code null})
     * @return the maximum Double value
     * @throws IllegalArgumentException if no non-{@code null} values found or array is {@code null}/empty
     */
    public static Double max(Double... numbers) {
        if (isEmpty(numbers)) {
            throw new IllegalArgumentException("Array must not be null or empty");
        }
        Double max = null;
        for (Double number : numbers) {
            if (isNotNull(number) && (isNull(max) || number > max)) {
                max = number;
            }
        }
        if (isNull(max)) {
            throw new IllegalArgumentException("Array must contain at least one non-null value");
        }
        return max;
    }
}
