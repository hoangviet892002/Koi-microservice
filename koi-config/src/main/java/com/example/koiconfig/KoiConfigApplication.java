package com.example.koiconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;


@EnableConfigServer
@SpringBootApplication
public class KoiConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(KoiConfigApplication.class, args);
    }

}
