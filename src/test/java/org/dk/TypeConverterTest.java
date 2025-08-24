package org.dk;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TypeConverterTest {

    @Test
    void testByteToString() {
        assertEquals("10", TypeConverter.byteToString((byte) 10));
        assertEquals("-5", TypeConverter.byteToString(Byte.valueOf((byte) -5)));
    }

    @Test
    void testShortToString() {
        assertEquals("100", TypeConverter.shortToString((short) 100));
        assertEquals("-2", TypeConverter.shortToString(Short.valueOf((short) -2)));
    }

    @Test
    void testIntToString() {
        assertEquals("1234", TypeConverter.intToString(1234));
        assertEquals("-7", TypeConverter.intToString(Integer.valueOf(-7)));
    }

    @Test
    void testFloatToString() {
        assertEquals("0.5", TypeConverter.floatToString(0.5f));
        assertEquals("2.0", TypeConverter.floatToString(Float.valueOf(2.0f)));
    }

    @Test
    void testDoubleToString() {
        assertEquals("4.9", TypeConverter.doubleToString(4.9));
        assertEquals("-0.1", TypeConverter.doubleToString(Double.valueOf(-0.1)));
    }

    @Test
    void testLongToString() {
        assertEquals("7", TypeConverter.longToString(7L));
        assertEquals("-100", TypeConverter.longToString(Long.valueOf(-100)));
    }

    @Test
    void testBooleanToString() {
        assertEquals("true", TypeConverter.booleanToString(true));
        assertEquals("false", TypeConverter.booleanToString(false));
        assertEquals("true", TypeConverter.booleanToString(Boolean.TRUE));
        assertEquals("false", TypeConverter.booleanToString(Boolean.FALSE));
    }

    @Test
    void testCharToString() {
        assertEquals("x", TypeConverter.charToString('x'));
        assertEquals("y", TypeConverter.charToString(Character.valueOf('y')));
    }

    @Test
    void testObjectToString() {
        assertEquals("test", TypeConverter.objectToString("test"));
        assertEquals("123", TypeConverter.objectToString(123));
        assertEquals("null", TypeConverter.objectToString(null));
    }

    @Test
    void testCollectionToString() {
        List<String> list = Arrays.asList("a", "b");
        assertEquals(list.toString(), TypeConverter.collectionToString(list));
        assertEquals("null", TypeConverter.collectionToString(null));
    }

    @Test
    void testArrayToStringObjectArray() {
        Object[] arr = {"a", "b"};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((Object[]) null));
    }

    @Test
    void testArrayToStringIntArray() {
        int[] arr = {1, 2};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((int[]) null));
    }

    @Test
    void testArrayToStringDoubleArray() {
        double[] arr = {1.1, 2.2};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((double[]) null));
    }

    @Test
    void testArrayToStringCharArray() {
        char[] arr = {'x', 'y'};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((char[]) null));
    }

    @Test
    void testArrayToStringBooleanArray() {
        boolean[] arr = {true, false};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((boolean[]) null));
    }

    @Test
    void testArrayToStringLongArray() {
        long[] arr = {1L, 2L};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((long[]) null));
    }

    @Test
    void testArrayToStringFloatArray() {
        float[] arr = {1.0f, 2.0f};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((float[]) null));
    }

    @Test
    void testArrayToStringShortArray() {
        short[] arr = {1, 2};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((short[]) null));
    }

    @Test
    void testArrayToStringByteArray() {
        byte[] arr = {1, 2};
        assertEquals(Arrays.toString(arr), TypeConverter.arrayToString(arr));
        assertEquals("null", TypeConverter.arrayToString((byte[]) null));
    }

    @Test
    void testShortToByte() {
        assertEquals((byte) 127, TypeConverter.shortToByte((short) 127));
        assertNull(TypeConverter.shortToByte((Short) null));
        assertEquals((byte) -1, TypeConverter.shortToByte(Short.valueOf((short) -1)));
    }

    @Test
    void testIntToByte() {
        assertEquals((byte) 10, TypeConverter.intToByte(10));
        assertNull(TypeConverter.intToByte((Integer) null));
        assertEquals((byte) 99, TypeConverter.intToByte(Integer.valueOf(99)));
    }

    @Test
    void testFloatToByte() {
        assertEquals((byte) 3, TypeConverter.floatToByte(3.45f));
        assertNull(TypeConverter.floatToByte((Float) null));
        assertEquals((byte) -3, TypeConverter.floatToByte(Float.valueOf(-3.99f)));
    }

    @Test
    void testDoubleToByte() {
        assertEquals((byte) 7, TypeConverter.doubleToByte(7.99));
        assertNull(TypeConverter.doubleToByte((Double) null));
        assertEquals((byte) -1, TypeConverter.doubleToByte(Double.valueOf(-1.1)));
    }

    @Test
    void testLongToByte() {
        assertEquals((byte) 120, TypeConverter.longToByte(120L));
        assertNull(TypeConverter.longToByte((Long) null));
        assertEquals((byte) 8, TypeConverter.longToByte(Long.valueOf(8L)));
    }

    @Test
    void testBooleanToByteAndObject() {
        assertEquals((byte) 1, TypeConverter.booleanToByte(true));
        assertEquals((byte) 0, TypeConverter.booleanToByte(false));
        assertNull(TypeConverter.booleanToByte((Boolean) null));
        assertEquals((byte) 1, TypeConverter.booleanToByte(Boolean.TRUE));
        assertEquals((byte) 0, TypeConverter.booleanToByte(Boolean.FALSE));
    }

    @Test
    void testIntToShort() {
        assertEquals((short) 123, TypeConverter.intToShort(123));
        assertNull(TypeConverter.intToShort((Integer) null));
        assertEquals((short) 99, TypeConverter.intToShort(Integer.valueOf(99)));
    }

    @Test
    void testFloatToShort() {
        assertEquals((short) 12, TypeConverter.floatToShort(12.5f));
        assertNull(TypeConverter.floatToShort((Float) null));
        assertEquals((short) -5, TypeConverter.floatToShort(Float.valueOf(-5.9f)));
    }

    @Test
    void testDoubleToShort() {
        assertEquals((short) 7, TypeConverter.doubleToShort(7.99));
        assertNull(TypeConverter.doubleToShort((Double) null));
        assertEquals((short) -1, TypeConverter.doubleToShort(Double.valueOf(-1.99)));
    }

    @Test
    void testLongToShort() {
        assertEquals((short) 900, TypeConverter.longToShort(900L));
        assertNull(TypeConverter.longToShort((Long) null));
        assertEquals((short) 88, TypeConverter.longToShort(Long.valueOf(88L)));
    }

    @Test
    void testBooleanToShortAndObject() {
        assertEquals((short) 1, TypeConverter.booleanToShort(true));
        assertEquals((short) 0, TypeConverter.booleanToShort(false));
        assertNull(TypeConverter.booleanToShort((Boolean) null));
        assertEquals((short) 1, TypeConverter.booleanToShort(Boolean.TRUE));
        assertEquals((short) 0, TypeConverter.booleanToShort(Boolean.FALSE));
    }

    @Test
    void testFloatToInt() {
        assertEquals(3, TypeConverter.floatToInt(3.45f));
        assertNull(TypeConverter.floatToInt((Float) null));
        assertEquals(78, TypeConverter.floatToInt(Float.valueOf(78.91f)));
    }

    @Test
    void testDoubleToInt() {
        assertEquals(7, TypeConverter.doubleToInt(7.99));
        assertNull(TypeConverter.doubleToInt((Double) null));
        assertEquals(-12, TypeConverter.doubleToInt(Double.valueOf(-12.3)));
    }

    @Test
    void testLongToInt() {
        assertEquals(777, TypeConverter.longToInt(777L));
        assertNull(TypeConverter.longToInt((Long) null));
        assertEquals(22, TypeConverter.longToInt(Long.valueOf(22L)));
    }

    @Test
    void testBooleanToIntAndObject() {
        assertEquals(1, TypeConverter.booleanToInt(true));
        assertEquals(0, TypeConverter.booleanToInt(false));
        assertNull(TypeConverter.booleanToInt((Boolean) null));
        assertEquals(1, TypeConverter.booleanToInt(Boolean.TRUE));
        assertEquals(0, TypeConverter.booleanToInt(Boolean.FALSE));
    }

    @Test
    void testIntToFloat() {
        assertEquals(4.0f, TypeConverter.intToFloat(4));
        assertNull(TypeConverter.intToFloat((Integer) null));
        assertEquals(87.0f, TypeConverter.intToFloat(Integer.valueOf(87)));
    }

    @Test
    void testDoubleToFloat() {
        assertEquals(4.5f, TypeConverter.doubleToFloat(4.5));
        assertNull(TypeConverter.doubleToFloat((Double) null));
        assertEquals(-9.2f, TypeConverter.doubleToFloat(Double.valueOf(-9.2)));
    }

    @Test
    void testLongToFloat() {
        assertEquals(55.0f, TypeConverter.longToFloat(55L));
        assertNull(TypeConverter.longToFloat((Long) null));
        assertEquals(-88.0f, TypeConverter.longToFloat(Long.valueOf(-88L)));
    }

    @Test
    void testBooleanToFloatAndObject() {
        assertEquals(1.0f, TypeConverter.booleanToFloat(true));
        assertEquals(0.0f, TypeConverter.booleanToFloat(false));
        assertNull(TypeConverter.booleanToFloat((Boolean) null));
        assertEquals(1.0f, TypeConverter.booleanToFloat(Boolean.TRUE));
        assertEquals(0.0f, TypeConverter.booleanToFloat(Boolean.FALSE));
    }

    @Test
    void testLongToDouble() {
        assertEquals(3.0, TypeConverter.longToDouble(3L));
        assertNull(TypeConverter.longToDouble((Long) null));
        assertEquals(99.0, TypeConverter.longToDouble(Long.valueOf(99L)));
    }

    @Test
    void testBooleanToDoubleAndObject() {
        assertEquals(1.0, TypeConverter.booleanToDouble(true));
        assertEquals(0.0, TypeConverter.booleanToDouble(false));
        assertNull(TypeConverter.booleanToDouble((Boolean) null));
        assertEquals(1.0, TypeConverter.booleanToDouble(Boolean.TRUE));
        assertEquals(0.0, TypeConverter.booleanToDouble(Boolean.FALSE));
    }

    @Test
    void testBooleanToLongAndObject() {
        assertEquals(1L, TypeConverter.booleanToLong(true));
        assertEquals(0L, TypeConverter.booleanToLong(false));
        assertNull(TypeConverter.booleanToLong((Boolean) null));
        assertEquals(1L, TypeConverter.booleanToLong(Boolean.TRUE));
        assertEquals(0L, TypeConverter.booleanToLong(Boolean.FALSE));
    }

    @Test
    void testDoubleToLong() {
        assertEquals(17L, TypeConverter.doubleToLong(17.76));
        assertNull(TypeConverter.doubleToLong((Double) null));
        assertEquals(-19L, TypeConverter.doubleToLong(Double.valueOf(-19.87)));
    }

    @Test
    void testFloatToLong() {
        assertEquals(7L, TypeConverter.floatToLong(7.9f));
        assertNull(TypeConverter.floatToLong((Float) null));
        assertEquals(-5L, TypeConverter.floatToLong(Float.valueOf(-5.5f)));
    }

    @Test
    void testByteToBoolean() {
        assertTrue(TypeConverter.byteToBoolean((byte) 1));
        assertFalse(TypeConverter.byteToBoolean((byte) 0));
        assertFalse(TypeConverter.byteToBoolean((byte) 2));

        assertTrue(TypeConverter.byteToBoolean(Byte.valueOf((byte) 1)));
        assertFalse(TypeConverter.byteToBoolean(Byte.valueOf((byte) 0)));
        assertFalse(TypeConverter.byteToBoolean(Byte.valueOf((byte) 55)));
        assertFalse(TypeConverter.byteToBoolean((Byte) null));
    }

    @Test
    void testShortToBoolean() {
        assertTrue(TypeConverter.shortToBoolean((short) 1));
        assertFalse(TypeConverter.shortToBoolean((short) 5));
        assertTrue(TypeConverter.shortToBoolean(Short.valueOf((short) 1)));
        assertFalse(TypeConverter.shortToBoolean(Short.valueOf((short) 0)));
        assertFalse(TypeConverter.shortToBoolean((Short) null));
    }

    @Test
    void testIntToBoolean() {
        assertTrue(TypeConverter.intToBoolean(1));
        assertFalse(TypeConverter.intToBoolean(0));
        assertFalse(TypeConverter.intToBoolean(-1));
        assertTrue(TypeConverter.intToBoolean(Integer.valueOf(1)));
        assertFalse(TypeConverter.intToBoolean(Integer.valueOf(0)));
        assertFalse(TypeConverter.intToBoolean(Integer.valueOf(123)));
        assertFalse(TypeConverter.intToBoolean((Integer) null));
    }

    @Test
    void testLongToBoolean() {
        assertTrue(TypeConverter.longToBoolean(1L));
        assertFalse(TypeConverter.longToBoolean(88L));
        assertTrue(TypeConverter.longToBoolean(Long.valueOf(1L)));
        assertFalse(TypeConverter.longToBoolean(Long.valueOf(0L)));
        assertFalse(TypeConverter.longToBoolean((Long) null));
    }

    @Test
    void testFloatToBoolean() {
        assertTrue(TypeConverter.floatToBoolean(1.0f));
        assertFalse(TypeConverter.floatToBoolean(0.99f));
        assertTrue(TypeConverter.floatToBoolean(Float.valueOf(1.0f)));
        assertFalse(TypeConverter.floatToBoolean(Float.valueOf(0.0f)));
        assertFalse(TypeConverter.floatToBoolean((Float) null));
    }

    @Test
    void testDoubleToBoolean() {
        assertTrue(TypeConverter.doubleToBoolean(1.0));
        assertFalse(TypeConverter.doubleToBoolean(0.5));
        assertTrue(TypeConverter.doubleToBoolean(Double.valueOf(1.0)));
        assertFalse(TypeConverter.doubleToBoolean(Double.valueOf(0.0)));
        assertFalse(TypeConverter.doubleToBoolean((Double) null));
    }

    @Test
    void testCharToBoolean() {
        assertTrue(TypeConverter.charToBoolean('1'));
        assertTrue(TypeConverter.charToBoolean('T'));
        assertTrue(TypeConverter.charToBoolean('t'));
        assertTrue(TypeConverter.charToBoolean('Y'));
        assertTrue(TypeConverter.charToBoolean('y'));
        assertFalse(TypeConverter.charToBoolean('N'));

        assertTrue(TypeConverter.charToBoolean(Character.valueOf('1')));
        assertFalse(TypeConverter.charToBoolean(Character.valueOf('n')));
        assertFalse(TypeConverter.charToBoolean((Character) null));
    }

    @Test
    void testStringToBoolean() {
        assertTrue(TypeConverter.stringToBoolean("1"));
        assertTrue(TypeConverter.stringToBoolean("true"));
        assertTrue(TypeConverter.stringToBoolean("t"));
        assertTrue(TypeConverter.stringToBoolean("yes"));
        assertTrue(TypeConverter.stringToBoolean("y"));
        assertTrue(TypeConverter.stringToBoolean("on"));
        assertFalse(TypeConverter.stringToBoolean("no"));
        assertFalse(TypeConverter.stringToBoolean(null));
    }

    @Test
    void testStringToBooleanIgnoreCase() {
        assertTrue(TypeConverter.stringToBooleanIgnoreCase("TRUE"));
        assertTrue(TypeConverter.stringToBooleanIgnoreCase("Y"));
        assertTrue(TypeConverter.stringToBooleanIgnoreCase("On"));
        assertFalse(TypeConverter.stringToBooleanIgnoreCase("NO"));
        assertFalse(TypeConverter.stringToBooleanIgnoreCase(null));
    }
}
