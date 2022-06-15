package org.example;


import org.example.config.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class ConfigFileUpdaterApplication {


    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Processor processor = context.getBean("processor" , Processor.class);
        processor.execute();

    }
}

