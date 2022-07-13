package org.example;


import org.example.config.MyConfig;
import org.example.logic.Processor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Scanner;

public class ConfigFileUpdaterApplication {


    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Processor processor = context.getBean("processor" , Processor.class);
        processor.execute();

        System.out.println("Script completed! Press Enter for finish!");
        Scanner keyboard = new Scanner(System.in);
        keyboard.nextLine();

    }
}

