package com.song.sojob.crawler4j.processor;


import com.song.sojob.common.DateUtil;
import com.song.sojob.jobdesc.vo.JobDescVo;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by 001844 on 2018/1/16.
 */
public class LagouPageProcessor {
    private static final Logger logger = LoggerFactory.getLogger(LagouPageProcessor.class);

    /*转换成对象*/
    public void process(String html) {
        JobDescVo jobDescVo = parse(html);

        //
    }
    //保存到数据库，db等
    public void persist(){

    }

    public JobDescVo parse(String html) {
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        Document doc = Jsoup.parseBodyFragment(html);

        String companyName = doc.select("div.company").text();

        String[] strs = doc.select("p.publish_time").text().split(" ");
        /*yyyy-MM-dd HH:mm:ss 09:52得到年月日，时分*/
        Date publishTimeX = DateUtil.parse(strs[0], "HH:mm");
        Date d = new Date();
        d.setHours(publishTimeX.getHours());
        d.setMinutes(publishTimeX.getMinutes());
        Date publishTime = d;
        String publishFrom = strs[1];

        Elements ddSpans = doc.select("dd.job_request p span");
//        String salary = ddSpans.get(0).text();//salary
        String city = ddSpans.get(1).text().replace("/", "");//深圳
        String experience = ddSpans.get(2).text();//经验5到10年
        String educationRequired = ddSpans.get(3).text();//本科及以上
        String jobType = ddSpans.get(4).text();//全职
        //System.out.println("salary:" + salary + ",city:" + city + ",experience:" + experience + ",educationR:" + educationRequired + ",jobType:" + jobType);


        String jobName = doc.select("span.ceil-job").text();
        String salary = doc.select("span.ceil-salary").text();
        String[] advantages = doc.select("span.advantage").next().get(0).text().split(",");

        Elements jobBt = doc.select("dd.job_bt div p");
        /*岗位职责*/
        List<String> jobResponsities = new ArrayList<String>();
        int jr = 0;
        /*职位要求*/
        List<String> jobRequirement = new ArrayList<String>();

        int jre = 0;
        for (int i = 0; i < jobBt.size(); i++) {
            Element ele = jobBt.get(i);
            String text = ele.text();
            if (text.startsWith("岗位职责")) {
                jr = i + 1;
            } else if (text.startsWith("职位要求")) {
                jre = i + 1;
            }
        }

        jobResponsities = new Elements(jobBt.subList(jr, jre - 1)).eachText();
        jobRequirement = new Elements(jobBt.subList(jre, jobBt.size())).eachText();

        List<String> tags = doc.select("li.lables").eachText();
        String workAddress = doc.select("div.work_addr").text().replace("查看地图", "").replace("-", "");

        JobDescVo jobDescVo = new JobDescVo();
        jobDescVo.setJdUrl("https://www.lagou.com/jobs/3713477.html");
        jobDescVo.setCompany(companyName);
        jobDescVo.setJobName(jobName);
        jobDescVo.setSalary(salary);
        jobDescVo.setCity(city);
        jobDescVo.setExperience(experience);
        jobDescVo.setEducationalRequirements(educationRequired);
        jobDescVo.setJobType(jobType);
        jobDescVo.setTags(tags);
        jobDescVo.setPublishTime(publishTime);//今天加上那个小时，分钟
        jobDescVo.setPublishFrom(publishFrom);
        jobDescVo.setJobAdvantages(Arrays.asList(advantages));
        jobDescVo.setJobResponsibilities(jobResponsities);
        jobDescVo.setJobRequirement(jobRequirement);
        jobDescVo.setWorkAddress(workAddress);

        logger.info("jobDescVo:" + jobDescVo.toString());
        return jobDescVo;
    }


}
