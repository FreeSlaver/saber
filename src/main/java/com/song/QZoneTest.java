package com.song;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.song.saber.common.http.HttpUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by 001844 on 2018/5/5.
 */
public class QZoneTest {
    public static int pos = 0;
    public static int total = 425;

    public static void main(String[] args) throws IOException {
        File file = new File("qzone.txt");
        String url = "https://user.qzone.qq.com/proxy/domain/taotao.qq.com/cgi-bin/emotion_cgi_msglist_v6?uin=504252262&ftype=0&sort=0&replynum=100&callback=_preloadCallback&code_version=1&format=jsonp&need_private_comment=1&qzonetoken=37668506047b380a02f7c1b03857a4d3a0890084713830a7b8f822cc2eb7ae34c0b77527a9ffc704ef&g_tk=852835650&num=40";

        while (total > 0) {
            String posStr = "&pos=" + pos;
            String url2 = url + posStr;
            String str = HttpUtil.get(url2);
            proc(str, file);

        }
    }

    public static void proc(String str, File file) throws IOException {
        int si = str.indexOf("(");
        int ei = str.lastIndexOf(")");
        String jsonStr = str.substring(si + 1, ei);
        JSONObject jo = JSON.parseObject(jsonStr);
        JSONArray jar = (JSONArray) jo.get("msglist");
        pos += jar.size();
        total = total - jar.size();

        for (int i = 0; i < jar.size(); i++) {
            JSONObject jsonObject = (JSONObject) jar.get(i);
            String content = (String) jsonObject.get("content");
            String createTime = (String) jsonObject.get("createTime");
            String result = "#### " + createTime + "\r\n" + content + "\r\n";

            FileUtils.write(file, result, "UTF-8", true);
        }
    }
}
