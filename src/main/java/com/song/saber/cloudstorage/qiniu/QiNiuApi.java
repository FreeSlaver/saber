package com.song.saber.cloudstorage.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import com.qiniu.util.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 00013708 on 2017/6/11.
 * 现在想做的是一键，将capture nx2转换后的文件上传到七牛云，
 * 然后遍历得到所有图片的外链，得到外链之后，生成markdown文件，
 * 调用github命令上传发到博客。
 * 我草，这自动化，感觉屌炸天啊。
 */
public class QiNiuApi {
  private static final Logger logger = LoggerFactory.getLogger(QiNiuApi.class);
  //设置需要操作的账号的AK和SK
  private static final String ACCESS_KEY = "iW0qYJJiTzAzo6tK3XT0_PCcPcAtWXyvwgsd5Ed3";
  private static final String SECRET_KEY = "lQIyvnubqZ-ZNTjgRJuZ5ZEPAXrVHR2oYiZngd4a";

  private static final String GET_DOMAIN = "http://api.qiniu.com/v6/domain/list?tbl=";
  private static final String CREATE_BUCKET = "http://rs.qiniu.com/mkbucket";

  private static Auth auth() {
    return Auth.create(ACCESS_KEY, SECRET_KEY);
  }

  private static Configuration getConfig() {
    Zone z = Zone.zone0();
    return new Configuration(z);
  }

  /**
   * 获取外链的域名
   */
  public static String getDomain(String bucketName) {
    String url = GET_DOMAIN + bucketName;
    StringMap stringMap = auth().authorization(url);

    try {
      Response response = executeHttpRequest(url, stringMap, null, "get");
      String body = response.bodyString();
      String[] strs = Json.decode(body, String[].class);
      return strs[0];
    } catch (QiniuException e) {
      e.printStackTrace();
      //logger.error(e.getMessage(), e);
    }
    return null;
  }

  public static String getExternalLink(String domain, String fileName) {
    StringBuilder sb = new StringBuilder("http://");
    sb.append(domain).append("/").append(fileName);
    return sb.toString();
  }

  /**
   * 获取空间下的所有文件外链
   *
   * @param bucketName 空间名
   */
  public static List<String> getExternalLinks(String bucketName) {
    String domain = getDomain(bucketName);

    Configuration config = getConfig();

    BucketManager bucketManager = new BucketManager(auth(), config);
    try {
      FileListing fileListing = bucketManager.listFiles(bucketName, null, null, 1000, null);
      FileInfo[] items = fileListing.items;
      List<String> externalLinkList = new ArrayList<String>();
      for (FileInfo fileInfo : items) {
        String fileName = fileInfo.key;
        externalLinkList.add(getExternalLink(domain, fileName));
      }
      return externalLinkList;
    } catch (QiniuException e) {
      //捕获异常信息
      Response r = e.response;
      System.out.println(r.toString());
      //logger.info(r.toString());
    }
    return null;
  }

  /**
   * 创建一个空间
   *
   * @param bucketName 空间名，需要进行base64编码(v2才要) 日尼玛V2版本的用不了 这调用会覆盖掉原来已经有的吗
   */
  public static boolean createBucket(String bucketName) {
    if (StringUtils.isNullOrEmpty(bucketName)) {
      throw new IllegalArgumentException("bucketName null");
    }
    StringBuilder sb = new StringBuilder(CREATE_BUCKET);
    sb.append("/").append(bucketName).append("/public/0");

    String url = sb.toString();
    StringMap stringMap = auth().authorization(url);
    Response response = executeHttpRequest(url, stringMap, null, "get");
    System.out.println(response.toString());
    return response.isOK();
  }

  private static Response executeHttpRequest(String url, StringMap headers, String body,
      String httpMethod) {
    Configuration c = getConfig();
    Client client = new Client(c.clone());
    try {
      return httpMethod.equalsIgnoreCase("get") ? client.get(url, headers)
          : client.post(url, body, headers);
    } catch (QiniuException e) {
      Response response = e.response;
      System.out.println(response.toString());
    }
    return null;
  }

  public static void upload(String bucketName, File file) throws QiniuException {
    List<File> files = Arrays.asList(file);
    batchUpload(bucketName, files);
  }

  public static void batchUpload(String bucketName, List<File> files) throws QiniuException {
    Configuration c = getConfig();

    Auth auth = auth();
    String upToken = auth.uploadToken(bucketName);

    //创建上传对象
    UploadManager uploadManager = new UploadManager(c);
    Response res = null;
    for (File file : files) {
      try {
        Response response = uploadManager.put(file, file.getName(), upToken);
        System.out.println(response.toString());
      } catch (QiniuException e) {
        e.printStackTrace();
        Response response = e.response;
        System.out.println(response.toString());
        throw e;
      }
    }
  }

  public static void main(String[] args) throws QiniuException {
    //String bucketName = "guizhou";
    //List<String> links = getExternalLinks(bucketName);

    //System.out.println(Arrays.toString(links.toArray()));

    //boolean result = createBucket("test");
    //System.out.println(result);

    testBatchUpload();
  }

  public static void testBatchUpload() throws QiniuException {
    //boolean result = createBucket("test");
    String bucketName = "test";
    String picDirStr = "E:\\Dropbox\\picture\\blog";
    File picDir = new File(picDirStr);
    File[] pictures = picDir.listFiles();
    batchUpload(bucketName, Arrays.asList(pictures));
  }
}
