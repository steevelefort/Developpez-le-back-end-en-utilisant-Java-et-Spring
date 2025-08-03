package com.openclassrooms.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

  public static String formatDate(Instant instant) {
    return instant.atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }
}
