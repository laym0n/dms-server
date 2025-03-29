package com.victor.kochnev.dmsserver;

import com.victor.kochnev.dmsserver.application.DmsApplication;
import com.victor.kochnev.dmsserver.configuration.LocalDevTestcontainersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DmsLocalDevApplication {
    public static void main(String[] args) {
        SpringApplication.from(DmsApplication::main)
                .with(LocalDevTestcontainersConfiguration.class)
                .run(args);
    }
}
