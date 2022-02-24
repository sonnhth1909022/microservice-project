package com.microservice.inventoryservice.queue;

import com.microservice.inventoryservice.common.events.OrderDetailEvent;
import com.microservice.inventoryservice.common.events.OrderEvent;
import com.microservice.inventoryservice.entity.ImportExportHistory;
import com.microservice.inventoryservice.entity.Inventory;
import com.microservice.inventoryservice.enums.InventoryStatus;
import com.microservice.inventoryservice.enums.InventoryType;
import com.microservice.inventoryservice.service.ImportExportService;
import com.microservice.inventoryservice.service.InventoryService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.microservice.inventoryservice.queue.Config.*;

@Service
public class ConsumerService {

    public static Set<OrderDetailEvent> tempReturnItems = new HashSet<>();

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ImportExportService importExportService;

    @Transactional
    void handleInventoryCheck(OrderEvent orderEvent) {
        orderEvent.setQueueName(QUEUE_INVENTORY);
        if (orderEvent.getInventoryStatus().equals(InventoryStatus.PENDING.name())) {
            handleInventoryPending(orderEvent);
        }
        if (orderEvent.getInventoryStatus().equals(InventoryStatus.OUT_OF_STOCK.name())) {
            handleInventoryOutOfStock(orderEvent);
        }
    }

    @Transactional
    void handleInventoryPending(OrderEvent orderEvent) {
        Set<OrderDetailEvent> orderItems = orderEvent.getOrderItems();
        for (OrderDetailEvent item : orderItems) {
            Optional<Inventory> inventory = inventoryService.findInventoryByProductId(item.getProductId());
            if (inventory.isPresent()) {
                if (inventory.get().getStockQuantity() < item.getQuantity()) {
                    orderEvent.setMessage("Out of stock!");
                    orderEvent.setInventoryStatus(InventoryStatus.OUT_OF_STOCK.name());
                    return;
                }
                inventory.get().setStockQuantity(inventory.get().getStockQuantity() - item.getQuantity());
                inventoryService.saveInventory(inventory.get());
                tempReturnItems.add(item);
            }
        }

        if (!orderEvent.getInventoryStatus().equals(InventoryStatus.OUT_OF_STOCK.name())) {
            orderEvent.setMessage("Inventory Check Successful!");
            orderEvent.setInventoryStatus(InventoryStatus.SUCCESS.name());
            Set<OrderDetailEvent> exportedItems = orderEvent.getOrderItems();
            for (OrderDetailEvent item: exportedItems){
                Optional<Inventory> inventory = inventoryService.findInventoryByProductId(item.getProductId());
                if (inventory.isPresent()){
                    ImportExportHistory exportHistory = new ImportExportHistory();
                    exportHistory.setOrderId(orderEvent.getOrderId());
                    exportHistory.setProductId(item.getProductId());
                    exportHistory.setProviderId(inventory.get().getProviderId());
                    exportHistory.setQuantity(item.getQuantity());
                    exportHistory.setType(InventoryType.EXPORT.name());
                    importExportService.saveHistory(exportHistory);
                }
            }
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_ORDER, orderEvent);
        }

    }

    @Transactional
    void handleInventoryOutOfStock(OrderEvent orderEvent) {
        for (OrderDetailEvent item : tempReturnItems) {
            Optional<Inventory> inventory = inventoryService.findInventoryByProductId(item.getProductId());
            if (inventory.isPresent()) {
                inventory.get().setStockQuantity(item.getQuantity() + inventory.get().getStockQuantity());
                inventoryService.saveInventory(inventory.get());
            }
        }
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_ORDER, orderEvent);
    }
}
