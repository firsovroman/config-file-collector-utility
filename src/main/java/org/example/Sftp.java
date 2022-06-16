package org.example;

import com.jcraft.jsch.*;
import org.example.config.RequisitesConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Component
public class Sftp {

    public final static String APP_ID = "config-file-updater";
    public static final Path APP_IO_TMPDIR = Paths.get(System.getProperty("java.io.tmpdir"), APP_ID);
    public final static Integer PORT = 22; // статично

    RequisitesConfigurator requisitesConfigurator;

    @Autowired
    public Sftp(RequisitesConfigurator requisitesConfigurator) {
        this.requisitesConfigurator = requisitesConfigurator;
    }

    public void download(String host, String regionName) {


        Session jschSession = null;
        ChannelSftp channelSftp = null;

        try{
            jschSession = getPreparedSession(host);

            System.out.println("Session opening with: " + host);
            jschSession.connect();

            channelSftp = (ChannelSftp) jschSession.openChannel("sftp");
            channelSftp.connect();

            createFolderByRegionName(regionName);

            for(String fileName : requisitesConfigurator.getRequisitesConfig().getFileNames()) {
                try{
                    downloadFile(requisitesConfigurator.getRequisitesConfig().getRemoteFolder(), APP_IO_TMPDIR.toString(), regionName, fileName, channelSftp);
                } catch (Exception e) {
                    System.out.println(e.getMessage() + " - problem with: " + fileName);
                }
            }

        } catch (Exception e) {
            System.err.println(e.getMessage() + " - problem with: " + host);
        } finally {
            if(channelSftp != null) {
                channelSftp.exit();
            }
            if(jschSession != null) {
                jschSession.disconnect();
                System.out.println("Session closed  with: " + host);
            }
        }

    }

    private void downloadFile(String remoteDirectory, String localPath, String regionName, String fileName, ChannelSftp channelSftp) throws SftpException {
        String destinationLocal = localPath + File.separator + regionName + File.separator + fileName;
        String remoteLocal = remoteDirectory + fileName;

        System.out.println("Start downloading File: " + fileName);
        channelSftp.get(remoteLocal , destinationLocal);
        System.out.println("File: " + fileName + " download success!");
    }

    public void createFolderByRegionName(String regionName) throws IOException {
        Files.createDirectories(Paths.get(APP_IO_TMPDIR + File.separator + regionName));
    }

    public Session getPreparedSession(String host) throws JSchException {
        JSch jsch = new JSch();
        //        jsch.setKnownHosts("/Users/john/.ssh/known_hosts"); // в случае если потребется заходиь по ключу
        Session jschSession = jsch.getSession(requisitesConfigurator.getRequisitesConfig().getUsernameSftp(), host, PORT);
        jschSession.setPassword(requisitesConfigurator.getRequisitesConfig().getPasswordSftp());

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        jschSession.setConfig(config);
        return jschSession;
    }


}
