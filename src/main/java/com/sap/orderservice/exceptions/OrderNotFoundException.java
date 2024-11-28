package com.sap.orderservice.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find order " + id);
    }

    public OrderNotFoundException(String id) {
        super("Could not find order " + id);
    }

}