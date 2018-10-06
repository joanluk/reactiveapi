package org.emaginalabs.reactiveapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class ReactiveApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveApiApplication.class, args);
    }
}
