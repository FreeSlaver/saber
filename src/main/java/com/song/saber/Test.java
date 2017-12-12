package com.song.saber;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 00013708 on 2017/6/21.
 */
public class Test {

  public static void main(String[] args) throws IOException {
/*    OkHttpClient client = new OkHttpClient();

    Picasso picasso = new Picasso.Builder(context)
        .downloader(new OkHttp3Downloader(client))
        .build()*/
/*    String url = "https://drscdn.500px.org/photo/216786803/q%3D80_h%3D450/eec40d15e9ed858f76e21fe922a27a91";

    OkHttpClient client = new OkHttpClient();
    Request Request = new Request.Builder().url(url).get().build();

    Response response = client.newCall(Request).execute();
    ResponseBody body = response.body();
    InputStream in = body.byteStream();*/


    /*
    AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();
    Future<Response> f = asyncHttpClient.prepareGet("http://www.example.com/").execute();
    Response r = f.get();
    */
    //https:\/\/drscdn.500px.org\/photo\/388736\/m%3D2048\/2f739bb14903b810cf1a6b2d32c48c8c
    String str ="https:\\/\\/drscdn.500px.org\\/photo\\/388736\\/m%3D2048\\/2f739bb14903b810cf1a6b2d32c48c8c";
    System.out.println(str);
    System.out.println(str.startsWith(str));
    String patternString =  "https:\\*";
    Pattern pattern = Pattern.compile(patternString);

    Matcher matcher = pattern.matcher(str);
    boolean isMatch = matcher.matches();
    System.out.println(isMatch);
    int i = 0;
    while (matcher.find()){
      System.out.println("@@@@@"+matcher.group(i));
      i++;
    }


  }
}
