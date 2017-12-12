package com.song.saber;

import com.song.saber.file.FileUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 00013708 on 2017/9/29.
 */
public class BlogUrlRedirect {

    public static void main(String[] args) throws IOException {
        redirectAllUrls();

    }

    public static void redirectAllUrls() throws IOException {
        String oldSitemap = "F:\\sitemap\\old\\sitemap.xml";
        String newSitemap = "F:\\sitemap\\new\\sitemap.xml";

        List<String> oldUrls = getAllUrl(oldSitemap);
        List<String> newUrls = getAllUrl(newSitemap);

        Map<String,String> oldUrlMap = new HashMap<String, String>();
        Map<String,String> newUrlMap = new HashMap<String, String>();
        for(String oldUrl:oldUrls){
            int lastbackslash = oldUrl.lastIndexOf("/");
            String title = oldUrl.substring(lastbackslash);
            oldUrlMap.put(title,oldUrl);
        }
        for(String newUrl:newUrls){
            int lastbackslash = newUrl.lastIndexOf("/");
            String title = newUrl.substring(lastbackslash);
            newUrlMap.put(title,newUrl);
        }

        List<String> redirectLines = new ArrayList<String>();
        for(Map.Entry<String,String> entry:oldUrlMap.entrySet()){
            String title = entry.getKey();
            String oldUrl = entry.getValue();
            String newUrl = newUrlMap.get(title);
            String redirectLine = "Redirect "+ oldUrl+" "+newUrl+"\n";
            redirectLines.add(redirectLine);
        }
        redirectLines.add(0,"RewriteEngine On\n");
        //生成.htaccess
        File file = new File(".htaccess");
        FileUtil.append(file,redirectLines);
    }

    public static List<String> getAllUrl(String filepath) throws IOException {
        List<String> list = FileUtils.readLines(new File(filepath), "utf-8");

        List<String> urls = new ArrayList<String>();
        for (String s : list) {
            if (s.startsWith("<loc>")) {
                s = s.replace("<loc>", "");
                s = s.replace("</loc>", "");
                s = s.replace("http://localhost:4000","");
                if (s.endsWith(".html")) {
                    urls.add(s);
                }
            }
        }
        return urls;
    }
}
