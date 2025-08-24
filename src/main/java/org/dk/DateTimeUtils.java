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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.Date;
import java.util.List;

/**
 * Utility class for various date and time operations.
 * <p>
 * Note: This class is not intended to be instantiated.
 * All methods are static and can be called directly on the class.
 * <p>
 * This class is part of the org.dk package, which provides utility methods for various date operations.
 * Includes methods for formatting, parsing, manipulating dates, as well as checking for leap years, weekends, and weekdays.
 *
 * @author Dhineshkumar Dhandapani
 * @version 1.0
 * @see org.dk.DateTimeUtils
 * @since 1.0
 * <p>Created at : 2025-08-23</p>
 */
public class DateTimeUtils {

    /**
     * Default date format pattern used in parsing and formatting.
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    /**
     * Default time zone name ("UTC") used for instantiation.
     */
    public static final String DEFAULT_TIME_ZONE = "UTC";

    /**
     * List of month names, indexed from 0 (January) to 11 (December).
     */
    public static final List<String> MONTHS = List.of(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    );

    private final LocalDateTime dateTime;
    private final ZoneId zoneId;

    /**
     * Constructs a DateTimeUtils instance with the current date and time in UTC.
     */
    public DateTimeUtils() {
        this.dateTime = LocalDateTime.now();
        this.zoneId = ZoneId.of(DEFAULT_TIME_ZONE);
    }

    /**
     * Constructs a DateTimeUtils instance with the given LocalDateTime in UTC.
     *
     * @param dateTime the LocalDateTime to use
     */
    public DateTimeUtils(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        this.zoneId = ZoneId.of(DEFAULT_TIME_ZONE);
    }

    /**
     * Constructs a DateTimeUtils instance by parsing a date string with the given format in the system default time zone.
     *
     * @param dateString the date string to parse
     * @param format     the date format
     * @throws ParseException if the date string cannot be parsed
     */
    public DateTimeUtils(String dateString, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date parsedDate = sdf.parse(dateString);
        this.dateTime = LocalDateTime.ofInstant(parsedDate.toInstant(), java.time.ZoneId.systemDefault());
        this.zoneId = ZoneId.of(DEFAULT_TIME_ZONE);
    }

    /**
     * Constructs a DateTimeUtils instance by parsing a date string with the default format in the system default time zone.
     *
     * @param dateString the date string to parse
     * @throws ParseException if the date string cannot be parsed
     */
    public DateTimeUtils(String dateString) throws ParseException {
        this(dateString, DEFAULT_DATE_FORMAT);
    }

    /**
     * Constructs a DateTimeUtils instance with the given LocalDateTime and ZoneId.
     *
     * @param dateTime the LocalDateTime to use
     * @param zoneId   the ZoneId to use
     */
    public DateTimeUtils(LocalDateTime dateTime, ZoneId zoneId) {
        this.dateTime = dateTime;
        this.zoneId = zoneId;
    }

    /**
     * Constructs a DateTimeUtils instance by parsing a date string with the given format and ZoneId.
     *
     * @param dateString the date string to parse
     * @param format     the date format
     * @param zoneId     the ZoneId to use
     * @throws ParseException if the date string cannot be parsed
     */
    public DateTimeUtils(String dateString, String format, ZoneId zoneId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date parsedDate = sdf.parse(dateString);
        this.dateTime = LocalDateTime.ofInstant(parsedDate.toInstant(), zoneId);
        this.zoneId = zoneId;
    }

    /**
     * Constructs a DateTimeUtils instance with the current date and time in the given ZoneId.
     *
     * @param zoneId the ZoneId to use
     */
    public DateTimeUtils(ZoneId zoneId) {
        this.dateTime = LocalDateTime.now(zoneId);
        this.zoneId = zoneId;
    }

    /**
     * Checks if the given date format string is valid.
     *
     * @param dateFormat the date format string
     * @return true if valid, false otherwise
     */
    public static boolean isValidDateFormat(String dateFormat) {
        if (CommonUtils.isNull(dateFormat)) {
            return false;
        }
        try {
            new SimpleDateFormat(dateFormat);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Returns the list of month names in the current year.
     *
     * @return list of month names
     */
    public List<String> getMonthsInCurrentYear() {
        return this.dateTime.getMonth().getDisplayName(java.time.format.TextStyle.FULL, java.util.Locale.getDefault()).lines().toList();
    }

    /**
     * Gets the current LocalDateTime encapsulated by this instance.
     *
     * @return the current LocalDateTime
     */
    public LocalDateTime getCurrentDateTime() {
        return this.dateTime;
    }

    /**
     * Formats the current date and time using the given format.
     *
     * @param format the date format
     * @return formatted date string
     * @throws IllegalArgumentException if the format is invalid
     */
    public String formatDate(String format) {
        if (!isValidDateFormat(format)) {
            throw new IllegalArgumentException("Invalid date format: " + format);
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = Date.from(this.dateTime.atZone(zoneId).toInstant());
        return sdf.format(date);
    }

    /**
     * Parses a date string to LocalDateTime using the given format.
     *
     * @param dateString the date string
     * @param format     the date format
     * @return parsed LocalDateTime
     * @throws ParseException if parsing fails
     */
    public LocalDateTime formatToLocalDateTime(String dateString, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date parsedDate = sdf.parse(dateString);
        return parsedDate.toInstant().atZone(zoneId).toLocalDateTime();
    }

    /**
     * Parses a date string to LocalDateTime using the default format.
     *
     * @param dateString the date string
     * @return parsed LocalDateTime
     * @throws ParseException if parsing fails
     */
    public LocalDateTime formatToLocalDateTime(String dateString) throws ParseException {
        return formatToLocalDateTime(dateString, DEFAULT_DATE_FORMAT);
    }

    /**
     * Gets the current year.
     *
     * @return the year
     */
    public int getCurrentYear() {
        return this.dateTime.getYear();
    }

    /**
     * Gets the current month value (1-12).
     *
     * @return the month value
     */
    public int getCurrentMonth() {
        return this.dateTime.getMonthValue();
    }

    /**
     * Gets the current day of the month.
     *
     * @return the day of the month
     */
    public int getCurrentDay() {
        return this.dateTime.getDayOfMonth();
    }

    /**
     * Gets the name of the current month.
     *
     * @return the month name
     */
    public String getMonthName() {
        return this.dateTime.getMonth().name();
    }

    /**
     * Adds the specified number of days to the current date.
     *
     * @param days number of days to add
     * @return new LocalDateTime
     */
    public LocalDateTime addDays(long days) {
        return this.dateTime.plusDays(days);
    }

    /**
     * Subtracts the specified number of days from the current date.
     *
     * @param days number of days to subtract
     * @return new LocalDateTime
     */
    public LocalDateTime subtractDays(long days) {
        return this.dateTime.minusDays(days);
    }

    /**
     * Adds the specified number of hours to the current date.
     *
     * @param hours number of hours to add
     * @return new LocalDateTime
     */
    public LocalDateTime addHours(int hours) {
        return this.dateTime.plusHours(hours);
    }

    /**
     * Subtracts the specified number of hours from the current date.
     *
     * @param hours number of hours to subtract
     * @return new LocalDateTime
     */
    public LocalDateTime subtractHours(int hours) {
        return this.dateTime.minusHours(hours);
    }

    /**
     * Adds the specified number of minutes to the current date.
     *
     * @param minutes number of minutes to add
     * @return new LocalDateTime
     */
    public LocalDateTime addMinutes(int minutes) {
        return this.dateTime.plusMinutes(minutes);
    }

    /**
     * Subtracts the specified number of minutes from the current date.
     *
     * @param minutes number of minutes to subtract
     * @return new LocalDateTime
     */
    public LocalDateTime subtractMinutes(int minutes) {
        return this.dateTime.minusMinutes(minutes);
    }

    /**
     * Adds the specified number of seconds to the current date.
     *
     * @param seconds number of seconds to add
     * @return new LocalDateTime
     */
    public LocalDateTime addSeconds(int seconds) {
        return this.dateTime.plusSeconds(seconds);
    }

    /**
     * Subtracts the specified number of seconds from the current date.
     *
     * @param seconds number of seconds to subtract
     * @return new LocalDateTime
     */
    public LocalDateTime subtractSeconds(int seconds) {
        return this.dateTime.minusSeconds(seconds);
    }

    /**
     * Calculates the difference in days between the current date and another LocalDateTime.
     *
     * @param otherDateTime the other LocalDateTime
     * @return difference in days
     */
    public long differenceInDays(LocalDateTime otherDateTime) {
        return Duration.between(otherDateTime, this.dateTime).toDays();
    }

    /**
     * Calculates the difference in hours between the current date and another LocalDateTime.
     *
     * @param otherDateTime the other LocalDateTime
     * @return difference in hours
     */
    public long differenceInHours(LocalDateTime otherDateTime) {
        return Duration.between(otherDateTime, this.dateTime).toHours();
    }

    /**
     * Calculates the difference in minutes between the current date and another LocalDateTime.
     *
     * @param otherDateTime the other LocalDateTime
     * @return difference in minutes
     */
    public long differenceInMinutes(LocalDateTime otherDateTime) {
        return Duration.between(otherDateTime, this.dateTime).toMinutes();
    }

    /**
     * Calculates the difference in seconds between the current date and another LocalDateTime.
     *
     * @param otherDateTime the other LocalDateTime
     * @return difference in seconds
     */
    public long differenceInSeconds(LocalDateTime otherDateTime) {
        return Duration.between(otherDateTime, this.dateTime).getSeconds();
    }

    /**
     * Checks if the current date is before the given LocalDateTime.
     *
     * @param otherDateTime the other LocalDateTime
     * @return true if before, false otherwise
     */
    public boolean isBefore(LocalDateTime otherDateTime) {
        return this.dateTime.isBefore(otherDateTime);
    }

    /**
     * Checks if the current date is after the given LocalDateTime.
     *
     * @param otherDateTime the other LocalDateTime
     * @return true if after, false otherwise
     */
    public boolean isAfter(LocalDateTime otherDateTime) {
        return this.dateTime.isAfter(otherDateTime);
    }

    /**
     * Checks if the current date is equal to the given LocalDateTime.
     *
     * @param otherDateTime the other LocalDateTime
     * @return true if equal, false otherwise
     */
    public boolean isEqual(LocalDateTime otherDateTime) {
        return this.dateTime.isEqual(otherDateTime);
    }

    /**
     * Gets the day of the week as an integer (1=Monday, 7=Sunday).
     *
     * @return day of week value
     */
    public int getDayOfWeek() {
        return this.dateTime.getDayOfWeek().getValue();
    }

    /**
     * Gets the day of the month.
     *
     * @return day of the month
     */
    public int getDayOfMonth() {
        return this.dateTime.getDayOfMonth();
    }

    /**
     * Gets the day of the year.
     *
     * @return day of the year
     */
    public int getDayOfYear() {
        return this.dateTime.getDayOfYear();
    }

    /**
     * Gets the LocalDateTime representing the start of the current week (Monday).
     *
     * @return start of the week
     */
    public LocalDateTime getStartOfWeek() {
        return this.dateTime.minusDays(this.dateTime.getDayOfWeek().getValue() - 1);
    }

    /**
     * Gets the LocalDateTime representing the start of the next week.
     *
     * @return start of the next week
     */
    public LocalDateTime getStartOfNextWeek() {
        return getStartOfWeek().plusWeeks(1);
    }

    /**
     * Gets the LocalDateTime representing the start of the current month.
     *
     * @return start of the month
     */
    public LocalDateTime getStartOfMonth() {
        return this.dateTime.withDayOfMonth(1);
    }

    /**
     * Gets the LocalDateTime representing the start of the next month.
     *
     * @return start of the next month
     */
    public LocalDateTime getStartOfNextMonth() {
        return getStartOfMonth().plusMonths(1);
    }

    /**
     * Gets the LocalDateTime representing the start of the current year.
     *
     * @return start of the year
     */
    public LocalDateTime getStartOfYear() {
        return this.dateTime.withDayOfYear(1);
    }

    /**
     * Gets the LocalDateTime representing the start of the next year.
     *
     * @return start of the next year
     */
    public LocalDateTime getStartOfNextYear() {
        return getStartOfYear().plusYears(1);
    }

    /**
     * Gets the LocalDateTime for the same day next month.
     *
     * @return LocalDateTime next month
     */
    public LocalDateTime getNextMonth() {
        return this.dateTime.plusMonths(1);
    }

    /**
     * Gets the LocalDateTime for the same day after the specified number of months.
     *
     * @param months number of months to add
     * @return LocalDateTime after months
     */
    public LocalDateTime getNextMonth(int months) {
        return this.dateTime.plusMonths(months);
    }

    /**
     * Gets the LocalDateTime for the same day next year.
     *
     * @return LocalDateTime next year
     */
    public LocalDateTime getNextYear() {
        return this.dateTime.plusYears(1);
    }

    /**
     * Gets the LocalDateTime for the same day after the specified number of years.
     *
     * @param years number of years to add
     * @return LocalDateTime after years
     */
    public LocalDateTime getNextYear(int years) {
        return this.dateTime.plusYears(years);
    }

    /**
     * Checks if the current year is a leap year.
     *
     * @return true if leap year, false otherwise
     */
    public boolean isLeapYear() {
        return this.dateTime.toLocalDate().isLeapYear();
    }

    /**
     * Checks if the specified year is a leap year.
     *
     * @param year the year to check
     * @return true if leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        return java.time.Year.isLeap(year);
    }

    /**
     * Checks if the given LocalDateTime is in a leap year.
     *
     * @param dateTime the LocalDateTime to check
     * @return true if leap year, false otherwise
     */
    public static boolean isLeapYear(LocalDateTime dateTime) {
        return dateTime.toLocalDate().isLeapYear();
    }

    /**
     * Checks if the current date is a weekend (Saturday or Sunday).
     *
     * @return true if weekend, false otherwise
     */
    public boolean isWeekend() {
        int dayOfWeek = this.dateTime.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    /**
     * Checks if the specified date is a weekend.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return true if weekend, false otherwise
     */
    public static boolean isWeekend(int year, int month, int day) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    /**
     * Checks if the given LocalDateTime is a weekend.
     *
     * @param dateTime the LocalDateTime to check
     * @return true if weekend, false otherwise
     */
    public static boolean isWeekend(LocalDateTime dateTime) {
        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    /**
     * Checks if the current date is a weekday (Monday to Friday).
     *
     * @return true if weekday, false otherwise
     */
    public boolean getWeekday() {
        int dayOfWeek = this.dateTime.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5;
    }

    /**
     * Checks if the specified date is a weekday.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return true if weekday, false otherwise
     */
    public static boolean isWeekday(int year, int month, int day) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5;
    }

    /**
     * Checks if the given LocalDateTime is a weekday.
     *
     * @param dateTime the LocalDateTime to check
     * @return true if weekday, false otherwise
     */
    public static boolean isWeekday(LocalDateTime dateTime) {
        int dayOfWeek = dateTime.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5;
    }

    /**
     * Gets the name of the current weekday.
     *
     * @return weekday name
     */
    public String getWeekdayName() {
        return this.dateTime.getDayOfWeek().name();
    }

    /**
     * Gets the name of the weekday for the specified date.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return weekday name
     */
    public static String getWeekdayName(int year, int month, int day) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
        return dateTime.getDayOfWeek().name();
    }

    /**
     * Gets the name of the weekday for the given LocalDateTime.
     *
     * @param dateTime the LocalDateTime
     * @return weekday name
     */
    public static String getWeekdayName(LocalDateTime dateTime) {
        return dateTime.getDayOfWeek().name();
    }

    /**
     * Gets the week of the year for the current date.
     *
     * @return week of the year
     */
    public int getWeekOfYear() {
        return this.dateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    /**
     * Gets the week of the year for the given LocalDateTime.
     *
     * @param dateTime the LocalDateTime
     * @return week of the year
     */
    public static int getWeekOfYear(LocalDateTime dateTime) {
        return dateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    /**
     * Gets the week of the year for the specified date.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     * @return week of the year
     */
    public static int getWeekOfYear(int year, int month, int day) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0);
        return dateTime.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    /**
     * Converts the current date and time to epoch milliseconds.
     *
     * @return epoch milliseconds
     */
    public long toEpochMilli() {
        return this.dateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    /**
     * Gets the ZoneId associated with this DateTimeUtils instance.
     *
     * @return the ZoneId used for date and time operations
     */
    public ZoneId getZoneId() {
        return this.zoneId;
    }
}
