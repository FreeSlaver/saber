package com.song.saber;

import com.google.common.collect.ImmutableMap;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 00013708 on 2017/10/29.
 */
public class Test2 {

    public static void main(String[] args) throws UnsupportedEncodingException {
//        test1();
//        test2();
        //取20个数
        String user = "504252262@163.com";
        String pass = "fuckudaohaod";
        BASE64Encoder encoder = new BASE64Encoder();
        String userBase64ed = encoder.encode(user.getBytes("utf-8"));
        String passBase64ed = encoder.encode(pass.getBytes("utf-8"));
        System.out.println(userBase64ed);
        System.out.println(passBase64ed);
    }

    public static void test1() {
        String[] arr = new String[]{"a", "b", "c"};
        List<String> list = Arrays.asList(arr);
        list.add("h");
        ImmutableMap<String, String> map2 = ImmutableMap.of("A", "b", "x", "y");
        ImmutableMap<String, Object> map = ImmutableMap.<String, Object>of(
                "a", "a",
                "b", 2,
                "d", "value");
    }

    public static void test2() {
        hello:
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i * j > 6) {
                    System.out.println("Breaking");
                    break hello;
                }
                System.out.println(i + " " + j);
            }
        }
        System.out.println("Done");

    }
}
