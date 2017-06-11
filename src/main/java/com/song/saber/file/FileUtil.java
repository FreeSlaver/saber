package com.song.saber.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 00013708 on 2017/6/11.
 */
public class FileUtil {

  //在文件头部添加东西，我感觉要先读出来，然后加进去，然后写到文件。。。
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

  public static void append(File file, String tailer) {
    List<String> list = Arrays.asList(tailer);
    append(file,list);
  }

  public static void append(File file, List<String> tailers) {
    PrintWriter pw = null;
    try {
      pw = new PrintWriter(file);
      for(String tailer:tailers){
        pw.append(tailer);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      pw.close();
    }
  }

  public static void main(String[] args) throws IOException {
    File file = new File("test.txt");
    file.createNewFile();
    addHeader(file, "hello world");
  }
}
