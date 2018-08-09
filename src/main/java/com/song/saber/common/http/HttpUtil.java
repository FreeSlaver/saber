package com.song.saber.common.http;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 10000;

    public static String postJSON(String url, String content) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);
        // 设置超时时间
        RequestConfig requestConfig = RequestConfig.custom().
                setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).
                setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();// 设置请求和传输超时时间
        httppost.setConfig(requestConfig);

        httppost.addHeader("Content-Type", CONTENT_TYPE_JSON);
        HttpEntity respEntity = null;
        try {
            HttpEntity body = new InputStreamEntity(IOUtils.toInputStream(content, "UTF-8"));
            httppost.setEntity(body);
            // 执行httppost
            logger.info("HttpUtil postJson REQUEST,url:{},body:{}", url, content);
            CloseableHttpResponse response = httpClient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            respEntity = response.getEntity();
            String respContent = null;
            if (respEntity != null) {
                respContent = IOUtils.toString(respEntity.getContent(), "UTF-8");
            }
            logger.info("HttpUtil postJson RESPONSE,statusCode:{},resp:{}", statusCode, respContent);
            if (statusCode != 200) {
                logger.info("HttpUtil postJson fail,statusCode:{},resp:{}", statusCode, respContent);
                return null;
            }
            return respContent;
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }finally {
            EntityUtils.consume(respEntity);
        }
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

    public static String get(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet get = new HttpGet(url);
//        get.addHeader("Content-Type", CONTENT_TYPE_JSON);
        get.addHeader("Cookie","pgv_pvi=8133557248; pgv_si=s3016792064; ptisp=ctc; ptui_loginuin=504252262@qq.com; pt2gguin=o0504252262; uin=o0504252262; RK=bPoBkqt1Yc; ptcz=900268fea33855be892e79f69a99b5c4a16e49e837313e1e3820d46393baeb58; pgv_info=ssid=s2378023107; pgv_pvid=4647790353; _qpsvr_localtk=0.7613129162328691; skey=@2JEDMueMS; p_uin=o0504252262; Loading=Yes; QZ_FE_WEBP_SUPPORT=1; __Q_w_s_hat_seed=1; __Q_w_s__QZN_TodoMsgCnt=1; pt4_token=ZH58twV4XDiyDhL74yU*J8GRDATwTzzNf7PO8rNXSSw_; p_skey=JZYfGOps2U7R1lvO0bm6vukisGGDyFfDe5hWR9OltBY_; qz_screen=1920x1080; cpu_performance_v8=21; midas_openid=504252262; midas_openkey=@2JEDMueMS");

        HttpEntity respEntity = null;
        try {
            logger.info("HttpUtil GET REQUEST,url:{}", url);
            CloseableHttpResponse response = httpclient.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            respEntity = response.getEntity();
            String respContent = null;
            if (respEntity != null) {
                respContent = IOUtils.toString(respEntity.getContent(), "UTF-8");
            }
            logger.info("HttpUtil GET RESPONSE,statusCode:{},resp:{}", statusCode, respContent);
            if (statusCode != 200) {
                logger.error("HttpUtil GET fail,statusCode:{},resp:{}", statusCode, respContent);
                return null;
            }
            return respContent;
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }finally {
            EntityUtils.consume(respEntity);
        }
    }


    public static String postForm(String url, Map<String, String> nameValuePair) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
                .build();
        httpPost.setConfig(requestConfig);

        httpPost.setHeader("Content-Type", CONTENT_TYPE_FORM);
        httpPost.setURI(URI.create(url));

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : nameValuePair.entrySet()) {
            String name = entry.getKey();
            String val = entry.getValue();
            nvps.add(new BasicNameValuePair(name, val));
        }
        HttpEntity respEntity = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            logger.info("HttpUtil postForm REQUEST,url:{},params:{}", url, Arrays.toString(nvps.toArray()));
            HttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            respEntity = response.getEntity();
            String resp = null;
            if (respEntity != null) {
                resp = IOUtils.toString(respEntity.getContent(), "UTF-8");
            }
            logger.info("HttpUtil postForm RESPONSE,statusCode:{},resp:{}", statusCode, resp);
            if (statusCode != 200) {
                logger.error("HttpUtil postForm fail,statusCode:{},resp:{}", statusCode, resp);
                return null;
            }
            return resp;
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }finally {
            EntityUtils.consume(respEntity);
        }
    }

}
