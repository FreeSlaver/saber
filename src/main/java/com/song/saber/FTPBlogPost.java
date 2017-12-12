package com.song.saber;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 00013708 on 2017/10/13.
 */
public class FTPBlogPost {
    public static void main(String[] args) {
        String server = "qxu1141810261.my3w.com";
        int port = 21;
        String ftpUser = "qxu1141810261FTP";
        String ftpPwd = "sx198942";

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            boolean loginSuccess = ftpClient.login(ftpUser, ftpPwd);
            ftpClient.enterLocalPassiveMode();

//            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File("D:/task1.org");

            String firstRemoteFile = "/myfolder/task1.org";
            InputStream inputStream = new FileInputStream(firstLocalFile);

            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            if (done) {
                System.out.println("The first file is uploaded successfully.");
            }

            // APPROACH #2: uploads second file using an OutputStream
           /* File secondLocalFile = new File("D:/task1.org");
            String secondRemoteFile = "/myfolder";
            InputStream inputStream = new FileInputStream(secondLocalFile);

            System.out.println("Start uploading second file");
            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
            byte[] bytesIn = new byte[4096];
            int read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The second file is uploaded successfully.");
            }*/

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}


