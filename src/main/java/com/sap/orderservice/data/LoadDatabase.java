package com.sap.orderservice.data;

import com.sap.orderservice.model.Order;
import com.sap.orderservice.model.OrderRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(OrderRepo repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Order("Bilbo Baggins", "The Hobbit")));
            log.info("Preloading " + repository.save(new Order("Frodo Baggins", "The Lord of the Ring")));
        };
    }
}