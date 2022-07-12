package org.example.logic;

import org.example.config.AppConfigurator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class Processor {

    private final Sftp sftp;
    private final AppConfigurator appConfigurator;

    public Processor(Sftp sftp, AppConfigurator appConfigurator) {
        this.sftp = sftp;
        this.appConfigurator = appConfigurator;
    }

    public void execute() throws IOException {

        Path appTempIODir = Paths.get(System.getProperty("java.io.tmpdir"), appConfigurator.getSettings().getServiceName());
        Files.createDirectories(appTempIODir);

        for (Map.Entry<String, String> entry : appConfigurator.getSettings().getRegions().entrySet()) {
            sftp.download(entry.getValue(), entry.getKey());
        }

    }


}
