package com.sap.orderservice.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sap.orderservice.model.Order;
import com.sap.orderservice.model.OrderRepo;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(OrderRepo repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Order(Long.valueOf(1), "The Hobbit")));
            log.info("Preloading " + repository.save(new Order(Long.valueOf(2), "The Lord of the Ring")));
            log.info("Preloading " + repository.save(new Order(Long.valueOf(2), "The Silmarillion")));
        };
    }
}