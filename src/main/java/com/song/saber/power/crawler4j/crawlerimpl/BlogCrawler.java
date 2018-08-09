package com.song.power.crawler4j.crawlerimpl;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by 00013708 on 2017/6/15.
 */
public class BlogCrawler extends WebCrawler {
    private static final Logger logger = LoggerFactory.getLogger(BlogCrawler.class);
//    private LagouPageProcessor processor = new LagouPageProcessor();

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp3|zip|gz))$");

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
//    return !FILTERS.matcher(href).matches()&&href.startsWith("https://www.lagou.com/jobs/");
//        return href.startsWith("https://www.lagou.com/jobs/");
        return !FILTERS.matcher(href).matches();
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        logger.info("URL:{}",url);
        if (url.startsWith("https://www.lagou.com/jobs/")&&page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

//            processor.process(html);
        }
    }
}
