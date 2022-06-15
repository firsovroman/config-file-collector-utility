package org.example.model;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Properties;


@Component
public class RequisitesConfig {

    private List<String> fileNames;
    private String remoteFolder;
    private String username;
    private String password;

    public RequisitesConfig readProps(Properties properties) {

        String fileNamesString = properties.getProperty("file_names");
        String[] file_names = fileNamesString.split("\\s");
        this.fileNames = Arrays.stream(file_names).filter(s -> !"".equals(s.trim())).collect(Collectors.toList());
        this.remoteFolder = properties.getProperty("remote_folder");
        this.username = properties.getProperty("username");
        this.password = properties.getProperty("password");
        return this;
    }

    public List<String> getFileNames() {
        return fileNames;
    }

    public void setFileNames(List<String> fileNames) {
        this.fileNames = fileNames;
    }

    public String getRemoteFolder() {
        return remoteFolder;
    }

    public void setRemoteFolder(String remoteFolder) {
        this.remoteFolder = remoteFolder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "RequisitesConfig{" +
                "fileNames=" + fileNames +
                ", remoteFolder='" + remoteFolder + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
