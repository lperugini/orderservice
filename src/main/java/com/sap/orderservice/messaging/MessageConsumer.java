package com.sap.orderservice.messaging;

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
        JSONObject jsonObject = new JSONObject(message);

        Order newOrder = new Order(
                jsonObject.getLong("userId"),
                jsonObject.getLong("itemId"),
                jsonObject.getString("description"),
                jsonObject.getDouble("price"));
        
        repository.save(newOrder);
    }
}
