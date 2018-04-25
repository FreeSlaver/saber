package com.song.saber.crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by 00013708 on 2017/6/21.
 */
public class Px500Controller {

  public static void main(String[] args) throws Exception {

    String crawlStorageFolder = "/data/crawl/px500";
    int numberOfCrawlers = 3;

    CrawlConfig crawlConfig = new CrawlConfig();
    crawlConfig.setResumableCrawling(true);
    //config.setUserAgentString("chrome");
    crawlConfig.setCrawlStorageFolder(crawlStorageFolder);
    /*config.setProxyHost("139.162.98.10");
    config.setProxyPort(9001);
    config.setProxyPassword("anystbz#123");*/

    PageFetcher pageFetcher = new PageFetcher(crawlConfig);

    RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
    robotstxtConfig.setEnabled(false);
    RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

    CrawlController controller = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);

    //controller.addSeed("https://500px.com");
    controller.addSeed("https://500px.com/photo/388736/oo-by-besim-mazhiqi");

    controller.addSeed("https://500px.com/photo/3446133/evolution-by-szilvia-pap-kutasi");
    //controller.addSeed("https://500px.com/photo/21086277/-by-maxim-gurtovoy");
    controller.start(Px500Crawler.class, numberOfCrawlers);

  }
}
