package com.song.saber.crawler.processor;

import com.song.saber.file.FileUtil;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 00013708 on 2017/6/22.
 */
public class Px500Processor {

  private static final String PX500_PIC_REGULAR_EXP =
      "https:\\\\/\\\\/drscdn.500px.org\\\\/photo\\\\/\\d*\\\\/[a-zA-Z_0-9%]+\\\\/\\w{32}";
  //Pattern是线程安全的，Matcher不是的
  private static final Pattern pattern = Pattern.compile(PX500_PIC_REGULAR_EXP);
  //从html页面通过正则表达式拿到xx
  //先只写一个方法,不对，他妈的，还是得将方法分开
  //1.从html页面根据regularEx获取到需要的url，即图片地址，并去重？
  //2. 去重后的url中，进行url decode，并找出最大m值（质量最高）的图片

  private static final File file = new File("500pxBestQtyImg.txt");

  public void process(String html) {
    Matcher matcher = pattern.matcher(html);

    while (matcher.find()) {
      String matchedStr = matcher.group();
      System.out.println("@@@@@"+matchedStr);
      String url = null;
      try {
        url = URLDecoder.decode(matchedStr, "utf-8");
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
        continue;
      }
      url = url.replace("\\", "");
      String regularExp2 = "m=\\d*";
      Pattern pattern2 = Pattern.compile(regularExp2);
      Matcher matcher2 = pattern2.matcher(url);
      int maxMVal = 0;
      String bestQtyImg = null;
      if (matcher2.find()) {
        String mstr = matcher2.group();
        int mVal = Integer.valueOf(mstr.split("=")[1]);
        if (mVal > maxMVal) {
          maxMVal = mVal;
          bestQtyImg = url;
        }
        bestQtyImg = url;
        FileUtil.append(file,bestQtyImg);
      }
    }
  }
}
