package com.song.bookonline;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.nlpcn.commons.lang.pinyin.Pinyin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 001844 on 2018/4/2.
 */
public class Utils {


    /**
     * 根据中文数字得到阿拉伯数字
     * 十，十一，我曹
     * //"三十万八千五百六十七"，这TM不是操蛋吗？
     * 其实简单来看，就分2种情况，一种是三十万，一种是十，只有单位。
     * 十二，一十二，
     *
     * @param chineseNo
     * @return
     */
    public static int arabicNo(String chineseNo) {
        if (StringUtils.isEmpty(chineseNo)) {
            throw new IllegalArgumentException("chineseNo null");
        }
        if (chineseNo.startsWith("十")) {
            chineseNo = "一" + chineseNo;
        }
        int sum = 0;
        Integer digit = null;
        for (int i = 0; i < chineseNo.length(); i++) {
            String ch = String.valueOf(chineseNo.charAt(i));

            if (digit(ch) == null) {//得到的是单位
                digit = digit * unit(ch);
            } else {
                if (digit != null) {
                    sum += digit;
                }
                digit = digit(ch);
            }
        }
        sum += digit;
        return sum;
    }


    private static Integer digit(String str) {
        if (str.equals("零")) {
            return 0;
        } else if (str.equals("一")) {
            return 1;
        } else if (str.equals("二")) {
            return 2;
        } else if (str.equals("三")) {
            return 3;
        } else if (str.equals("四")) {
            return 4;
        } else if (str.equals("五")) {
            return 5;
        } else if (str.equals("六")) {
            return 6;
        } else if (str.equals("七")) {
            return 7;
        } else if (str.equals("八")) {
            return 8;
        } else if (str.equals("九")) {
            return 9;
        }
        return null;
    }

    private static Integer unit(String s) {
        if (s.equals("个")) {
            return 1;
        } else if (s.equals("十")) {
            return 10;
        } else if (s.equals("百")) {
            return 100;
        } else if (s.equals("千")) {
            return 1000;
        } else if (s.equals("万")) {
            return 10000;
        } else {
            return null;
        }
    }


    /**
     * 根据中文得到拼音组合
     *
     * @param chinese
     * @return
     */
    public static String pinyin(String chinese) {
        return pinyin(chinese, "");
    }

    public static String pinyin(String chinese, String separator) {
        List<String> list = Pinyin.pinyin(chinese);
        return StringUtils.join(list, separator);
    }

    /**
     * 还有一种判断方式啊，就是章节的前后是一空行。
     * 使用正则表达式来判断是否章节头部
     * 第一章，第一回，一，
     *
     * @param line
     * @return
     */
    public static boolean isChapterName(String line) {
        if (StringUtils.isEmpty(line)) {
            return false;
        }
        if (line.startsWith("第") && line.contains("回")) {
            return true;
        } else if (line.equals("后记")) {
            return true;
        } else if (line.equals("成吉思汗家族")) {
            return true;
        } else if (line.equals("关于全真教")) {
            return true;
        }
        return false;
    }

    public String bookInfo(File dir) {
        if (dir == null || !dir.exists()) {
            throw new IllegalArgumentException("dir null");
        }
        //【人物传记】pdf格式放在2个【】之间的
        String dirName = dir.getName();
        int bi = dirName.indexOf("【");
        int ei = dirName.indexOf("】");

        String category = dirName.substring(bi + 1, ei);
        return category;
    }

    /*public static void utf8(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
        char[] b = new char[1024 * 5];
        int len;
        while ((len = br.read(b)) != -1) {
            bw.write(b, 0, len);
        }
        bw.flush();
        br.close();
        bw.close();
    }*/
    @SuppressWarnings("Since15")
    public static String detectEncoding(File file) {
        /*Path path = file.toPath();
        byte[]  bytes = Files.readAllBytes(path);*/
        byte[] bytes = new byte[8];
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            System.out.println(Arrays.toString(bytes));
            String fileStr = FileUtils.readFileToString(file,"UTF-8");
            fileStr = fileStr.substring(0,200);

            System.out.println(fileStr);
            System.out.println("-----");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) {
        /*int xx = arabicNo("十二");
        System.out.println(xx);

        *//**
         * 遇到三转成3，遇到十，乘以10，遇到万乘以10000，
         * 再次遇到八，将上次得到结果加到sum上去，并将X赋值为8，再次遇到十，乘以10。对，就是这么傻逼，哈哈
         *//*
        String test = "三十万八千五百六十七";
        int no = arabicNo(test);
        System.out.println(no);*/
        String dir = "F:\\\\story\\【名著文学】txtpdf格式";
        File file = new File(dir);
        File[] files = file.listFiles();
        for (File tmpFile : files) {
            detectEncoding(tmpFile);
        }

    }
}
