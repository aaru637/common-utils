package org.dk;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilsTest {

    @Test
    void testDefaultConstructorAndGetters() {
        DateTimeUtils utils = new DateTimeUtils();
        assertNotNull(utils.getCurrentDateTime());
        assertEquals(ZoneId.of(DateTimeUtils.DEFAULT_TIME_ZONE), utils.getZoneId());
    }

    @Test
    void testConstructorWithLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeUtils utils = new DateTimeUtils(now);
        assertEquals(now, utils.getCurrentDateTime());
        assertEquals(ZoneId.of(DateTimeUtils.DEFAULT_TIME_ZONE), utils.getZoneId());
    }

    @Test
    void testConstructorWithLocalDateTimeAndZoneId() {
        LocalDateTime now = LocalDateTime.now();
        ZoneId zone = ZoneId.of("America/New_York");
        DateTimeUtils utils = new DateTimeUtils(now, zone);
        assertEquals(now, utils.getCurrentDateTime());
        assertEquals(zone, utils.getZoneId());
    }

    @Test
    void testConstructorWithDateStringAndFormat() throws ParseException {
        String dateStr = "2025-08-21T15:30:00.000+0000";
        DateTimeUtils utils = new DateTimeUtils(dateStr, DateTimeUtils.DEFAULT_DATE_FORMAT);
        assertNotNull(utils.getCurrentDateTime());
    }

    @Test
    void testConstructorWithDateStringDefaultFormat() throws ParseException {
        String dateStr = "2025-08-21T15:30:00.000+0000";
        DateTimeUtils utils = new DateTimeUtils(dateStr);
        assertNotNull(utils.getCurrentDateTime());
    }

    @Test
    void testConstructorWithDateStringFormatAndZoneId() throws ParseException {
        String dateStr = "2025-08-21T15:30:00.000+0000";
        ZoneId zone = ZoneId.of("America/Los_Angeles");
        DateTimeUtils utils = new DateTimeUtils(dateStr, DateTimeUtils.DEFAULT_DATE_FORMAT, zone);
        assertEquals(zone, utils.getZoneId());
    }

    @Test
    void testConstructorWithZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        DateTimeUtils utils = new DateTimeUtils(zone);
        assertEquals(zone, utils.getZoneId());
        assertNotNull(utils.getCurrentDateTime());
    }

    @Test
    void testIsValidDateFormat() {
        assertTrue(DateTimeUtils.isValidDateFormat("yyyy-MM-dd"));
        assertFalse(DateTimeUtils.isValidDateFormat(null));
        assertFalse(DateTimeUtils.isValidDateFormat("invalid-format"));
    }

    @Test
    void testFormatDate() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 21, 10, 30));
        String formatted = utils.formatDate("yyyy-MM-dd");
        assertEquals("2025-08-21", formatted);
    }

    @Test
    void testFormatDateInvalidFormat() {
        DateTimeUtils utils = new DateTimeUtils();
        assertThrows(IllegalArgumentException.class, () -> utils.formatDate("invalid"));
    }

    @Test
    void testFormatToLocalDateTimeWithFormat() throws ParseException {
        DateTimeUtils utils = new DateTimeUtils();
        LocalDateTime ldt = utils.formatToLocalDateTime("2025-08-21T15:30:00.000+0000", DateTimeUtils.DEFAULT_DATE_FORMAT);
        assertEquals(2025, ldt.getYear());
    }

    @Test
    void testFormatToLocalDateTimeDefaultFormat() throws ParseException {
        DateTimeUtils utils = new DateTimeUtils();
        LocalDateTime ldt = utils.formatToLocalDateTime("2025-08-21T15:30:00.000+0000");
        assertEquals(2025, ldt.getYear());
    }

    @Test
    void testGetCurrentYearMonthDayMonthName() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 21, 0, 0));
        assertEquals(2025, utils.getCurrentYear());
        assertEquals(8, utils.getCurrentMonth());
        assertEquals(21, utils.getCurrentDay());
        assertEquals("AUGUST", utils.getMonthName());
    }

    @Test
    void testAddAndSubtractDays() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 21, 0, 0));
        assertEquals(LocalDateTime.of(2025, 8, 26, 0, 0), utils.addDays(5));
        assertEquals(LocalDateTime.of(2025, 8, 16, 0, 0), utils.subtractDays(5));
    }

    @Test
    void testAddAndSubtractHours() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 21, 10, 0));
        assertEquals(LocalDateTime.of(2025, 8, 21, 15, 0), utils.addHours(5));
        assertEquals(LocalDateTime.of(2025, 8, 21, 5, 0), utils.subtractHours(5));
    }

    @Test
    void testAddAndSubtractMinutes() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 21, 10, 0));
        assertEquals(LocalDateTime.of(2025, 8, 21, 10, 30), utils.addMinutes(30));
        assertEquals(LocalDateTime.of(2025, 8, 21, 9, 30), utils.subtractMinutes(30));
    }

    @Test
    void testAddAndSubtractSeconds() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 21, 10, 0, 0));
        assertEquals(LocalDateTime.of(2025, 8, 21, 10, 0, 30), utils.addSeconds(30));
        assertEquals(LocalDateTime.of(2025, 8, 21, 9, 59, 30), utils.subtractSeconds(30));
    }

    @Test
    void testDifferenceMethods() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 22, 12, 0));
        LocalDateTime other = LocalDateTime.of(2025, 8, 21, 12, 0);
        assertEquals(1, utils.differenceInDays(other));
        assertEquals(24, utils.differenceInHours(other));
        assertEquals(1440, utils.differenceInMinutes(other));
        assertEquals(86400, utils.differenceInSeconds(other));
    }

    @Test
    void testIsBeforeAfterEqual() {
        LocalDateTime now = LocalDateTime.of(2025, 8, 21, 12, 0);
        DateTimeUtils utils = new DateTimeUtils(now);
        LocalDateTime before = now.minusDays(1);
        LocalDateTime after = now.plusDays(1);
        assertTrue(utils.isAfter(before));
        assertTrue(utils.isBefore(after));
        assertTrue(utils.isEqual(now));
        assertFalse(utils.isBefore(before));
        assertFalse(utils.isAfter(after));
        assertFalse(utils.isEqual(after));
    }

    @Test
    void testGetDayOfWeekDayMonthDayYear() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 21, 0, 0);
        DateTimeUtils utils = new DateTimeUtils(dateTime);
        assertEquals(4, utils.getDayOfWeek()); // Thursday is 4
        assertEquals(21, utils.getDayOfMonth());
        assertEquals(233, utils.getDayOfYear()); // August 21 in non-leap year
    }

    @Test
    void testGetStartOfWeekAndNextWeek() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 21, 0, 0); // Thursday
        DateTimeUtils utils = new DateTimeUtils(dateTime);
        assertEquals(LocalDateTime.of(2025, 8, 18, 0, 0), utils.getStartOfWeek());
        assertEquals(LocalDateTime.of(2025, 8, 25, 0, 0), utils.getStartOfNextWeek());
    }

    @Test
    void testGetStartOfMonthAndNextMonth() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 21, 0, 0);
        DateTimeUtils utils = new DateTimeUtils(dateTime);
        assertEquals(LocalDateTime.of(2025, 8, 1, 0, 0), utils.getStartOfMonth());
        assertEquals(LocalDateTime.of(2025, 9, 1, 0, 0), utils.getStartOfNextMonth());
    }

    @Test
    void testGetStartOfYearAndNextYear() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 21, 0, 0);
        DateTimeUtils utils = new DateTimeUtils(dateTime);
        assertEquals(LocalDateTime.of(2025, 1, 1, 0, 0), utils.getStartOfYear());
        assertEquals(LocalDateTime.of(2026, 1, 1, 0, 0), utils.getStartOfNextYear());
    }

    @Test
    void testGetNextMonthAndNextYear() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 8, 21, 0, 0);
        DateTimeUtils utils = new DateTimeUtils(dateTime);
        assertEquals(LocalDateTime.of(2025, 9, 21, 0, 0), utils.getNextMonth());
        assertEquals(LocalDateTime.of(2025, 11, 21, 0, 0), utils.getNextMonth(3));
        assertEquals(LocalDateTime.of(2026, 8, 21, 0, 0), utils.getNextYear());
        assertEquals(LocalDateTime.of(2028, 8, 21, 0, 0), utils.getNextYear(3));
    }

    @Test
    void testLeapYearChecks() {
        DateTimeUtils utilsLeap = new DateTimeUtils(LocalDateTime.of(2024, 1, 1, 0, 0));
        DateTimeUtils utilsNonLeap = new DateTimeUtils(LocalDateTime.of(2025, 1, 1, 0, 0));
        assertTrue(utilsLeap.isLeapYear());
        assertFalse(utilsNonLeap.isLeapYear());

        assertTrue(DateTimeUtils.isLeapYear(2024));
        assertFalse(DateTimeUtils.isLeapYear(2025));

        assertTrue(DateTimeUtils.isLeapYear(LocalDateTime.of(2024, 12, 31, 0, 0)));
    }

    @Test
    void testIsWeekendAndIsWeekdayInstance() {
        DateTimeUtils saturday = new DateTimeUtils(LocalDateTime.of(2025, 8, 23, 0, 0));
        assertTrue(saturday.isWeekend());
        assertFalse(saturday.getWeekday());

        DateTimeUtils monday = new DateTimeUtils(LocalDateTime.of(2025, 8, 25, 0, 0));
        assertFalse(monday.isWeekend());
        assertTrue(monday.getWeekday());
    }

    @Test
    void testIsWeekendAndIsWeekdayStaticWithParams() {
        assertTrue(DateTimeUtils.isWeekend(2025, 8, 23));
        assertFalse(DateTimeUtils.isWeekend(2025, 8, 25));
        assertFalse(DateTimeUtils.isWeekday(2025, 8, 23));
        assertTrue(DateTimeUtils.isWeekday(2025, 8, 25));
    }

    @Test
    void testIsWeekendAndIsWeekdayStaticWithLocalDateTime() {
        LocalDateTime saturday = LocalDateTime.of(2025, 8, 23, 0, 0);
        LocalDateTime monday = LocalDateTime.of(2025, 8, 25, 0, 0);
        assertTrue(DateTimeUtils.isWeekend(saturday));
        assertFalse(DateTimeUtils.isWeekday(saturday));
        assertFalse(DateTimeUtils.isWeekend(monday));
        assertTrue(DateTimeUtils.isWeekday(monday));
    }

    @Test
    void testGetWeekdayNameInstanceAndStatic() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 21, 0, 0));
        assertEquals("THURSDAY", utils.getWeekdayName());
        assertEquals("THURSDAY", DateTimeUtils.getWeekdayName(2025, 8, 21));
        assertEquals("THURSDAY", DateTimeUtils.getWeekdayName(LocalDateTime.of(2025, 8, 21, 0, 0)));
    }

    @Test
    void testGetWeekOfYear() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 8, 21, 0, 0));
        int weekOfYear = utils.getWeekOfYear();
        assertTrue(weekOfYear >= 1 && weekOfYear <= 53);

        assertEquals(weekOfYear, DateTimeUtils.getWeekOfYear(LocalDateTime.of(2025, 8, 21, 0, 0)));
        assertEquals(weekOfYear, DateTimeUtils.getWeekOfYear(2025, 8, 21));
    }

    @Test
    void testToEpochMilli() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(1970, 1, 1, 0, 0));
        assertEquals(0, utils.toEpochMilli());
    }

    @Test
    void testGetMonthsInCurrentYear() {
        DateTimeUtils utils = new DateTimeUtils(LocalDateTime.of(2025, 1, 1, 0, 0));
        assertNotNull(utils.getMonthsInCurrentYear());
    }

    @Test
    void isValidDateWithValidDateAndFormatReturnsTrue() {
        assertTrue(DateTimeUtils.isValidDate("2025-08-23T15:30:00.000+0000", "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    }

    @Test
    void isValidDateWithInvalidDateAndValidFormatReturnsFalse() {
        assertFalse(DateTimeUtils.isValidDate("2025-02-30T15:30:00.000+0000", "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    }

    @Test
    void isValidDateWithValidDateAndInvalidFormatReturnsFalse() {
        assertFalse(DateTimeUtils.isValidDate("2025-08-23T15:30:00.000+0000", "invalid-format"));
    }

    @Test
    void isValidDateWithNullDateStringReturnsFalse() {
        assertFalse(DateTimeUtils.isValidDate(null, "yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
    }

    @Test
    void isValidDateWithNullFormatReturnsFalse() {
        assertFalse(DateTimeUtils.isValidDate("2025-08-23T15:30:00.000+0000", null));
    }

    @Test
    void isValidDateWithDefaultFormatAndValidDateReturnsTrue() {
        assertTrue(DateTimeUtils.isValidDate("2025-08-23T15:30:00.000+0000"));
    }

    @Test
    void isValidDateWithDefaultFormatAndInvalidDateReturnsFalse() {
        assertFalse(DateTimeUtils.isValidDate("2025-02-30T15:30:00.000+0000"));
    }

    @Test
    void isValidDateWithDefaultFormatAndNullDateReturnsFalse() {
        assertFalse(DateTimeUtils.isValidDate(null));
    }
}

