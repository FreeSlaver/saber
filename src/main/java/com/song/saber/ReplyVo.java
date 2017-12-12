package com.song.saber;

/**
 * Created by 00013708 on 2017/11/13.
 */
public class ReplyVo {

    private int replyCode;

    private String replyContent;

    public ReplyVo() {
    }

    public ReplyVo(String replayString) {
        this.replyCode = Integer.valueOf(replayString.substring(0, 3));
        this.replyContent = replayString.substring(3).trim();
    }

    public ReplyVo(int replyCode, String replyContent) {
        this.replyCode = replyCode;
        this.replyContent = replyContent;
    }


    public int getReplyCode() {
        return replyCode;
    }

    public void setReplyCode(int replyCode) {
        this.replyCode = replyCode;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

}
