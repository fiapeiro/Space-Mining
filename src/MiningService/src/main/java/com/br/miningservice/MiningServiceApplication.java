package com.br.miningservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MiningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiningServiceApplication.class, args);
    }

}
