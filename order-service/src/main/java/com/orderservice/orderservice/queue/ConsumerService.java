package com.orderservice.orderservice.queue;

import com.orderservice.orderservice.common.events.OrderEvent;
import com.orderservice.orderservice.entity.Order;
import com.orderservice.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.orderservice.orderservice.queue.Config.QUEUE_INVENTORY;
import static com.orderservice.orderservice.queue.Config.QUEUE_PAYMENT;

@Service
@Log4j2
public class ConsumerService {

    @Autowired
    private OrderService orderService;

    @Transactional
    void handlerPaymentAndInventoryMessages(OrderEvent orderEvent){
            Order checkOrder = orderService.findByOrderIdForRabbit(orderEvent.getOrderId());
            if(checkOrder == null){
                log.error("Order not found!");
                return;
            }
            if(orderEvent.getQueueName().equals(QUEUE_PAYMENT)){
                checkOrder.setPaymentStatus(orderEvent.getPaymentStatus());
                orderService.saveOrder(checkOrder);
            }
            if(orderEvent.getQueueName().equals(QUEUE_INVENTORY)){
                checkOrder.setInventoryStatus(orderEvent.getInventoryStatus());
                orderService.saveOrder(checkOrder);
            }

    }
}
