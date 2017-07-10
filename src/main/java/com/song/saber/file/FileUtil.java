package com.song.saber.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 00013708 on 2017/6/11.
 */
public class FileUtil {

  /**
   * 文件头部添加
   */
  public static void addHeader(File file, String header) {
    RandomAccessFile f = null;
    try {
      f = new RandomAccessFile(file, "rw");
      f.seek(0); // to the beginning
      f.write(header.getBytes());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        f.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 文件尾部添加
   */
  public static void append(File file, String tailer) {
    List<String> list = Arrays.asList(tailer);
    append(file, list);
  }

  /**
   * 文件尾部添加
   */
  public static void append(File file, List<String> tailers) {
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
        return;
      }
    }
    PrintWriter pw = null;
    try {
      pw = new PrintWriter(file);
      for (String tailer : tailers) {
        pw.append(tailer);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      pw.close();
    }
  }

  /**
   * 将org文件转markdown格式
   */
  public File org2Md(File file) {
    //需要得到以下几个信息，文件名，title，去掉*开头的，将**的转换成###，更多的*更多的#
    //还有就是超链接的转换
    return null;
  }

  public static String file2String(String filePath) {
    InputStream is = null;
    BufferedReader reader = null;
    try {
      is = new FileInputStream(filePath);
      reader = new BufferedReader(new InputStreamReader(is));

      StringBuffer sb = new StringBuffer();
      String line = reader.readLine();
      while (line != null) {
        sb.append(line);
        sb.append("\n");
        line = reader.readLine();
      }
      return sb.toString();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        reader.close();
        is.close();
      } catch (IOException e) {
      }
    }
    return null;
  }

  public static void main(String[] args) throws IOException {
    File file = new File("test.txt");
    file.createNewFile();
    addHeader(file, "hello world");
  }
}
