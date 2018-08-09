package com.song.sojob.xiami;

import com.song.sojob.common.HttpUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 001844 on 2018/1/24.
 */
public class XiamiTest {

    public static void main(String[] args) throws IOException {
          login();
    }
    public static void login() throws IOException {
        String userName = "18025360608";
        String password = "sx198942";
        String url = "https://login.xiami.com/passport/login";
        Map<String, String> map = new HashMap<String, String>();
        /*_xiamitoken:d4ccd6d2402220c26bccf21f47b4e5bf
        done:http%3A%2F%2Fwww.xiami.com%2F%3Fspm%3Da1z1s.6843761.226669510.1.yGeYDw
        verifycode:
        account:18025360608
        pw:sx198942
        submit:登 录*/
        map.put("account",userName);
        map.put("pw",password);
        map.put("_xiamitoken","d4ccd6d2402220c26bccf21f47b4e5bf");
        map.put("done","http%3A%2F%2Fwww.xiami.com%2F%3Fspm%3Da1z1s.6843761.226669510.1.yGeYDw");
        map.put("submit","登录");
        HttpResponse resp = HttpUtil.postForm(url,map);
        Header[] headers = resp.getHeaders("Set-Cookie");
        Map<String,String> cookiesMap = new HashMap<String, String>();
        for (Header h : headers) {
           cookiesMap.put(h.getName(),h.getValue());
        }
        System.out.println(cookiesMap.toString());
        String cookie = "td_cookie=3262009322; td_cookie=3259825213; gid=15167626955841; " +
                "join_from=0zqfTI9Kv2Ew3f7BEdw; _xiamitoken=d4ccd6d2402220c26bccf21f47b4e5bf; " +
                "_unsign_token=0610991c04ae5d4da707acaed405d0fc; " +
                "user_from=2; UM_distinctid=161261a09ce5a8-0fdcc3ab4978a1-393d5f0e-1fa400-161261a09cfde2; " +
                "CNZZDATA2629111=cnzz_eid%3D1101868372-1516761254-null%26ntime%3D1516761254; " +
                "cna=FhC+EjWj33ECAbcPt3atA0al; " +
                "t_sign_auth=2;" +
                " _umdata=55F3A8BFC9C50DDA1405737B7D7AFAE281519B325ED7C9D89A0E77650AEB1605F63FF2CAFC0499C8CD43AD3E795C914C456E3DC343C5B38D4861FB7B4EB8C19D; " +
                "CNZZDATA921634=cnzz_eid%3D1154552291-1516757524-null%26ntime%3D1516762924; " +
                "bdshare_firstime=1516766053557; XMPLAYER_url=/song/playlist/id/2087677/object_name/default/object_id/0; td_cookie=3263121896; XMPLAYER_isOpen=1; XMPLAYER_addSongsToggler=0; isg=BE1NmC5nK0R_rY9nCci9R7FfXGnN5nHxN2-SF4_SieRThm04V3qRzJs09BjgXZm0";
    }
}
