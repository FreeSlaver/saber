package com.song.sojob.xiami;

import com.song.sojob.common.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by 001844 on 2018/1/16.
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        String cookie = "td_cookie=3262009322; td_cookie=3259825213; gid=15167626955841; join_from=0zqfTI9Kv2Ew3f7BEdw; _xiamitoken=d4ccd6d2402220c26bccf21f47b4e5bf; _unsign_token=0610991c04ae5d4da707acaed405d0fc; user_from=2; UM_distinctid=161261a09ce5a8-0fdcc3ab4978a1-393d5f0e-1fa400-161261a09cfde2; CNZZDATA2629111=cnzz_eid%3D1101868372-1516761254-null%26ntime%3D1516761254; cna=FhC+EjWj33ECAbcPt3atA0al; t_sign_auth=2; _umdata=55F3A8BFC9C50DDA1405737B7D7AFAE281519B325ED7C9D89A0E77650AEB1605F63FF2CAFC0499C8CD43AD3E795C914C456E3DC343C5B38D4861FB7B4EB8C19D; CNZZDATA921634=cnzz_eid%3D1154552291-1516757524-null%26ntime%3D1516762924; bdshare_firstime=1516766053557; XMPLAYER_url=/song/playlist/id/2087677/object_name/default/object_id/0; td_cookie=3263121896; XMPLAYER_isOpen=1; XMPLAYER_addSongsToggler=0; isg=BE1NmC5nK0R_rY9nCci9R7FfXGnN5nHxN2-SF4_SieRThm04V3qRzJs09BjgXZm0";
//     final   Map<String,String> cookieMap = login();
        final Map<String, String> cookieMap = cookies(cookie);
        String urlTemplate = "http://www.xiami.com/space/lib-song/u/11763281/page/%d?spm=a1z1s.6928797.1561534521.388.rAG3gp";

        Document firstDoc = fetch(String.format(urlTemplate, 1), cookieMap);
        int pageNum = getPageNum(firstDoc);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);

        final ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue();
        for (int i = 1; i <= pageNum; i++) {
            final String url = String.format(urlTemplate, i);
            executorService.submit(new Runnable() {
                public void run() {
                    Document doc = null;
                    try {
//                        logger.info("url:{}",url);
                        doc = Jsoup.connect(url).header("Connection", "keep-alive")
                                .cookies(cookieMap)
                                .get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Elements elements = doc.select("div.c695_l_content table.track_list tr td.song_name");
                    List<String> songList = new ArrayList<String>();
                    for (Element element : elements) {
                        Elements links = element.select("a");
                        XiamiSongVo xiamiSongVo = parse(links);
                        songList.add(xiamiSongVo.toString());
                    }
                    queue.addAll(songList);
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        FileUtils.writeLines(new File("song.txt"), queue, true);

    }

    private static Document fetch(String url, Map<String, String> cookieMap) throws IOException {
        return Jsoup.connect(url).header("Connection", "keep-alive")
                .cookies(cookieMap)
                .get();
    }

    private static int getPageNum(Document document) throws IOException {
        //每页25首，然后总共多少首，可以页面取到
        //取到总共多少首歌  例子：(第1页, 共6351条)
        String totalStr = document.select("div.all_page span").text();
        int indexBegin = totalStr.indexOf("共");
        int indexEnd = totalStr.indexOf("条");
        int total = Integer.valueOf(totalStr.substring(indexBegin + 1, indexEnd));
        int pageSize = 25;
        int pageNum = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
        return pageNum;
    }


    public static Map<String, String> cookies(String cookie) {
        //将cookie转换成map
        Map<String, String> cookieMap = new HashMap<String, String>();
        String[] strs = cookie.split(";");
        for (String str : strs) {
            String[] strTemp = str.trim().split("=");
            cookieMap.put(strTemp[0].trim(), strTemp[1].trim());
        }
        return cookieMap;
    }


    public static XiamiSongVo parse(Elements links) {
        Element firstElement = links.first();
        String songName = firstElement.text();
        String songHref = firstElement.attr("href");
        String album = null;
        try {
            Document songDoc = Jsoup.connect(songHref).get();
            album = songDoc.select("#albums_info tr").first().select(" td div a").text();
        } catch (IOException e) {
            e.printStackTrace();
        }
//            Element secondElement = links.get(1);
//            String mvHref = secondElement.absUrl("href");
        Element thirdElement = links.last();
        String artist = thirdElement.text();
        String artistHref = thirdElement.absUrl("href");

        XiamiSongVo xiamiSongVo = new XiamiSongVo();
        xiamiSongVo.setName(songName);
//            xiamiSongVo.setHref(songHref);
//            xiamiSongVo.setMvHref(mvHref);
        xiamiSongVo.setArtist(artist);
//            xiamiSongVo.setArtistHref(artistHref);
        xiamiSongVo.setAlbum(album);
        logger.info(xiamiSongVo.toString());
        return xiamiSongVo;
    }

    public static Map<String, String> login() throws IOException {
        String userName = "18025360608";
        String password = "sx198942";
        String url = "https://login.xiami.com/passport/login";
        Map<String, java.lang.String> map = new HashMap<String, java.lang.String>();
        /*_xiamitoken:d4ccd6d2402220c26bccf21f47b4e5bf
        done:http%3A%2F%2Fwww.xiami.com%2F%3Fspm%3Da1z1s.6843761.226669510.1.yGeYDw
        verifycode:
        account:18025360608
        pw:sx198942
        submit:登 录*/
        map.put("account", userName);
        map.put("pw", password);
        HttpResponse resp = HttpUtil.postForm(url, map);
        Header[] headers = resp.getHeaders("Set-Cookie");
        Map<String, String> cookiesMap = new HashMap<String, String>();
        for (Header h : headers) {
            cookiesMap.put(h.getName(), h.getValue());
        }
        return cookiesMap;
    }

}
