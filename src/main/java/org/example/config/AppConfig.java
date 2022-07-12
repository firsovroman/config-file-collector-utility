package org.example.config;


import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class AppConfig {

    public String serviceName;

    public String remoteFolder;
    public String usernameSftp;
    public String passwordSftp;

    public ArrayList<String> fileNames;
    public HashMap<String,String> regions;

    public AppConfig() {
    }

    public AppConfig readSettings() {

        //TODO(заменить абсолютный путь на относительный)
        try(FileReader fileReader = new FileReader("C:\\Users\\Roman\\IdeaProjects\\config-file-updater\\src\\main\\resources\\settings.json")) {

            Gson gson = new Gson();
            return gson.fromJson(fileReader, AppConfig.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalArgumentException("");

    }

    public String getServiceName() {
        return serviceName;
    }

    public String getRemoteFolder() {
        return remoteFolder;
    }

    public String getUsernameSftp() {
        return usernameSftp;
    }

    public String getPasswordSftp() {
        return passwordSftp;
    }

    public ArrayList<String> getFileNames() {
        return fileNames;
    }

    public HashMap<String, String> getRegions() {
        return regions;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "serviceName='" + serviceName + '\'' +
                ", remoteFolder='" + remoteFolder + '\'' +
                ", usernameSftp='" + usernameSftp + '\'' +
                ", passwordSftp='" + passwordSftp + '\'' +
                ", fileNames=" + fileNames +
                ", regions=" + regions +
                '}';
    }
}