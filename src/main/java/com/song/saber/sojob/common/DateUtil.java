package com.song.sojob.common;

import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

public class DateUtil {
  public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

  public static final String FILE_PATTERN = "yyyy-MM-dd";

  public static String format(Date d) {
    return FastDateFormat.getInstance(DEFAULT_PATTERN).format(d);
  }

  public static String format(Date d, String pattern) {
    return FastDateFormat.getInstance(pattern).format(d);
  }

  public static Date parse(String dateStr, String pattern) {
    try {
      return org.apache.commons.lang3.time.FastDateFormat.getInstance(pattern).parse(dateStr);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
