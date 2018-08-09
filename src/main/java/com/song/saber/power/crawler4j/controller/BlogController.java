package com.song.power.crawler4j.controller;

import com.song.power.crawler4j.crawlerimpl.BlogCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class BlogController {
    public static void main(String[] args) throws Exception {
        CrawlConfig config = new CrawlConfig();
        config.setIncludeHttpsPages(true);
        config.setCrawlStorageFolder("/data/crawl/lagou");
        int timeout = 10 * 1000;
        config.setConnectionTimeout(timeout);
        config.setSocketTimeout(timeout);
//        config.setUserAgentString("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");

        PageFetcher pageFetcher = new PageFetcher(config);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        robotstxtConfig.setIgnoreUADiscrimination(true);

        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

//        controller.addSeed("https://www.lagou.com");
//        controller.addSeed("https://www.lagou.com/zhaopin/Java/?labelWords=label");
        controller.addSeed(" https://www.lagou.com/jobs/3713477.html");
        int threadNums = Runtime.getRuntime().availableProcessors() * 2;
        controller.start(BlogCrawler.class, threadNums);
    }
}