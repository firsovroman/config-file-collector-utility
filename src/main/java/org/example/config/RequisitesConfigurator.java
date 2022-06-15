package org.example.config;

import org.example.model.RequisitesConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

@Configuration
@PropertySource(value = "classpath:requisites.properties", encoding = "UTF-8", ignoreResourceNotFound = true)
public class RequisitesConfigurator {

    private final RequisitesConfig requisitesConfig;
    private final Environment environment;

    public RequisitesConfigurator(Environment environment) {
        this.environment = environment;
        this.requisitesConfig = new RequisitesConfig().readProps(environment);
    }

    public static Properties load(File propsFile) {
        Properties props = new Properties();

        try(FileInputStream fis = new FileInputStream(propsFile)){
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
