package org.dk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Collection;

class CommonUtilsTest {

    @Test
    void testIsNullAndIsNotNull() {
        assertTrue(CommonUtils.isNull((String) null));
        assertTrue(CommonUtils.isNull((Object) null));
        assertFalse(CommonUtils.isNull("test"));
        assertFalse(CommonUtils.isNull(42));

        assertFalse(CommonUtils.isNotNull((String) null));
        assertFalse(CommonUtils.isNotNull((Object) null));
        assertTrue(CommonUtils.isNotNull("test"));
        assertTrue(CommonUtils.isNotNull(42));
    }

    @Test
    void testIsEmpty_String() {
        assertTrue(CommonUtils.isEmpty((String) null));
        assertTrue(CommonUtils.isEmpty(""));
        assertFalse(CommonUtils.isEmpty("abc"));
    }

    @Test
    void testIsEmpty_Collection() {
        assertTrue(CommonUtils.isEmpty((List<?>) null));
        assertTrue(CommonUtils.isEmpty(Collections.emptyList()));
        assertFalse(CommonUtils.isEmpty(Arrays.asList(1, 2)));
    }

    @Test
    void testIsEmpty_Map() {
        assertTrue(CommonUtils.isEmpty((Map<?, ?>) null));
        assertTrue(CommonUtils.isEmpty(Collections.emptyMap()));
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        assertFalse(CommonUtils.isEmpty(map));
    }

    @Test
    void testIsEmpty_CharSequence() {
        CharSequence nullSeq = null;
        assertTrue(CommonUtils.isEmpty(nullSeq));
        assertTrue(CommonUtils.isEmpty(""));
        assertFalse(CommonUtils.isEmpty(new StringBuilder("a")));
    }

    @Test
    void testIsEmpty_Array() {
        assertTrue(CommonUtils.isEmpty((Object[]) null));
        assertTrue(CommonUtils.isEmpty(new Object[0]));
        assertFalse(CommonUtils.isEmpty(new Integer[]{1, 2}));
    }

    @Test
    void testIsNotEmptyVariants() {
        assertFalse(CommonUtils.isNotEmpty((String) null));
        assertFalse(CommonUtils.isNotEmpty(""));
        assertTrue(CommonUtils.isNotEmpty("hi"));

        assertFalse(CommonUtils.isNotEmpty((List<?>) null));
        assertFalse(CommonUtils.isNotEmpty(Collections.emptyList()));
        assertTrue(CommonUtils.isNotEmpty(Arrays.asList(1, 2)));

        assertFalse(CommonUtils.isNotEmpty((Map<?, ?>) null));
        assertFalse(CommonUtils.isNotEmpty(Collections.emptyMap()));
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        assertTrue(CommonUtils.isNotEmpty(map));

        assertFalse(CommonUtils.isNotEmpty((CharSequence) null));
        assertFalse(CommonUtils.isNotEmpty(""));
        assertTrue(CommonUtils.isNotEmpty(new StringBuilder("hi")));

        assertFalse(CommonUtils.isNotEmpty((Object[]) null));
        assertFalse(CommonUtils.isNotEmpty(new String[]{}));
        assertTrue(CommonUtils.isNotEmpty(new String[]{"a"}));
    }

    @Test
    void testIsEmptyReturnNullAndDefault() {
        assertNull(CommonUtils.isEmptyReturnNull(null));
        assertNull(CommonUtils.isEmptyReturnNull(""));
        assertEquals("abc", CommonUtils.isEmptyReturnNull("abc"));

        assertEquals("default", CommonUtils.isEmpty(null, "default"));
        assertEquals("default", CommonUtils.isEmpty("", "default"));
        assertEquals("abc", CommonUtils.isEmpty("abc", "default"));
    }

    @Test
    void testIsStringsEqual() {
        assertTrue(CommonUtils.isStringsEqual("abc", "abc"));
        assertTrue(CommonUtils.isStringsEqual(" abc ", "abc"));
        assertFalse(CommonUtils.isStringsEqual(null, "abc"));
        assertFalse(CommonUtils.isStringsEqual("abc", null));
        assertFalse(CommonUtils.isStringsEqual("abc", "def"));
    }

    @Test
    void testIsStringsNotEqual() {
        assertFalse(CommonUtils.isStringsNotEqual("abc", "abc"));
        assertTrue(CommonUtils.isStringsNotEqual("abc", "def"));
        assertTrue(CommonUtils.isStringsNotEqual(null, "abc"));
    }

    @Test
    void testIsStringsEqualWithIgnoreCase() {
        assertTrue(CommonUtils.isStringsEqualWithIgnoreCase("abc", "ABC"));
        assertTrue(CommonUtils.isStringsEqualWithIgnoreCase(" abc ", "ABC"));
        assertFalse(CommonUtils.isStringsEqualWithIgnoreCase(null, "abc"));
    }

    @Test
    void testIsStringsNotEqualWithIgnoreCase() {
        assertFalse(CommonUtils.isStringsNotEqualWithIgnoreCase("x", "X"));
        assertTrue(CommonUtils.isStringsNotEqualWithIgnoreCase("abc", "def"));
        assertTrue(CommonUtils.isStringsNotEqualWithIgnoreCase(null, "abc"));
    }

    @Test
    void testContainsAndIsNotContainsString() {
        assertTrue(CommonUtils.contains("abcdef", "cd"));
        assertFalse(CommonUtils.contains("abcdef", "xyz"));
        assertFalse(CommonUtils.contains((String) null, "a"));
        assertFalse(CommonUtils.contains("a", null));
        assertTrue(CommonUtils.isNotContains("abcdef", "xyz"));
        assertFalse(CommonUtils.isNotContains("abcdef", "cd"));
    }

    @Test
    void testContainsAndIsNotContainsCollection() {
        List<String> list = Arrays.asList("a", "b", "c");
        assertTrue(CommonUtils.contains(list, "b"));
        assertFalse(CommonUtils.contains(list, "x"));
        assertFalse(CommonUtils.contains((Collection<String>) null, "a"));
        assertFalse(CommonUtils.contains(list, null));
        assertTrue(CommonUtils.isNotContains(list, "x"));
        assertFalse(CommonUtils.isNotContains(list, "b"));
    }

    @Test
    void testContainsKeyContainsValueAndIsNot() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 2);
        assertTrue(CommonUtils.containsKey(map, "a"));
        assertFalse(CommonUtils.containsKey(map, "c"));
        assertFalse(CommonUtils.containsKey(null, "a"));
        assertFalse(CommonUtils.containsKey(map, null));
        assertTrue(CommonUtils.isNotContainsKey(map, "c"));

        assertTrue(CommonUtils.containsValue(map, 1));
        assertFalse(CommonUtils.containsValue(map, 3));
        assertFalse(CommonUtils.containsValue(null, 1));
        assertFalse(CommonUtils.containsValue(map, null));
        assertTrue(CommonUtils.isNotContainsValue(map, 3));
    }

    @Test
    void testContainsAndIsNotContainsArray() {
        String[] arr = {"a", "b", "c"};
        assertTrue(CommonUtils.contains(arr, "b"));
        assertFalse(CommonUtils.contains(arr, "x"));
        assertFalse(CommonUtils.contains((String) null, "a"));
        assertFalse(CommonUtils.contains(arr, null));
        assertTrue(CommonUtils.isNotContains(arr, "x"));
        assertFalse(CommonUtils.isNotContains(arr, "a"));
    }

    @Test
    void testContainsAndIsNotContainsList() {
        List<Object> list = Arrays.asList("a", 1, true);
        assertTrue(CommonUtils.contains(list, true));
        assertFalse(CommonUtils.contains(list, "x"));
        assertFalse(CommonUtils.contains((List<Object>) null, "a"));
        assertFalse(CommonUtils.contains(list, null));
    }

    @Test
    void testContainsIgnoreCase() {
        List<String> list = Arrays.asList("a", "b", "cD");
        assertTrue(CommonUtils.containsIgnoreCase(list, "cd"));
        assertFalse(CommonUtils.containsIgnoreCase(list, "ZZ"));
        assertFalse(CommonUtils.containsIgnoreCase((List<String>) null, "a"));
        assertFalse(CommonUtils.containsIgnoreCase(list, null));
    }

    @Test
    void testContainsIgnoreCaseStringAndIsNotContainsIgnoreCase() {
        assertTrue(CommonUtils.containsIgnoreCase("abCD", "abc"));
        assertFalse(CommonUtils.containsIgnoreCase("abCD", "ZZ"));
        assertFalse(CommonUtils.containsIgnoreCase((List<String>) null, "a"));
        assertFalse(CommonUtils.containsIgnoreCase("abc", null));
        assertTrue(CommonUtils.isNotContainsIgnoreCase("abCD", "ZZ"));
        assertFalse(CommonUtils.isNotContainsIgnoreCase("abCD", "CD"));
    }

    @Test
    void testCapitalizeWithDot() {
        assertEquals("HelloWorld", CommonUtils.capitalize("hello.world"));
        assertNull(CommonUtils.capitalize((String) null));
        assertEquals("", CommonUtils.capitalize(""));
        assertEquals("A", CommonUtils.capitalize("...a"));
    }

    @Test
    void testFindFirstStringIndex() {
        assertEquals(-1, CommonUtils.findFirstStringIndex(null));
        assertEquals(-1, CommonUtils.findFirstStringIndex("1234"));
        assertEquals(2, CommonUtils.findFirstStringIndex("12a"));
        assertEquals(0, CommonUtils.findFirstStringIndex("a12"));
    }

    @Test
    void testFindFirstIndex() {
        assertEquals(-1, CommonUtils.findFirstIndex(null, "a"));
        assertEquals(-1, CommonUtils.findFirstIndex("abc", null));
        assertEquals(1, CommonUtils.findFirstIndex("abc", "b"));
    }

    @Test
    void testCapitalizeWithSeparator() {
        assertEquals("HelloOrld", CommonUtils.capitalize("helloWorld", "W"));
        assertNull(CommonUtils.capitalize(null, "x"));
        assertEquals("", CommonUtils.capitalize("", "x"));
    }

    @Test
    void testReverse() {
        assertNull(CommonUtils.reverse(null));
        assertEquals("", CommonUtils.reverse(""));
        assertEquals("cba", CommonUtils.reverse("abc"));
    }

    @Test
    void testConvertIfNullToEmpty() {
        assertEquals("", CommonUtils.convertIfNullToEmpty(null));
        assertEquals("abc", CommonUtils.convertIfNullToEmpty("abc"));
    }

    @Test
    void testConvertIfEmptyToNull() {
        assertNull(CommonUtils.convertIfEmptyToNull(null));
        assertNull(CommonUtils.convertIfEmptyToNull(""));
        assertEquals("abc", CommonUtils.convertIfEmptyToNull("abc"));
    }

    @Test
    void testRandomString() {
        String random = CommonUtils.randomString(5);
        assertNotNull(random);
        assertEquals(5, random.length());

        String random2 = CommonUtils.randomString();
        assertNotNull(random2);
        assertEquals(10, random2.length());

        String prefix = "pre";
        String suffix = "suf";
        String withPrefix = CommonUtils.randomString(5, prefix);
        assertTrue(withPrefix.startsWith(prefix));
        assertEquals(prefix.length() + 5, withPrefix.length());

        String withPrefixAndSuffix = CommonUtils.randomString(5, prefix, suffix);
        assertTrue(withPrefixAndSuffix.startsWith(prefix));
        assertTrue(withPrefixAndSuffix.endsWith(suffix));
        assertEquals(prefix.length() + 5 + suffix.length(), withPrefixAndSuffix.length());

        assertTrue(CommonUtils.randomString(5, "test").startsWith("test"));
        assertTrue(CommonUtils.randomString(5, "test", null).startsWith("test"));
        assertTrue(CommonUtils.randomString(5, "test", "suf").endsWith("suf"));
    }

    @Test
    void testRandomIntVariants() {
        int rand1 = CommonUtils.randomInt(1, 10);
        assertTrue(rand1 >= 1 && rand1 < 10);

        int rand2 = CommonUtils.randomInt(5);
        assertTrue(rand2 >= 0 && rand2 < 5);

        int rand3 = CommonUtils.randomInt();
        assertTrue(rand3 >= 0 && rand3 < 10000);

        Exception e1 = assertThrows(IllegalArgumentException.class, () -> CommonUtils.randomInt(-1, 10));
        Exception e2 = assertThrows(IllegalArgumentException.class, () -> CommonUtils.randomInt(1, -5));
        Exception e3 = assertThrows(IllegalArgumentException.class, () -> CommonUtils.randomInt(10, 1));
        assertTrue(e1.getMessage().contains("non-negative"));
        assertTrue(e2.getMessage().contains("non-negative"));
        assertTrue(e3.getMessage().contains("less than"));
    }

    @Test
    void testIntegerMathSum() {
        assertEquals(6, CommonUtils.sum(1, 2, 3));
        assertEquals(3, CommonUtils.sum(1, null, 2));
        assertEquals(0, CommonUtils.sum((Integer[]) new Integer[]{}));
    }

    @Test
    void testIntegerMathSubMulDivMod() {
        assertEquals(1, CommonUtils.subtract(3, 2));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> CommonUtils.subtract(null, 3));
        Exception e2 = assertThrows(IllegalArgumentException.class, () -> CommonUtils.subtract(3, null));

        assertEquals(6, CommonUtils.multiply(3, 2));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.multiply(null, 2));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.multiply(2, null));

        assertEquals(2, CommonUtils.divide(4, 2));
        Exception e3 = assertThrows(IllegalArgumentException.class, () -> CommonUtils.divide(null, 2));
        Exception e4 = assertThrows(IllegalArgumentException.class, () -> CommonUtils.divide(2, null));
        Exception e5 = assertThrows(ArithmeticException.class, () -> CommonUtils.divide(2, 0));

        assertEquals(1, CommonUtils.modulus(7, 3));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.modulus(null, 2));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.modulus(2, null));
        assertThrows(ArithmeticException.class, () -> CommonUtils.modulus(2, 0));
    }

    @Test
    void testIntegerMax() {
        assertEquals(3, CommonUtils.max(1, 2, 3));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.max((Integer[]) null));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.max((Integer) null, null));
    }

    @Test
    void testDoubleMaths() {
        assertEquals(6.0, CommonUtils.sum(1.0, 2.0, 3.0));
        assertEquals(3.0, CommonUtils.sum(1.0, null, 2.0));
        assertEquals(0.0, CommonUtils.sum(new Double[]{}));

        assertEquals(1.0, CommonUtils.subtract(3.0, 2.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.subtract(null, 3.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.subtract(3.0, null));

        assertEquals(6.0, CommonUtils.multiply(3.0, 2.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.multiply(null, 2.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.multiply(2.0, null));

        assertEquals(2.0, CommonUtils.divide(4.0, 2.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.divide(null, 2.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.divide(2.0, null));
        assertThrows(ArithmeticException.class, () -> CommonUtils.divide(2.0, 0.0));

        assertEquals(1.0, CommonUtils.modulus(7.0, 3.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.modulus(null, 2.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.modulus(2.0, null));
        assertThrows(ArithmeticException.class, () -> CommonUtils.modulus(2.0, 0.0));
    }

    @Test
    void testDoubleMax() {
        assertEquals(3.0, CommonUtils.max(1.0, 2.0, 3.0));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.max((Double[]) null));
        assertThrows(IllegalArgumentException.class, () -> CommonUtils.max((Double) null, null));
    }
}
