package com.song.sojob.crawler4j.crawlerimpl;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;

import java.util.regex.Pattern;

/**
 * Created by 001844 on 2018/1/19.
 */
public class InstagramCrawler extends WebCrawler {
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|"
            + "mp3|mp3|zip|gz))$");
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL();
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        logger.info("URL:{}",url);
        if(url.endsWith(".jpg")){
            System.out.println("@@@@"+url);
        }
//        if (page.getParseData() instanceof HtmlParseData) {
//            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
//            String text = htmlParseData.getText();
//            String html = htmlParseData.getHtml();
//            Set<WebURL> links = htmlParseData.getOutgoingUrls();
//
////            processor.process(html);
//        }
    }
}
