package com.song.sojob.jobdesc.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by 001844 on 2018/1/16.
 * job desc
 */
public class JobDescVo {
    private String jdUrl;

/* <div class="company">
    PPmoney互联网金融平台技术部招聘
            </div>*/
    private String company;

    private String jobName;

    private String salary;

    private String city;

    private String experience;

    private String educationalRequirements;
    /*全职，兼职等*/
    private String jobType;

    private List<String> tags;

    private Date publishTime;
    /*拉钩，51job*/
    private String publishFrom;

    private List<String> jobAdvantages;

    private List<String> jobResponsibilities;

    private List<String> jobRequirement;

    private String workAddress;


    public String getJdUrl() {
        return jdUrl;
    }

    public void setJdUrl(String jdUrl) {
        this.jdUrl = jdUrl;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducationalRequirements() {
        return educationalRequirements;
    }

    public void setEducationalRequirements(String educationalRequirements) {
        this.educationalRequirements = educationalRequirements;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishFrom() {
        return publishFrom;
    }

    public void setPublishFrom(String publishFrom) {
        this.publishFrom = publishFrom;
    }

    public List<String> getJobAdvantages() {
        return jobAdvantages;
    }

    public void setJobAdvantages(List<String> jobAdvantages) {
        this.jobAdvantages = jobAdvantages;
    }

    public List<String> getJobResponsibilities() {
        return jobResponsibilities;
    }

    public void setJobResponsibilities(List<String> jobResponsibilities) {
        this.jobResponsibilities = jobResponsibilities;
    }

    public List<String> getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(List<String> jobRequirement) {
        this.jobRequirement = jobRequirement;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    @Override
    public String toString() {
        return this.toString();
    }
}
