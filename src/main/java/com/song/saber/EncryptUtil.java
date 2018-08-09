package com.song.saber;

/**
 * Created by 001844 on 2018/6/22.
 */
public class EncryptUtil {
    public static String encryptPhone(String phone) {
        return encrypt(phone, 3, 3+4);
    }
    public static  String encryptIdCard(String idCard){
        return encrypt(idCard, 6, 6+8);
    }


    public static String encrypt(String s, int begin, int end) {
        if (org.apache.commons.lang.StringUtils.isEmpty(s)) {
            throw new IllegalArgumentException("s null");
        }
        if (begin < 0) {
            throw new IndexOutOfBoundsException("begin:" + begin);
        }
        if (end >= s.length()) {
            throw new IndexOutOfBoundsException("end:" + end + ",s length:" + s.length());
        }
        if (begin > end) {
            throw new IndexOutOfBoundsException("begin>end,begin:" + begin + "end:" + end);
        }
        char[] chars = s.toCharArray();
        for (int i = begin; i < end; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }


    public static void main(String[] args) {
        String s = "18025360608";
        String r = encrypt(s, 3, 7);
        System.out.println(r);
        String idCard = "420117199010105999";
        String rs = encryptIdCard(idCard);
        System.out.println(rs);
    }
}
