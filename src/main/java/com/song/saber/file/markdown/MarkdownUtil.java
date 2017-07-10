package com.song.saber.file.markdown;

import com.qiniu.common.QiniuException;
import com.song.saber.cloudstorage.qiniu.QiNiuApi;
import com.song.saber.common.date.DateUtil;
import com.song.saber.file.FileUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by 00013708 on 2017/6/11.
 * 用于处理markdown的工具类
 */
public class MarkdownUtil {

  private static final String MARKDOWN_FILE_SUFFIX = ".md";

  //生成markdown文件名
  public static String getFileFullName(String fileName) {
    Date date = new Date();
    return getFileFullName(date, fileName);
  }

  public static String getFileFullName(Date date, String fileName) {
    if (date == null) {
      date = new Date();
    }
    if (StringUtils.isEmpty(fileName)) {
      throw new IllegalArgumentException("filename null");
    }
    String dateStr = DateUtil.format(date, DateUtil.FILE_PATTERN);
    StringBuilder sb = new StringBuilder(dateStr);
    sb.append("-").append(fileName).append(MARKDOWN_FILE_SUFFIX);
    return sb.toString();
  }

  //markdown文件添加jekyll头部模板
  public static String getHeader(String title, String category) {
    if (StringUtils.isEmpty(title)) {
      throw new IllegalArgumentException("title null");
    }
    if (StringUtils.isEmpty(category)) {
      throw new IllegalArgumentException("category null");
    }
    StringBuilder sb = new StringBuilder();
    sb.append("---").append("\n")
        .append("layout:").append(" ").append("post").append("\n")
        .append("title:").append(" ").append(title).append("\n")
        .append("category:").append(" ").append(category).append("\n")
        .append("keywords:").append(" ").append("\n")
        .append("description:").append(" ").append("\n")
        .append("---").append("\n");
    return sb.toString();
  }

  /**
   * 给一组超链接，生成一个影集的markdown文件
   */
  public static void  createGalleryPost(String fileName, String title, String category,
      List<String> hyperlinks) {
    if (hyperlinks == null || hyperlinks.isEmpty()) {
      throw new IllegalArgumentException("hyperlinks null");
    }
    String fileFullName = getFileFullName(fileName);
    File file = new File(fileFullName);
    if (file.exists()) {
      return;
    }
    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
    String header = getHeader(title, category);
    FileUtil.addHeader(file, header);
    List<String> mdImageLinks = new ArrayList<String>();
    for (String hyperlink : hyperlinks) {
      String mdImageLink = getMdImageLink(null, hyperlink);
      mdImageLinks.add(mdImageLink);
    }

    FileUtil.append(file, mdImageLinks);
  }

  /**
   * 构造markdown的超链接 ![1](http://orbzynzxu.bkt.clouddn.com/DSC_0021.jpg)
   */
  public static String getMdImageLink(String alt, String hyperlink) {
    StringBuilder sb = new StringBuilder();
    if (StringUtils.isEmpty(alt)) {
      alt = "1";
    }
    sb.append("!").append("[").append(alt).append("]")
        .append("(").append(hyperlink).append(")");
    return sb.toString();
  }

  /**
   * 指定图片文件夹，新建空间，批量上传到七牛，生成markdown文件，调用git push
   * @param dir
   */
  public static void  publishGalleryPost(File dir) throws QiniuException {
    if(!dir.exists()||!dir.isDirectory()){
      return ;
    }
    String bucketName = dir.getName();
    //新建空间
    QiNiuApi.createBucket(bucketName);
    //批量上传图片
    File[] pictures = dir.listFiles();
    QiNiuApi.batchUpload(bucketName, Arrays.asList(pictures));

    //生成markdown文件
    List<String> hyperLinks = QiNiuApi.getExternalLinks(bucketName);
    createGalleryPost(bucketName,"测试影集","travelphoto",hyperLinks);
    //这个地方还有问题，就是要指定文件夹，也就是git所在的目录。
  }

  public static void main(String[] args) {
    String header = getHeader("贵州之行", "travelphoto");
    //System.out.println(header);

  }
}
