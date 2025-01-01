package org.example.springboot.Util;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static List<Timestamp> getTimestamp(String timeStart, String timeEnd) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTimeStart = LocalDateTime.parse(timeStart, formatter);
        LocalDateTime dateTimeEnd = LocalDateTime.parse(timeEnd, formatter);
        Timestamp timestampStart = Timestamp.valueOf(dateTimeStart.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
        Timestamp timestampEnd = Timestamp.valueOf(dateTimeEnd.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
        return Arrays.asList(timestampStart,timestampEnd);
    }
}
