package com.song.sojob.crawler4j.controller;

import com.song.sojob.crawler4j.crawlerimpl.InstagramCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by 001844 on 2018/1/19.
 */
public class InstagramController {

    public static void main(String[] args) throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setIncludeHttpsPages(true);
        config.setCrawlStorageFolder("/data/crawl/instagram");
//        int timeout = 10 * 1000;
        config.setConnectionTimeout(0);
        config.setSocketTimeout(0);

        config.setProxyHost("127.0.0.1");
        config.setProxyPort(1080);
        config.setIncludeBinaryContentInCrawling(true);
        config.setProcessBinaryContentInCrawling(true);


        PageFetcher pageFetcher = new PageFetcher(config);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        robotstxtConfig.setIgnoreUADiscrimination(true);

        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);


        controller.addSeed("http://tofo.me/");
        int threadNums = Runtime.getRuntime().availableProcessors() * 2;
        controller.start(InstagramCrawler.class, threadNums);
    }
}
