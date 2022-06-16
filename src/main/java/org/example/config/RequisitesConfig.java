package org.example.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class RequisitesConfig {

    private List<String> fileNames;
    private String remoteFolder;
    private String usernameSftp;
    private String passwordSftp;

    public RequisitesConfig readProps(Environment environment) {
        String fileNamesString = environment.getProperty("file_names");
        String[] file_names = Objects.requireNonNull(fileNamesString).split("\\s");
        this.fileNames = Arrays.stream(file_names).filter(s -> !"".equals(s.trim())).collect(Collectors.toList());
        this.remoteFolder = environment.getProperty("remote_folder");
        this.usernameSftp = environment.getProperty("username_sftp");
        this.passwordSftp = environment.getProperty("password_sftp");
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

    public String getUsernameSftp() {
        return usernameSftp;
    }

    public void setUsernameSftp(String usernameSftp) {
        this.usernameSftp = usernameSftp;
    }

    public String getPasswordSftp() {
        return passwordSftp;
    }

    public void setPasswordSftp(String passwordSftp) {
        this.passwordSftp = passwordSftp;
    }


    @Override
    public String toString() {
        return "RequisitesConfig{" +
                "fileNames=" + fileNames +
                ", remoteFolder='" + remoteFolder + '\'' +
                ", usernameSftp='" + usernameSftp + '\'' +
                ", passwordSftp='" + passwordSftp + '\'' +
                '}';
    }
}
