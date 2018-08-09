package com.song.bookonline;

/**
 * Created by 001844 on 2018/4/2.
 */
public class ChapterVo {

    private Integer chapterNo;//第一回就是1，这个用来做html最后一个序号的，比如http://domain/武侠/射雕英雄传/1.html或者wuxia/sdyxz/1.html
    private String chapterTitle;
    private String chapterContent;

    private String author;//作者，金庸
    private String category;//分类，武侠
    private String title;//书名，作品名字

    public ChapterVo() {
    }

    public Integer getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(Integer chapterNo) {
        this.chapterNo = chapterNo;
    }


    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
