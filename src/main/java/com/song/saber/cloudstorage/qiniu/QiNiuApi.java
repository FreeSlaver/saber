package com.song.saber.cloudstorage.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Client;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.storage.model.FileListing;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import com.qiniu.util.StringMap;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 00013708 on 2017/6/11.
 * 现在想做的是一键，将capture nx2转换后的文件上传到七牛云，
 * 然后遍历得到所有图片的外链，得到外链之后，生成markdown文件，
 * 调用github命令上传发到博客。
 * 我草，这自动化，感觉屌炸天啊。
 */
public class QiNiuApi {
  //设置需要操作的账号的AK和SK
  private static final String ACCESS_KEY = "iW0qYJJiTzAzo6tK3XT0_PCcPcAtWXyvwgsd5Ed3";
  private static final String SECRET_KEY = "lQIyvnubqZ-ZNTjgRJuZ5ZEPAXrVHR2oYiZngd4a";

  private static final String GET_DOMAIN_PREFIX = "http://api.qiniu.com/v6/domain/list?tbl=";

  private static Auth auth() {
    return Auth.create(ACCESS_KEY, SECRET_KEY);
  }

  public static String getDomain(String bucketName) {
    String url = GET_DOMAIN_PREFIX + bucketName;
    StringMap stringMap = auth().authorization(url);

    Zone z = Zone.zone2();
    Configuration c = new Configuration(z);

    Client client = new Client(c.clone());
    try {
      Response response = client.get(url, stringMap);
      boolean isJson = response.isJson();
      String body = response.bodyString();
      //List<String> body = response.jsonToObject(String[].class);
      String[] strs = Json.decode(body,String[].class);
      //String defaultDomain = body.split(",")[0];
      return strs[0];
    } catch (QiniuException e) {
      e.printStackTrace();
    }
    return null;
  }

  //获取空间下的所有文件外链
  public static List<String> getExternalLinks(String bucketName) {
    String domain = getDomain(bucketName);
    Zone z = Zone.zone0();
    Configuration config = new Configuration(z);

    //实例化一个BucketManager对象
    BucketManager bucketManager = new BucketManager(auth(), config);

    try {
      //调用listFiles方法列举指定空间的指定文件
      //参数一：bucket    空间名
      //参数二：prefix    文件名前缀
      //参数三：marker    上一次获取文件列表时返回的 marker
      //参数四：limit     每次迭代的长度限制，最大1000，推荐值 100
      //参数五：delimiter 指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
      FileListing fileListing = bucketManager.listFiles(bucketName, null, null, 1000, null);
      FileInfo[] items = fileListing.items;
      List<String> externalLinkList = new ArrayList<String>();
      for (FileInfo fileInfo : items) {
        //http://orbzynzxu.bkt.clouddn.com也就是这个外链，然后拼接文件名
        //System.out.println(fileInfo.key);
        String fileName = fileInfo.key;
        StringBuilder sb = new StringBuilder("http://");
        sb.append(domain).append("/").append(fileName);
        externalLinkList.add(sb.toString());
        System.out.println(sb.toString());
      }
      return externalLinkList;
    } catch (QiniuException e) {
      //捕获异常信息
      Response r = e.response;
      System.out.println(r.toString());
    }
    return null;
  }

  public static void main(String[] args) {
    //travel使用这个为毛不行？
    String bucketName = "guizhou";
    List<String> links = getExternalLinks(bucketName);

    //System.out.println(Arrays.toString(links.toArray()));
  }
}
