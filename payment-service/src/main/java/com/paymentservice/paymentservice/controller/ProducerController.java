package com.paymentservice.paymentservice.controller;

import com.paymentservice.paymentservice.ulti.RESTResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.paymentservice.paymentservice.queue.Config.*;

@RestController
@RequestMapping("api/v1/payment/producer/")
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("message")
    public ResponseEntity<?> sendMessage(){
        String message = "Hello Payment!";
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_PAYMENT, "payment message: " + message);
        return new ResponseEntity<>(RESTResponse.success(message
                ,"Send message to queue successful!"), HttpStatus.OK);
    }
}
