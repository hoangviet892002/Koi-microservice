package com.example.koiapigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class KoiApiGatewayApplication {


    private final List<String> services = Arrays.asList(
            "user-service",
            "pond-service"
    );
    @Bean
    public RouteLocator deployRoutes(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder custom = builder.routes();
        for (String service : this.services) {
            custom = custom
                    .route(r -> r.path("/api/" + service + "/**")
                            .filters(filter -> filter.rewritePath("/api/" + service + "/(?<path>.*)", "/$\\{path}"))
                            .uri("lb://" + service));
        }
        return custom.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(KoiApiGatewayApplication.class, args);
    }

}
