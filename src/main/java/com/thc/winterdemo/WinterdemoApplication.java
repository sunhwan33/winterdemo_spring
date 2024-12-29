package com.thc.winterdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication

public class WinterdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinterdemoApplication.class, args);
    }

}
