package com.example.koiserviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class KoiServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(KoiServiceRegistryApplication.class, args);
	}

}
