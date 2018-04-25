package com.song.saber.wechat;

import java.util.HashSet;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.message.massmessage.MassTextMessage;

/**
 * Created by 00013708 on 2017/6/14.
 */
public class WechatApi {

  public static void main(String[] args){

    //access_token
    String access_token = "";

    //根据openId 群发
    //文本群发
    MassTextMessage textMessage2 = new MassTextMessage("openId 列表群发文本消息");
    textMessage2.setTouser(new HashSet<String>());
    textMessage2.getTouser().add("openid_1");
    textMessage2.getTouser().add("openid_2");
    MessageAPI.messageMassSend(access_token,textMessage2);
    //MessageAPI.mediaUploadnews();

  }
}
