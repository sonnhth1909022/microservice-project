package com.orderservice.orderservice.queue;

import com.orderservice.orderservice.common.events.PaymentEvent;
import com.orderservice.orderservice.dto.order.OrderDto;
import com.orderservice.orderservice.entity.Order;
import com.orderservice.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Log4j2
public class ConsumerService {

    @Autowired
    private OrderService orderService;

    @Transactional
    void handlerMessagePayment(PaymentEvent paymentEvent){
            Order checkOrder = orderService.findByOrderIdForRabbit(paymentEvent.getOrderId());
            if(checkOrder != null){
                checkOrder.setPaymentStatus(paymentEvent.getPaymentStatus());
                orderService.saveOrder(checkOrder);
            }
            log.error("Order not found!");
    }
}
