package com.song.saber.common.date;

import java.text.ParseException;
import java.util.Date;
import org.apache.commons.lang3.time.FastDateFormat;

public class DateUtil {
  public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final String FILE_PATTERN = "yyyy-MM-dd";

  /*public static enum DatePattern {
    DEFAULT_PATTERN("yyyy-MM-dd HH:mm:ss"),

    FILE_PATTERN("yyyy-MM-dd"),;
    private String pattern;

    DatePattern(String pattern) {
      this.pattern = pattern;
    }

    public String getPattern() {
      return pattern;
    }
  }*/

  public static String format(Date d) {
    return FastDateFormat.getInstance(DEFAULT_PATTERN).format(d);
  }

  public static String format(Date d, String pattern) {
    return FastDateFormat.getInstance(pattern).format(d);
  }

  public static Date parse(String dateStr, String pattern) {
    try {
      return FastDateFormat.getInstance(pattern).parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
