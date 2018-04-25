package com.song.saber.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by 00013708 on 2017/6/14.
 * 使用java执行windows的命令
 */
public class WinCommandUtil {

  public static void main(String[] args) throws Exception {
    try {
      Runtime runtime = Runtime.getRuntime();
      String[] commands = new String[]{"cmd /k dir"};
      Process process = runtime.exec(commands);
      //process.waitFor();
      //读取屏幕输出
      BufferedReader strCon = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = strCon.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }

  public static void executeCommand(String command) throws IOException {
    try {
      Process process = Runtime.getRuntime().exec("cmd /k " + command);
      //process.waitFor();
      //读取屏幕输出
      BufferedReader strCon = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = strCon.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
