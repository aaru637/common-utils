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

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for various type conversions in Java.
 *
 * <p>This class is not intended to be instantiated. All methods are static and can
 * be called directly on the class.</p>
 *
 * <p>This class provides methods to convert between different primitive types,
 * wrapper objects, Strings, arrays, collections, and objects.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     String byteStr = TypeConverter.byteToString((byte) 10); // "10"
 *     int intValue = TypeConverter.stringToInt("123"); // 123
 *     boolean boolValue = TypeConverter.stringToBoolean("true"); // true
 * </pre>
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @see org.dk.TypeConverter
 * <p>Created at : 2025-08-23</p>
 * @since 1.0
 */
public class TypeConverter {

    private TypeConverter() {
        // Private constructor to prevent instantiation
    }

    private static final List<String> TRUE_STRINGS = Arrays.asList("1", "true", "t", "yes", "y", "on");
    private static final String NULL_STRING = "null";

    /**
     * Converts a primitive byte to its String representation.
     *
     * @param b the byte value
     * @return the String representation of the byte
     */
    public static String byteToString(byte b) {
        return Byte.toString(b);
    }

    /**
     * Converts a Byte object to its String representation.
     *
     * @param b the Byte object
     * @return the String representation of the Byte
     */
    public static String byteToString(Byte b) {
        return Byte.toString(b);
    }

    /**
     * Converts a primitive short to its String representation.
     *
     * @param s the short value
     * @return the String representation of the short
     */
    public static String shortToString(short s) {
        return Short.toString(s);
    }

    /**
     * Converts a Short object to its String representation.
     *
     * @param s the Short object
     * @return the String representation of the Short
     */
    public static String shortToString(Short s) {
        return Short.toString(s);
    }

    /**
     * Converts a primitive int to its String representation.
     *
     * @param number the int value
     * @return the String representation of the int
     */
    public static String intToString(int number) {
        return Integer.toString(number);
    }

    /**
     * Converts an Integer object to its String representation.
     *
     * @param number the Integer object
     * @return the String representation of the Integer
     */
    public static String intToString(Integer number) {
        return Integer.toString(number);
    }

    /**
     * Converts a primitive float to its String representation.
     *
     * @param f the float value
     * @return the String representation of the float
     */
    public static String floatToString(float f) {
        return Float.toString(f);
    }

    /**
     * Converts a Float object to its String representation.
     *
     * @param f the Float object
     * @return the String representation of the Float
     */
    public static String floatToString(Float f) {
        return Float.toString(f);
    }

    /**
     * Converts a primitive double to its String representation.
     *
     * @param d the double value
     * @return the String representation of the double
     */
    public static String doubleToString(double d) {
        return Double.toString(d);
    }

    /**
     * Converts a Double object to its String representation.
     *
     * @param d the Double object
     * @return the String representation of the Double
     */
    public static String doubleToString(Double d) {
        return Double.toString(d);
    }

    /**
     * Converts a primitive long to its String representation.
     *
     * @param l the long value
     * @return the String representation of the long
     */
    public static String longToString(long l) {
        return Long.toString(l);
    }

    /**
     * Converts a Long object to its String representation.
     *
     * @param l the Long object
     * @return the String representation of the Long
     */
    public static String longToString(Long l) {
        return Long.toString(l);
    }

    /**
     * Converts a primitive boolean to its String representation.
     *
     * @param b the boolean value
     * @return the String representation of the boolean
     */
    public static String booleanToString(boolean b) {
        return Boolean.toString(b);
    }

    /**
     * Converts a Boolean object to its String representation.
     *
     * @param b the Boolean object
     * @return the String representation of the Boolean
     */
    public static String booleanToString(Boolean b) {
        return Boolean.toString(b);
    }

    /**
     * Converts a primitive char to its String representation.
     *
     * @param c the char value
     * @return the String representation of the char
     */
    public static String charToString(char c) {
        return Character.toString(c);
    }

    /**
     * Converts a Character object to its String representation.
     *
     * @param c the Character object
     * @return the String representation of the Character
     */
    public static String charToString(Character c) {
        return Character.toString(c);
    }

    /**
     * Converts an Object to its String representation, or "null" if the object is null.
     *
     * @param obj the Object to convert
     * @return the String representation of the Object or "null"
     */
    public static String objectToString(Object obj) {
        return CommonUtils.isNotNull(obj) ? obj.toString() : NULL_STRING;
    }

    /**
     * Converts a Collection to its String representation, or "null" if the collection is null.
     *
     * @param collection the Collection to convert
     * @return the String representation of the Collection or "null"
     */
    public static String collectionToString(java.util.Collection<?> collection) {
        return CommonUtils.isNotNull(collection) ? collection.toString() : NULL_STRING;
    }

    /**
     * Converts an Object array to its String representation, or "null" if the array is null.
     *
     * @param array the Object array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(Object[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts an int array to its String representation, or "null" if the array is null.
     *
     * @param array the int array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(int[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts a double array to its String representation, or "null" if the array is null.
     *
     * @param array the double array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(double[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts a char array to its String representation, or "null" if the array is null.
     *
     * @param array the char array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(char[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts a boolean array to its String representation, or "null" if the array is null.
     *
     * @param array the boolean array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(boolean[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts a long array to its String representation, or "null" if the array is null.
     *
     * @param array the long array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(long[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts a float array to its String representation, or "null" if the array is null.
     *
     * @param array the float array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(float[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts a short array to its String representation, or "null" if the array is null.
     *
     * @param array the short array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(short[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts a byte array to its String representation, or "null" if the array is null.
     *
     * @param array the byte array to convert
     * @return the String representation of the array or "null"
     */
    public static String arrayToString(byte[] array) {
        return CommonUtils.isNotNull(array) ? Arrays.toString(array) : NULL_STRING;
    }

    /**
     * Converts a primitive short to a primitive byte.
     *
     * @param s the short value
     * @return the byte value
     */
    public static byte shortToByte(short s) {
        return (byte) s;
    }

    /**
     * Converts a Short object to a Byte object.
     *
     * @param s the Short object
     * @return the Byte value or null if input is null
     */
    public static Byte shortToByte(Short s) {
        return CommonUtils.isNotNull(s) ? s.byteValue() : null;
    }

    /**
     * Converts a primitive int to a primitive byte.
     *
     * @param b the int value
     * @return the byte value
     */
    public static byte intToByte(int b) {
        return (byte) b;
    }

    /**
     * Converts an Integer object to a Byte object.
     *
     * @param b the Integer object
     * @return the Byte value or null if input is null
     */
    public static Byte intToByte(Integer b) {
        return CommonUtils.isNotNull(b) ? b.byteValue() : null;
    }

    /**
     * Converts a primitive float to a primitive byte.
     *
     * @param f the float value
     * @return the byte value
     */
    public static byte floatToByte(float f) {
        return (byte) f;
    }

    /**
     * Converts a Float object to a Byte object.
     *
     * @param f the Float object
     * @return the Byte value or null if input is null
     */
    public static Byte floatToByte(Float f) {
        return CommonUtils.isNotNull(f) ? f.byteValue() : null;
    }

    /**
     * Converts a primitive double to a primitive byte.
     *
     * @param d the double value
     * @return the byte value
     */
    public static byte doubleToByte(double d) {
        return (byte) d;
    }

    /**
     * Converts a Double object to a Byte object.
     *
     * @param d the Double object
     * @return the Byte value or null if input is null
     */
    public static Byte doubleToByte(Double d) {
        return CommonUtils.isNotNull(d) ? d.byteValue() : null;
    }

    /**
     * Converts a primitive long to a primitive byte.
     *
     * @param l the long value
     * @return the byte value
     */
    public static byte longToByte(long l) {
        return (byte) l;
    }

    /**
     * Converts a Long object to a Byte object.
     *
     * @param l the Long object
     * @return the Byte value or null if input is null
     */
    public static Byte longToByte(Long l) {
        return CommonUtils.isNotNull(l) ? l.byteValue() : null;
    }

    /**
     * Converts a primitive boolean to a primitive byte (1 for true, 0 for false).
     *
     * @param bool the boolean value
     * @return 1 if true, 0 if false
     */
    public static byte booleanToByte(boolean bool) {
        return (byte) (bool ? 1 : 0);
    }

    /**
     * Converts a Boolean object to a Byte object (1 for true, 0 for false, null if input is null).
     *
     * @param bool the Boolean object
     * @return 1 if true, 0 if false, or null if input is null
     */
    public static Byte booleanToByte(Boolean bool) {
        return CommonUtils.isNotNull(bool) ? (byte) (bool ? 1 : 0) : null;
    }

    /**
     * Converts a primitive int to a primitive short.
     *
     * @param s the int value
     * @return the short value
     */
    public static short intToShort(int s) {
        return (short) s;
    }

    /**
     * Converts an Integer object to a Short object.
     *
     * @param s the Integer object
     * @return the Short value or null if input is null
     */
    public static Short intToShort(Integer s) {
        return CommonUtils.isNotNull(s) ? s.shortValue() : null;
    }

    /**
     * Converts a primitive float to a primitive short.
     *
     * @param f the float value
     * @return the short value
     */
    public static short floatToShort(float f) {
        return (short) f;
    }

    /**
     * Converts a Float object to a Short object.
     *
     * @param f the Float object
     * @return the Short value or null if input is null
     */
    public static Short floatToShort(Float f) {
        return CommonUtils.isNotNull(f) ? f.shortValue() : null;
    }

    /**
     * Converts a primitive double to a primitive short.
     *
     * @param d the double value
     * @return the short value
     */
    public static short doubleToShort(double d) {
        return (short) d;
    }

    /**
     * Converts a Double object to a Short object.
     *
     * @param d the Double object
     * @return the Short value or null if input is null
     */
    public static Short doubleToShort(Double d) {
        return CommonUtils.isNotNull(d) ? d.shortValue() : null;
    }

    /**
     * Converts a primitive long to a primitive short.
     *
     * @param l the long value
     * @return the short value
     */
    public static short longToShort(long l) {
        return (short) l;
    }

    /**
     * Converts a Long object to a Short object.
     *
     * @param l the Long object
     * @return the Short value or null if input is null
     */
    public static Short longToShort(Long l) {
        return CommonUtils.isNotNull(l) ? l.shortValue() : null;
    }

    /**
     * Converts a primitive boolean to a primitive short (1 for true, 0 for false).
     *
     * @param bool the boolean value
     * @return 1 if true, 0 if false
     */
    public static short booleanToShort(boolean bool) {
        return (short) (bool ? 1 : 0);
    }

    /**
     * Converts a Boolean object to a Short object (1 for true, 0 for false, null if input is null).
     *
     * @param bool the Boolean object
     * @return 1 if true, 0 if false, or null if input is null
     */
    public static Short booleanToShort(Boolean bool) {
        return CommonUtils.isNotNull(bool) ? (short) (bool ? 1 : 0) : null;
    }

    /**
     * Converts a primitive float to a primitive int.
     *
     * @param f the float value
     * @return the int value
     */
    public static int floatToInt(float f) {
        return (int) f;
    }

    /**
     * Converts a Float object to an Integer object.
     *
     * @param f the Float object
     * @return the Integer value or null if input is null
     */
    public static Integer floatToInt(Float f) {
        return CommonUtils.isNotNull(f) ? f.intValue() : null;
    }

    /**
     * Converts a primitive double to a primitive int.
     *
     * @param d the double value
     * @return the int value
     */
    public static int doubleToInt(double d) {
        return (int) d;
    }

    /**
     * Converts a Double object to an Integer object.
     *
     * @param d the Double object
     * @return the Integer value or null if input is null
     */
    public static Integer doubleToInt(Double d) {
        return CommonUtils.isNotNull(d) ? d.intValue() : null;
    }

    /**
     * Converts a primitive long to a primitive int.
     *
     * @param l the long value
     * @return the int value
     */
    public static int longToInt(long l) {
        return (int) l;
    }

    /**
     * Converts a Long object to an Integer object.
     *
     * @param l the Long object
     * @return the Integer value or null if input is null
     */
    public static Integer longToInt(Long l) {
        return CommonUtils.isNotNull(l) ? l.intValue() : null;
    }

    /**
     * Converts a primitive boolean to a primitive int (1 for true, 0 for false).
     *
     * @param bool the boolean value
     * @return 1 if true, 0 if false
     */
    public static int booleanToInt(boolean bool) {
        return bool ? 1 : 0;
    }

    /**
     * Converts a Boolean object to an Integer object (1 for true, 0 for false, null if input is null).
     *
     * @param bool the Boolean object
     * @return 1 if true, 0 if false, or null if input is null
     */
    public static Integer booleanToInt(Boolean bool) {
        return CommonUtils.isNotNull(bool) ? (bool ? 1 : 0) : null;
    }

    /**
     * Converts a primitive int to a primitive float.
     *
     * @param i the int value
     * @return the float value
     */
    public static float intToFloat(int i) {
        return (float) i;
    }

    /**
     * Converts an Integer object to a Float object.
     *
     * @param i the Integer object
     * @return the Float value or null if input is null
     */
    public static Float intToFloat(Integer i) {
        return CommonUtils.isNotNull(i) ? i.floatValue() : null;
    }

    /**
     * Converts a primitive double to a primitive float.
     *
     * @param d the double value
     * @return the float value
     */
    public static float doubleToFloat(double d) {
        return (float) d;
    }

    /**
     * Converts a Double object to a Float object.
     *
     * @param d the Double object
     * @return the Float value or null if input is null
     */
    public static Float doubleToFloat(Double d) {
        return CommonUtils.isNotNull(d) ? d.floatValue() : null;
    }

    /**
     * Converts a primitive long to a primitive float.
     *
     * @param l the long value
     * @return the float value
     */
    public static float longToFloat(long l) {
        return (float) l;
    }

    /**
     * Converts a Long object to a Float object.
     *
     * @param l the Long object
     * @return the Float value or null if input is null
     */
    public static Float longToFloat(Long l) {
        return CommonUtils.isNotNull(l) ? l.floatValue() : null;
    }

    /**
     * Converts a primitive boolean to a primitive float (1.0f for true, 0.0f for false).
     *
     * @param bool the boolean value
     * @return 1.0f if true, 0.0f if false
     */
    public static float booleanToFloat(boolean bool) {
        return bool ? 1.0f : 0.0f;
    }

    /**
     * Converts a Boolean object to a Float object (1.0f for true, 0.0f for false, null if input is null).
     *
     * @param bool the Boolean object
     * @return 1.0f if true, 0.0f if false, or null if input is null
     */
    public static Float booleanToFloat(Boolean bool) {
        return CommonUtils.isNotNull(bool) ? (bool ? 1.0f : 0.0f) : null;
    }

    /**
     * Converts a primitive long to a primitive double.
     *
     * @param l the long value
     * @return the double value
     */
    public static double longToDouble(long l) {
        return (double) l;
    }

    /**
     * Converts a Long object to a Double object.
     *
     * @param l the Long object
     * @return the Double value or null if input is null
     */
    public static Double longToDouble(Long l) {
        return CommonUtils.isNotNull(l) ? l.doubleValue() : null;
    }

    /**
     * Converts a primitive boolean to a primitive double (1.0 for true, 0.0 for false).
     *
     * @param bool the boolean value
     * @return 1.0 if true, 0.0 if false
     */
    public static double booleanToDouble(boolean bool) {
        return bool ? 1.0 : 0.0;
    }

    /**
     * Converts a Boolean object to a Double object (1.0 for true, 0.0 for false, null if input is null).
     *
     * @param bool the Boolean object
     * @return 1.0 if true, 0.0 if false, or null if input is null
     */
    public static Double booleanToDouble(Boolean bool) {
        return CommonUtils.isNotNull(bool) ? (bool ? 1.0 : 0.0) : null;
    }

    /**
     * Converts a primitive boolean to a primitive long (1L for true, 0L for false).
     *
     * @param bool the boolean value
     * @return 1L if true, 0L if false
     */
    public static long booleanToLong(boolean bool) {
        return bool ? 1L : 0L;
    }

    /**
     * Converts a Boolean object to a Long object (1L for true, 0L for false, null if input is null).
     *
     * @param bool the Boolean object
     * @return 1L if true, 0L if false, or null if input is null
     */
    public static Long booleanToLong(Boolean bool) {
        return CommonUtils.isNotNull(bool) ? (bool ? 1L : 0L) : null;
    }

    /**
     * Converts a primitive double to a primitive long.
     *
     * @param d the double value
     * @return the long value
     */
    public static long doubleToLong(double d) {
        return (long) d;
    }

    /**
     * Converts a Double object to a Long object.
     *
     * @param d the Double object
     * @return the Long value or null if input is null
     */
    public static Long doubleToLong(Double d) {
        return CommonUtils.isNotNull(d) ? d.longValue() : null;
    }

    /**
     * Converts a primitive float to a primitive long.
     *
     * @param f the float value
     * @return the long value
     */
    public static long floatToLong(float f) {
        return (long) f;
    }

    /**
     * Converts a Float object to a Long object.
     *
     * @param f the Float object
     * @return the Long value or null if input is null
     */
    public static Long floatToLong(Float f) {
        return CommonUtils.isNotNull(f) ? f.longValue() : null;
    }

    /**
     * Converts a primitive byte to a primitive boolean (true if 1, false otherwise).
     *
     * @param b the byte value
     * @return true if b is 1, false otherwise
     */
    public static boolean byteToBoolean(byte b) {
        return b == 1;
    }

    /**
     * Converts a Byte object to a primitive boolean (true if 1, false otherwise).
     *
     * @param b the Byte object
     * @return true if b is 1, false otherwise
     */
    public static boolean byteToBoolean(Byte b) {
        return CommonUtils.isNotNull(b) && b == 1;
    }

    /**
     * Converts a primitive short to a primitive boolean (true if 1, false otherwise).
     *
     * @param s the short value
     * @return true if s is 1, false otherwise
     */
    public static boolean shortToBoolean(short s) {
        return s == 1;
    }

    /**
     * Converts a Short object to a primitive boolean (true if 1, false otherwise).
     *
     * @param s the Short object
     * @return true if s is 1, false otherwise
     */
    public static boolean shortToBoolean(Short s) {
        return CommonUtils.isNotNull(s) && s == 1;
    }

    /**
     * Converts a primitive int to a primitive boolean (true if 1, false otherwise).
     *
     * @param i the int value
     * @return true if i is 1, false otherwise
     */
    public static boolean intToBoolean(int i) {
        return i == 1;
    }

    /**
     * Converts an Integer object to a primitive boolean (true if 1, false otherwise).
     *
     * @param i the Integer object
     * @return true if i is 1, false otherwise
     */
    public static boolean intToBoolean(Integer i) {
        return CommonUtils.isNotNull(i) && i == 1;
    }

    /**
     * Converts a primitive long to a primitive boolean (true if 1L, false otherwise).
     *
     * @param l the long value
     * @return true if l is 1L, false otherwise
     */
    public static boolean longToBoolean(long l) {
        return l == 1L;
    }

    /**
     * Converts a Long object to a primitive boolean (true if 1L, false otherwise).
     *
     * @param l the Long object
     * @return true if l is 1L, false otherwise
     */
    public static boolean longToBoolean(Long l) {
        return CommonUtils.isNotNull(l) && l == 1L;
    }

    /**
     * Converts a primitive float to a primitive boolean (true if 1.0f, false otherwise).
     *
     * @param f the float value
     * @return true if f is 1.0f, false otherwise
     */
    public static boolean floatToBoolean(float f) {
        return f == 1.0f;
    }

    /**
     * Converts a Float object to a primitive boolean (true if 1.0f, false otherwise).
     *
     * @param f the Float object
     * @return true if f is 1.0f, false otherwise
     */
    public static boolean floatToBoolean(Float f) {
        return CommonUtils.isNotNull(f) && f == 1.0f;
    }

    /**
     * Converts a primitive double to a primitive boolean (true if 1.0, false otherwise).
     *
     * @param d the double value
     * @return true if d is 1.0, false otherwise
     */
    public static boolean doubleToBoolean(double d) {
        return d == 1.0;
    }

    /**
     * Converts a Double object to a primitive boolean (true if 1.0, false otherwise).
     *
     * @param d the Double object
     * @return true if d is 1.0, false otherwise
     */
    public static boolean doubleToBoolean(Double d) {
        return CommonUtils.isNotNull(d) && d == 1.0;
    }

    /**
     * Converts a primitive char to a primitive boolean (true if '1', 'T', 't', 'Y', or 'y').
     *
     * @param c the char value
     * @return true if c is '1', 'T', 't', 'Y', or 'y', false otherwise
     */
    public static boolean charToBoolean(char c) {
        return c == '1' || c == 'T' || c == 't' || c == 'Y' || c == 'y';
    }

    /**
     * Converts a Character object to a primitive boolean (true if '1', 'T', 't', 'Y', or 'y').
     *
     * @param c the Character object
     * @return true if c is '1', 'T', 't', 'Y', or 'y', false otherwise
     */
    public static boolean charToBoolean(Character c) {
        return CommonUtils.isNotNull(c) && (c == '1' || c == 'T' || c == 't' || c == 'Y' || c == 'y');
    }

    /**
     * Converts a String to a primitive boolean by checking if it matches any of the true values.
     *
     * @param str the String to check
     * @return true if the string matches a true value, false otherwise
     */
    public static boolean stringToBoolean(String str) {
        return CommonUtils.contains(TRUE_STRINGS, str);
    }

    /**
     * Converts a String to a primitive boolean by checking if it matches any of the true values (case-insensitive).
     *
     * @param str the String to check
     * @return true if the string matches a true value (case-insensitive), false otherwise
     */
    public static boolean stringToBooleanIgnoreCase(String str) {
        return CommonUtils.containsIgnoreCase(TRUE_STRINGS, str);
    }

    /**
     * Converts a String to a primitive int. Returns 0 if the string is not a valid integer.
     *
     * @param str the String to convert
     * @return the int value or 0 if conversion fails
     */
    public static int stringToInt(String str) {
        if (CommonUtils.isNull(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Converts a String to a primitive byte. Returns 0 if the string is not a valid byte.
     *
     * @param str the String to convert
     * @return the byte value or 0 if conversion fails
     */
    public static byte stringToByte(String str) {
        if (CommonUtils.isNull(str)) {
            return 0;
        }
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Converts a String to a primitive short. Returns 0 if the string is not a valid short.
     *
     * @param str the String to convert
     * @return the short value or 0 if conversion fails
     */
    public static short stringToShort(String str) {
        if (CommonUtils.isNull(str)) {
            return 0;
        }
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Converts a String to a primitive long. Returns 0L if the string is not a valid long.
     *
     * @param str the String to convert
     * @return the long value or 0L if conversion fails
     */
    public static long stringToLong(String str) {
        if (CommonUtils.isNull(str)) {
            return 0L;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * Converts a String to a primitive float. Returns 0.0f if the string is not a valid float.
     *
     * @param str the String to convert
     * @return the float value or 0.0f if conversion fails
     */
    public static float stringToFloat(String str) {
        if (CommonUtils.isNull(str)) {
            return 0.0f;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    /**
     * Converts a String to a primitive double. Returns 0.0 if the string is not a valid double.
     *
     * @param str the String to convert
     * @return the double value or 0.0 if conversion fails
     */
    public static double stringToDouble(String str) {
        if (CommonUtils.isNull(str)) {
            return 0.0;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Converts a String to a primitive char. Returns '\u0000' if the string is null or empty.
     *
     * @param str the String to convert
     * @return the char value or '\u0000' if the string is null or empty
     */
    public static char stringToChar(String str) {
        return CommonUtils.isNotNull(str) && !str.isEmpty() ? str.charAt(0) : '\u0000';
    }

    /**
     * Converts a String to a Character object. Returns null if the string is null or empty.
     *
     * @param str the String to convert
     * @return the Character value or null if the string is null or empty
     */
    public static Character stringToCharacter(String str) {
        return CommonUtils.isNotNull(str) && !str.isEmpty() ? str.charAt(0) : null;
    }

}