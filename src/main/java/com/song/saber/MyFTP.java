package com.song.saber;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by 00013708 on 2017/11/6.
 */
public class MyFTP {
    private BufferedReader reader;
    private BufferedWriter writer;

    public MyFTP() {

    }

    public void connect(String host) {
        connect(host, 21);
    }

    /**
     * 这个socket只用作传输命令
     * 要返回连接的结果
     * @param host
     * @param port
     */
    public void connect(String host, int port) {
        Socket socket = new Socket();
        SocketAddress address = new InetSocketAddress(host, port);
        try {
            socket.connect(address, 1000 * 180);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
//
            reader = new BufferedReader(new InputStreamReader(in));
            writer = new BufferedWriter(new OutputStreamWriter(out));
            int readChar;
//            String line;
            StringBuilder sb = new StringBuilder();
            while ((readChar = reader.read()) != -1) {
                System.out.println((char) readChar);
                sb.append((char)readChar);
            }
            int replyCode = Integer.valueOf(sb.substring(0,3));
//            if(replyCode>=200&&replyCode<=){
//
//            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    socket = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getReplay() throws IOException {
        return reader.readLine();
    }

    public void sendCMD(String cmd,String arg) throws IOException {
//        writer.write();
        writer.flush();
    }

    public void login() {


    }

    public void retrieveFile() {

    }

    public void storeFile() {

    }

    public static void main(String[] args) {
        String server = "198.74.48.82";
        int port = 21;
        String username = "sx";
        String password = "sx198942";
        MyFTP ftp = new MyFTP();
    }
}
