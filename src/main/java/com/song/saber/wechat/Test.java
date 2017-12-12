package com.song.saber.wechat;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;

/**
 * Created by 00013708 on 2017/10/13.
 */
public class Test {
    public static void main(String[] args){
        FTPClient ftp = new FTPClient();

        FTPClientConfig config = new FTPClientConfig();
//        config.setXXX(YYY); // change required options

        // for example config.setServerTimeZoneId("Pacific/Pitcairn")
//        ftp.configure(config );
        boolean error = false;
        try {
            int reply;
            String server = "qxu1141810261.my3w.com";
            ftp.connect(server);
            System.out.println("Connected to " + server + ".");
            System.out.print(ftp.getReplyString());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();

            if(!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }

            boolean loginResult = ftp.login("qxu1141810261FTP","sx198942");
            // transfer files
            ftp.logout();
        } catch(IOException e) {
            error = true;
            e.printStackTrace();
        } finally {
            if(ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch(IOException ioe) {
                    // do nothing
                }
            }
            System.exit(error ? 1 : 0);
        }
    }
}
