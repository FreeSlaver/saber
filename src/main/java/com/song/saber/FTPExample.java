package com.song.saber;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;


public class FTPExample {
    /**
     * @param args
     */
    public static void main(String[] args) {
        String server = "198.74.48.82";
        int port = 21;
        String ftpUser = "sx";
        String ftpPwd = "sx198942";

        FTPClient ftp = new FTPClient();

        boolean error = false;
        try {
            int reply;
            //1.connect
            ftp.connect(server);
//            ftp
            System.out.println("Connected to " + server + ".");
            System.out.print(ftp.getReplyString());

            // After connection attempt, you should check the reply code to verify
            // success.
            reply = ftp.getReplyCode();


            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
            //2.login
            boolean loginResult = ftp.login(ftpUser, ftpPwd);
            System.out.println("loginResult:" + loginResult);
            //3.list files
            FTPFile[] files = ftp.listFiles();

            if (files != null && files.length > 0) {
                for (FTPFile file : files) {
                    //4.retrieve files
                    String fileName = file.getName();
                    /*OutputStream out = new FileOutputStream("test" + fileName);
                    boolean retriResult = ftp.retrieveFile(fileName, out);
                    out.flush();
                    out.close();
                    System.out.println(retriResult);*/
                    System.out.println(file.getName());
                }
            }

            // 5.transfer files
            /*File firstLocalFile = new File("D:/task1.org");

            String firstRemoteFile = "/task1.org";
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftp.storeFile(firstRemoteFile, inputStream);
            System.out.println("store file result:"+done);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }*/

            ftp.logout();
        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
            System.exit(error ? 1 : 0);
        }
    }
}
