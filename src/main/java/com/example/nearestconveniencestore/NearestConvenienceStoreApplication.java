package com.example.nearestconveniencestore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NearestConvenienceStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(NearestConvenienceStoreApplication.class, args);
    }

}
