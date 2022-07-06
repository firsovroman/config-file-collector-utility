package org.example.config;

import org.springframework.stereotype.Component;


@Component
public class AppConfigurator {

    private final AppConfig appConfig;

    public AppConfigurator(AppConfig appConfig) {
        this.appConfig = new AppConfig().readSettings();
    }

    public AppConfig getSettings() {
        return appConfig;
    }
}
