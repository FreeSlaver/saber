package com.song.saber.common.download;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by 00013708 on 2017/6/21.
 */
public class Downloader {

  public static void download(String url) {
    if (StringUtils.isEmpty(url)) {
      throw new IllegalArgumentException("url none");
    }
    batchDownload(Arrays.asList(url));
  }

  public static void batchDownload(List<String> urls) {
    if (urls == null || urls.isEmpty()) {
      throw new IllegalArgumentException("urls none");
    }
    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      int i = 0;
      for (String url : urls) {
        HttpGet get = new HttpGet(url);
        //get.addHeader("Content-Type", "application/json");
        CloseableHttpResponse response = null;
        OutputStream os = null;
        try {
          response = httpclient.execute(get);
          int statusCode = response.getStatusLine().getStatusCode();
          if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            os = new FileOutputStream("test" + i++ + ".jpg");
            entity.writeTo(os);
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            os.close();
            response.close();
          } catch (IOException e) {
          }
        }
      }
    } finally {
      try {
        httpclient.close();
      } catch (IOException e) {
      }
    }
  }

  public static void main(String[] args) {
    String url1 =
        "https://drscdn.500px.org/photo/216814205/q%3D80_m%3D1000/60a6cd5de823cba286eb7dcf88728749";
    String url =
        "https://drscdn.500px.org/photo/216786803/q%3D80_h%3D450/eec40d15e9ed858f76e21fe922a27a91";
    List<String> urls = new ArrayList<String>();
    urls.add(url);
    urls.add(url1);

    batchDownload(urls);
  }
}
