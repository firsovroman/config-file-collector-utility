package org.example.config;


import com.google.gson.Gson;
import org.example.ConfigFileUpdaterApplication;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;
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
        //для консольного тестирования "C:\\Users\\Roman\\IdeaProjects\\config-file-updater\\src\\main\\resources\\settings.json"

        String inputFilePath = getPath();
        try(FileReader fileReader = new FileReader(inputFilePath)) {

            Gson gson = new Gson();
            return gson.fromJson(fileReader, AppConfig.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        throw new IllegalArgumentException("");

    }

    /**
     * Читается файл, лежащий в той же папке, что и jar-файл (при запуске)
     *
     * @return inputFilePath  путь до settings.json
     */
    private String getPath() {
        String fileName = "settings.json";
        File jarFile = null;
        try {
            jarFile = new File(ConfigFileUpdaterApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String inputFilePath = jarFile.getParent() + File.separator + fileName;
        return inputFilePath;
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