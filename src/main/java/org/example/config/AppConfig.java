package org.example.config;


import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

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

        String inputFilePath = readPathFromConsole();
        try(FileReader fileReader = new FileReader(inputFilePath)) {

            Gson gson = new Gson();
            return gson.fromJson(fileReader, AppConfig.class);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Нажмите Enter для завершения!");
            Scanner keyboard = new Scanner(System.in);
            keyboard.nextLine();
        }

        throw new IllegalArgumentException("");

    }

    public String readPathFromConsole() {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к JSON файлу с настройками: ");

        String enteredString = scanner.nextLine();
        if(StringUtils.isBlank(enteredString)) {
            throw new IllegalArgumentException("Вы ничего не ввели запустите программу заново и будьте внимательны!");
        }
        if(!StringUtils.endsWithIgnoreCase(enteredString, ".json")) {
            throw new IllegalArgumentException("Ожидается путь к файлу формата JSON, попробуйте снова!");
        }
        return enteredString;
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