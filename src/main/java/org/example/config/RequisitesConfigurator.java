package org.example.config;

import org.example.model.RequisitesConfig;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.Properties;

@Configuration
public class RequisitesConfigurator {

    private final RequisitesConfig requisitesConfig;

    public RequisitesConfigurator() throws MalformedURLException {
        File propFile = new File("C:\\Users\\Roman\\IdeaProjects\\config-file-updater\\src\\main\\resources\\requisites.properties");
        Properties prop = load(propFile);
        this.requisitesConfig = new RequisitesConfig().readProps(prop);
    }

    public static Properties load(File propsFile) {
        Properties props = new Properties();

        try(FileInputStream fis = new FileInputStream(propsFile);){
            props.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return props;
    }

    public RequisitesConfig getRequisitesConfig() {
        return requisitesConfig;
    }
}
