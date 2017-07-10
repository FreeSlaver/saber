package com.song.saber.hotdeploy;

/**
 * Created by 00013708 on 2017/6/12.
 * 设想的需求场景是这样的：将一个类编译成字节码，丢到redis上面去，
 * 然后正在运行的程序通过类加载器加载此最新 的类实现，以此达到目的。
 */
public class AutoUpdateCode {




  public static void main(String[] args){
    //用最简单的代码想想
    //先将类转换成字节码，然后将字节码转换成类
    System.out.println("hello");

    ClassLoader classLoader = Thread.currentThread().getClass().getClassLoader();
    //classLoader.
  }

}
