package com.song.power.blog.vo;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by 001844 on 2018/1/12.
 * ---
 * layout: post
 * title: 工作上的几点建议-转载
 * category: career
 * tags: 工作 建议
 * keywords: 工作 职业 建议 成长
 * description: 工作上的几点建议：努力，虚心学习，自由，目标，计划，惜时，了解社会，不要害怕贫穷，吃苦耐闹，合作。
 * ---
 */
public class BlogVo {

    private String layout;

    private String title;
    /*这里这个category有一级，二级，三级目录的，需要搞复杂吗？我要做的是什么？*/
    private List<String> category;

    private List<String> tags;

    private List<String> keywords;

    private String description;

    private String content;

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
