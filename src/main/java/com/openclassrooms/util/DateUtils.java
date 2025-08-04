package com.openclassrooms.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {

  /**
   * Return the given date in the format yyyy/MM/dd format
   *
   * @param instant the date to format
   * @return String the formated date
   */
  public static String formatDate(Instant instant) {
    return instant.atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
  }
}
