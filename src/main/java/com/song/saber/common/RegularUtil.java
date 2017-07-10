package com.song.saber.common;

import com.song.saber.file.FileUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 00013708 on 2017/6/22.
 */
public class RegularUtil {

  public static void main(String[] args){

    //https:\/\/drscdn.500px.org\/photo\/388736\/m%3D2048\/2f739bb14903b810cf1a6b2d32c48c8c
/*    String pattern1 = "\\\\";
    String txt1 = "\\";
    System.out.println(match(pattern1,txt1));*/

/*    String pattern2 = "https:\\\\/drcdn";
    String txt2 = "https:\\/drcdn";
    System.out.println(match(pattern2,txt2));*/
//\\/(d*) %3D2048
/*    String pattern3 = "https:\\\\/\\\\/drscdn.500px.org\\\\/photo\\\\/\\d*\\\\/[mdh]%3D\\d*\\\\/\\w{32}";
    String txt3 = "https:\\/\\/drscdn.500px.org\\/photo\\/388736\\/m%3D2048\\/2f739bb14903b810cf1a6b2d32c48c8c";
    System.out.println(match(pattern3,txt3));*/

    test2();

  }

  public static boolean match(String regularExpression,String txt){
    Pattern pattern = Pattern.compile(regularExpression);

    Matcher matcher = pattern.matcher(txt);
    return matcher.matches();
  }

  public static void test2(){
    //将文件读成字符串
    String html = FileUtil.file2String("E:\\saber\\src\\main\\java\\com\\song\\saber\\crawler\\util\\test.html");

    String regularExp = "https:\\\\/\\\\/drscdn.500px.org\\\\/photo\\\\/\\d*\\\\/[a-zA-Z_0-9%]+\\\\/\\w{32}";

    Set<String> set = new HashSet<String>();
    Pattern pattern = Pattern.compile(regularExp);
    Matcher matcher = pattern.matcher(html);
    //现在要做的是去重，加上
    String bestQtyImg = null;
    int largestW=0;
    while (matcher.find()){
      String matchStr = matcher.group();
      //对url进行解码，关键这个编码是多少？
      String url = null;
      try {
        url = URLDecoder.decode(matchStr, "utf-8");
        //url = URLDecoder.decode(matchStr, "gb2312");
        //去掉转义符backsplash
         url= url.replace("\\","");

       /* if(url.contains("m=")){//算了，自己写个，尼玛
          int start = url.indexOf("m=");
          //对啊，他妈的，可以用正则表达式匹配到m=xx啊
        }*/
        String regularExp2 = "m=\\d*";
        Pattern pattern2 = Pattern.compile(regularExp2);
        Matcher matcher2 = pattern2.matcher(url);
        if(matcher2.find()){
          String mstr = matcher2.group();
          int width = Integer.valueOf(mstr.split("=")[1]);
          if(width>largestW){
            largestW = width;
          }
          bestQtyImg = url;
        }

        //set.add(url);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    System.out.println(bestQtyImg);
    if(set!=null&&!set.isEmpty()){
      for(String url:set){
        System.out.println(url);
      }
    }
    //再对url中的m进行排序，得到最大的m的值。


  }
}
