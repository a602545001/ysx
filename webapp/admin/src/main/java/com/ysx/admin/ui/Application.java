package com.ysx.admin.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "com.ysx.**" })
@SpringBootApplication
public class Application extends SpringBootServletInitializer  {
    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }
}