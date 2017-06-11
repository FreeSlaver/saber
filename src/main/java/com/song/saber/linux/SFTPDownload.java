package com.song.saber.linux;

import java.io.IOException;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;

/** This example demonstrates downloading of a file over SFTP from the SSH server. */
public class SFTPDownload {

    public static void main(String[] args)
            throws IOException {
        final SSHClient ssh = new SSHClient();
        ssh.loadKnownHosts();
        ssh.connect("localhost");
        try {
            ssh.authPublickey(System.getProperty("user.name"));
            final SFTPClient sftp = ssh.newSFTPClient();
            try {
                sftp.get("test_file", new FileSystemFile("/tmp"));
            } finally {
                sftp.close();
            }
        } finally {
            ssh.disconnect();
        }
    }

}
