package org.example;

import org.example.model.RegionsAndHosts;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class Processor {

    public final static String APP_ID = "config-file-updater";
    public static final Path APP_IO_TMPDIR = Paths.get(System.getProperty("java.io.tmpdir"), APP_ID);

    private final Sftp sftp;
    private final RegionsAndHosts regionsAndHosts;

    public Processor(Sftp sftp, RegionsAndHosts regionsAndHosts) {
        this.sftp = sftp;
        this.regionsAndHosts = regionsAndHosts;
    }

    public void execute() throws IOException {

        Files.createDirectories(APP_IO_TMPDIR);

        for (Map.Entry<String, String> entry : regionsAndHosts.getMap().entrySet()) {
            sftp.download(entry.getValue(), entry.getKey());
        }

    }


}
