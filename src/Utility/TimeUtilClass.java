package Utility;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class TimeUtilClass {


    /**
     *
     * @param date
     * @return converted string to a zoned date time of UTC
     */
    public static ZonedDateTime convertTimeToUtc(String date) {
        TimeZone zone = TimeZone.getDefault();
        ZoneId fromTimeZone = ZoneId.of(zone.getID());    //Source timezone
        ZoneId toTimeZone = ZoneId.of("UTC");
        String dateTime = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
        ZonedDateTime currentMyTime = formatDateTime.atZone(fromTimeZone);
        ZonedDateTime currentUtcTime = currentMyTime.withZoneSameInstant(toTimeZone);

        return currentUtcTime;

    }

    /**
     *
     * @param date
     * @return converts string date to a zoned date time of User local time
     */
    public static ZonedDateTime convertTimeToLocal(String date) {
        TimeZone zone = TimeZone.getDefault();
        ZoneId fromTimeZone = ZoneId.of("UTC");
        ZoneId toTimeZone = ZoneId.of(zone.getID());    //Source timezone
        String dateTime = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
        ZonedDateTime currentMyTime = formatDateTime.atZone(fromTimeZone);
        ZonedDateTime localTime = currentMyTime.withZoneSameInstant(toTimeZone);


        return localTime;

    }


    /**
     *
     * @param date
     * @return converts string date to a zoned date time of User local time with slightly different format.
     */
    public static ZonedDateTime convertTimeToLocal2(String date) {
        TimeZone zone = TimeZone.getDefault();
        ZoneId fromTimeZone = ZoneId.of("UTC");
        ZoneId toTimeZone = ZoneId.of(zone.getID());    //Source timezone
        String dateTime = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
        ZonedDateTime currentMyTime = formatDateTime.atZone(fromTimeZone);
        ZonedDateTime localTime = currentMyTime.withZoneSameInstant(toTimeZone);


        return localTime;

    }

    /**
     *
     * @param date
     * @return converted string date to Eastern zoned time
     */
    public static ZonedDateTime convertTimeEastern(String date) {
        TimeZone zone = TimeZone.getDefault();
        ZoneId fromTimeZone = ZoneId.of(zone.getID());
        ZoneId toTimeZone = ZoneId.of("US/Eastern");    //Source timezone
        String dateTime = date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formatDateTime = LocalDateTime.parse(dateTime, formatter);
        ZonedDateTime currentMyTime = formatDateTime.atZone(fromTimeZone);
        ZonedDateTime easternTime = currentMyTime.withZoneSameInstant(toTimeZone);

        return easternTime;

    }









}
