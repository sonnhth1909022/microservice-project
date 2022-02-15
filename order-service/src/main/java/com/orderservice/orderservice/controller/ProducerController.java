package com.orderservice.orderservice.controller;

import com.orderservice.orderservice.ulti.RESTResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.orderservice.orderservice.queue.Config.ROUTING_KEY_ORDER;
import static com.orderservice.orderservice.queue.Config.TOPIC_EXCHANGE;

@RestController
@RequestMapping("api/v1/order/producer/")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("message")
    public ResponseEntity<?> sendMessage(){
        String message = "Hello Order!";
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_ORDER, "order message: " + message);
        return new ResponseEntity<>(RESTResponse.success(message
                ,"Send message to queue successful!"), HttpStatus.OK);
    }
}
