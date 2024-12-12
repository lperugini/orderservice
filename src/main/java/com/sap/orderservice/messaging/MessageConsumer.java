package com.sap.orderservice.messaging;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.orderservice.model.Order;
import com.sap.orderservice.model.OrderRepo;

@Service
public class MessageConsumer {

    @Autowired
    private OrderRepo repository;

    // Questo metodo ascolta i messaggi in arrivo nella coda 'orderQueue'
    @RabbitListener(queues = MessageConfig.QUEUE_NAME)
    public void consumeNewOrder(String message) {
        System.out.println("Ricevuto messaggio: " + message);

        JSONObject jsonObject = new JSONObject(message);
        Order newOrder = new Order(jsonObject.getLong("customerCode"), jsonObject.getString("productCode"));
        Order order = newOrder;

        if (jsonObject.has("id")) {
            Long id = jsonObject.getLong("id");
            Optional<Order> optionalOrder = repository.findById(id);

            if (optionalOrder.isPresent()) {
                order = optionalOrder.get();
                order.setCustomer(newOrder.getCustomer());
                order.setProduct(newOrder.getProduct());
            }
        } 
        
        repository.save(order);
        System.out.println("Parsed: " + jsonObject.toString());
    }
}
