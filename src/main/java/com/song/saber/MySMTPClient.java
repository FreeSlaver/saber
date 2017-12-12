package com.song.saber;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by 00013708 on 2017/11/13.
 * 你只要思考逻辑的每一个步骤
 * 没搞清楚每一步，
 */
public class MySMTPClient {

    private static BufferedReader br;
    private static PrintWriter pw;


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        SocketAddress address = new InetSocketAddress("smtp.163.com", 25);
        socket.connect(address, 10 * 1000);

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        br = new BufferedReader(new InputStreamReader(in));
        pw = new PrintWriter(new OutputStreamWriter(out));
        String line = br.readLine();
        System.out.println(line);
        //怎么不断从流中读取数据？
        helo();
        authLogin();
        writeEmail();
        quit();
        //这整个的流程是一个链式调用，先判断返回的值，然后进行下一步操作。
        //很多代码有重复，怎么稍微改下？下一个操作是取决于上一个操作的返回code
    }

    private static void helo() throws IOException {
        flush("helo 182.254.229.224");
        check(250);
    }


    private static void flush(String cmd) {
        pw.write(cmd + "\r\n");
        pw.flush();
    }

    private static void check(int code) throws IOException {
        ReplyVo replyVo = getReply();
        int replyCode = replyVo.getReplyCode();
        String replyContent = replyVo.getReplyContent();
        if (replyCode != code) {
            throw new IOException("failed,code:" + replyCode + ",content:" + replyContent);
        }
    }

    private static void authLogin() throws IOException {
        flush("auth login");
        check(334);
        flush("NTA0MjUyMjYyQDE2My5jb20=");
        check(334);
        flush("ZnVja3VkYW9oYW9k");
        check(235);

    }

    private static void writeEmail() throws IOException {
        //考虑这里是否需要转移?
        flush("mail from: <504252262@163.com>");
        check(250);
        flush("rcpt to: <504252262@qq.com>");
        check(250);
        flush("data");
        check(354);

        pw.write("To:504252262@qq.com\r\n");
        pw.write("From:504252262@163.com\r\n");
        pw.write("Subject: hello world\r\n");
        pw.write("mail body\r\n");
        pw.write("分组交换采用存储转发技术，要发的整块数据成为报文，发送报文前，将教程报文切分一个个更小的等长数据段。然后在数据段前面加上必要的控制信息组成的头部（header），就构成了一个分组（packet）,也称为包、分组的头部称为包头。分组是在因特网传输的数据单元。首部非常重要，包含了诸如目的地址，原地址等信息，才能选择传输路径。\r\n");
        pw.write(".\r\n");
        pw.flush();
        check(250);
    }

    private static void quit() throws IOException {
        flush("quit");
        check(221);
    }

    private static ReplyVo getReply() throws IOException {
        String inLine = br.readLine();
        return new ReplyVo(inLine);
    }

}
