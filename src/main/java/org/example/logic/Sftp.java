package org.example.logic;

import com.jcraft.jsch.*;
import org.example.config.AppConfigurator;
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

    public final Path appTempIODir;
    public final static Integer PORT = 22; // статично

    private final AppConfigurator appConfigurator;

    @Autowired
    public Sftp(AppConfigurator appConfigurator) {
        this.appConfigurator = appConfigurator;
        this.appTempIODir = Paths.get(System.getProperty("java.io.tmpdir"), appConfigurator.getSettings().getServiceName());
    }

    public void download(String host, String regionName) {


        Session jschSession = null;
        ChannelSftp channelSftp = null;

        try{
            jschSession = getPreparedSession(host);

            System.out.println("Сессия открывается с: " + host);
            jschSession.connect(appConfigurator.getSettings().getConnectTimeoutMilliSec());

            channelSftp = (ChannelSftp) jschSession.openChannel("sftp");
            channelSftp.connect();

            createFolderByRegionName(regionName);

            // перебираются директории пока не будет найден хотя бы один файл, либо пока список директорий не закончится
            for(String remoteFolder: appConfigurator.getSettings().getRemoteFolders()) {
                System.out.println("Попытка для директории: " + remoteFolder);
                boolean atLeastOneFileHasBeenDownloaded = downloadAllFiles(remoteFolder, regionName, channelSftp);

                if(atLeastOneFileHasBeenDownloaded) {
                    return;
                }
                System.out.println("Директория указана неверно либо в директории не оказалось ни одного файла: " + remoteFolder);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage() + " - проблема с: " + host);
        } finally {
            if(channelSftp != null) {
                channelSftp.exit();
            }
            if(jschSession != null) {
                jschSession.disconnect();
                System.out.println("Сессия закрыта для: " + host);
            }
        }

    }

    private void downloadFile(String remoteDirectory, String localPath, String regionName, String fileName, ChannelSftp channelSftp) throws SftpException {
        String destinationLocal = localPath + File.separator + regionName + File.separator + fileName;
        String remoteLocal = remoteDirectory + fileName;

        System.out.println("Начало загрузки файла: " + fileName);
        channelSftp.get(remoteLocal , destinationLocal);
        System.out.println("Файл: " + fileName + " загружен успешно!");
    }

    public void createFolderByRegionName(String regionName) throws IOException {
        Files.createDirectories(Paths.get(appTempIODir + File.separator + regionName));
    }

    public Session getPreparedSession(String host) throws JSchException {
        //TODO вынести создание объекта
        JSch jsch = new JSch();
        // использовать в случае если потребуется заходить по ключу
        // jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
        Session jschSession = jsch.getSession(appConfigurator.getSettings().getUsernameSSH(), host, PORT);
        jschSession.setPassword(appConfigurator.getSettings().getPasswordSSH());

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        jschSession.setConfig(config);
        return jschSession;
    }


    /**
     *
     * Метод перебирает все заданные названия файлов в случае если ни один файл не был скачен возвращает false
     *
     * @param remoteFolder
     * @param regionName
     * @param channelSftp
     *
     * @return
     */

    public boolean downloadAllFiles(String remoteFolder, String regionName, ChannelSftp channelSftp) {
        int counterOfFails = 0;
        for(String fileName : appConfigurator.getSettings().getFileNames()) {
            try{
                downloadFile(remoteFolder, appTempIODir.toString(), regionName, fileName, channelSftp);
                counterOfFails++;
            } catch (Exception e) {
                System.out.println(e.getMessage() + " - проблема с загрузкой файла: " + fileName);
            }
        }

        if(counterOfFails == 0) {
            return false;
        }

        return true;
    }




}
