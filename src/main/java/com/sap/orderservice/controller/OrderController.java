package com.sap.orderservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sap.orderservice.exceptions.OrderNotFoundException;
import com.sap.orderservice.model.Order;
import com.sap.orderservice.model.OrderRepo;

@RestController
public class OrderController {

    @Autowired
    private OrderRepo repository;
    @Autowired
    private OrderAssembler assembler;

    @GetMapping("/orders")
    CollectionModel<EntityModel<Order>> all() {
        List<EntityModel<Order>> orders = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(orders, linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    /*
     * @PostMapping("/orders")
     * Order newOrder(@RequestBody Order newOrder) {
     * System.out.println("new order");
     * return repository.save(newOrder);
     * }
     */

    // Single item
    @GetMapping("/orders/{id}")
    EntityModel<Order> one(@PathVariable Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return assembler.toModel(order);
    }

    // count item
    @GetMapping("/orders/count")
    void count() {
        System.out.println(repository.count());
    }

    @PutMapping("/orders/{id}")
    Order replaceOrder(@RequestBody Order newOrder, @PathVariable Long id) {
        return repository.findById(id)
                .map(order -> {
                    order.setUser(newOrder.getUser())
                            .setItem(newOrder.getItem())
                            .setPrice(newOrder.getPrice())
                            .setDescription(newOrder.getDescription());
                    return repository.save(order);
                })
                .orElseGet(() -> {
                    return repository.save(newOrder);
                });
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
