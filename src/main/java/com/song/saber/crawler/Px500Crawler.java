package com.song.saber.crawler;

import com.song.saber.crawler.processor.Px500Processor;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import java.util.regex.Pattern;

/**
 * Created by 00013708 on 2017/6/21.
 */
public class Px500Crawler extends WebCrawler {

  private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
      + "|png|mp3|mp3|zip|gz))$");

  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase();
    return !FILTERS.matcher(href).matches();
  }

  @Override
  public void visit(Page page) {
    if (page.getParseData() instanceof HtmlParseData) {
      HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
      String html = htmlParseData.getHtml();
      //System.out.println();
      Px500Processor processor = new Px500Processor();
      processor.process(html);
    }

  }
}
