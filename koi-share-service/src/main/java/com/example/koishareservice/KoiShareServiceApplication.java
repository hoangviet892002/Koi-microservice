package com.example.koishareservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class KoiShareServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiShareServiceApplication.class, args);
    }

}
