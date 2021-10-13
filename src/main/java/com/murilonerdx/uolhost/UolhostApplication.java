package com.murilonerdx.uolhost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class UolhostApplication {

    public static void main(String[] args) {
        SpringApplication.run(UolhostApplication.class, args);
    }

}
