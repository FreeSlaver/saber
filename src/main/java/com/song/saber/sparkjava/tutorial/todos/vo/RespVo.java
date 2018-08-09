package com.sparkjava.tutorial.todos.vo;

import com.alibaba.fastjson.JSON;

import java.util.Date;

/**
 * Created by 00013708 on 2017/6/21.
 */
public class RespVo {

    private int code;

    private String msg;

    private Date timestamp;

    public RespVo() {
        this.code = 0;
        this.msg = "success";
        this.timestamp = new Date();
    }

    public RespVo(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = new Date();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
