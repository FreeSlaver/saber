package com.song.saber.common.http;

import java.io.IOException;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 要设置他妈的超时时间我草。
 */
public class HttpUtil {
  private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

  public static String post(String url, String content)
      throws ClientProtocolException, IOException {
    return post("application/json", url, content);
  }

  public static String post(String contentType, String url, String content)
      throws ClientProtocolException, IOException {

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost httppost = new HttpPost(url);
    httppost.addHeader("Content-Type", contentType);
    httppost.setEntity(new InputStreamEntity(IOUtils.toInputStream(content, "UTF-8")));

    // 设置超时时间
    RequestConfig requestConfig =
        RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(6000).build();// 设置请求和传输超时时间
    httppost.setConfig(requestConfig);

    // 执行httppost
    CloseableHttpResponse response = httpClient.execute(httppost);

    int statusCode = response.getStatusLine().getStatusCode();
    HttpEntity entity = response.getEntity();
    String respContent = null;
    if (entity != null) {
      respContent = IOUtils.toString(entity.getContent(), "UTF-8");
    }
    if (statusCode != 200) {
      logger.error("HTTP POST fail,statusCode=>" + statusCode + ",content=>" + respContent);
      return null;
    }
    return respContent;
  }

  public static String get(String url, Map<String, String> params)
      throws ClientProtocolException, IOException {
    StringBuilder sb = new StringBuilder(url);
    sb.append("?");
    for (Map.Entry<String, String> entry : params.entrySet()) {
      String key = entry.getKey();
      String val = entry.getValue();
      sb.append("&").append(key).append("=").append(val);
    }
    return get(sb.toString());
  }

  public static String get(String url) throws ClientProtocolException, IOException {
    HttpGet get = new HttpGet(url);

    get.addHeader("Content-Type", "application/json");
    CloseableHttpClient httpclient = HttpClients.createDefault();

    CloseableHttpResponse response = httpclient.execute(get);
    int statusCode = response.getStatusLine().getStatusCode();
    HttpEntity entity = response.getEntity();
    String respContent = null;
    if (entity != null) {
      respContent = IOUtils.toString(entity.getContent(), "UTF-8");
    }
    if (statusCode != 200) {
      logger.error("HTTP GET fail,statusCode=>" + statusCode + ",content=>" + respContent);
      return null;
    }
    return respContent;
  }

  public static void main(String[] args) throws IOException {
    //String url = "https://500px.com/photo/3446133/evolution-by-szilvia-pap-kutasi";
    //String url = "https://500px.com/photo/388736/oo-by-besim-mazhiqi";

    String url = "https://500px.com/popular";
   String respContent =  get(url);
   System.out.println(respContent);

  }
}
