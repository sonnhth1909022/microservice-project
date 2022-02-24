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

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.microservice.inventoryservice.queue.Config.*;

@Service
public class ConsumerService {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ImportExportService importExportService;

    @Transactional
    void handleInventoryCheck(OrderEvent orderEvent){
        orderEvent.setQueueName(QUEUE_INVENTORY);
        if (orderEvent.getInventoryStatus().equals(InventoryStatus.PENDING.name())){
            handleInventoryPending(orderEvent);
        }
    }

    void handleInventoryPending(OrderEvent orderEvent){
        Set<OrderDetailEvent> orderItems = orderEvent.getOrderItems();
        for (OrderDetailEvent item : orderItems){
            Optional<Inventory> inventory = inventoryService.findInventoryByProductId(item.getProductId());
            if (inventory.isPresent()){
                if(inventory.get().getStockQuantity() < item.getQuantity()){
                    orderEvent.setMessage("Out of stock!");
                    orderEvent.setInventoryStatus(InventoryStatus.OUT_OF_STOCK.name());
                    rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_ORDER, orderEvent);
                    return;
                }
                inventory.get().setStockQuantity(inventory.get().getStockQuantity() - item.getQuantity());

                ImportExportHistory exportHistory = new ImportExportHistory();
                exportHistory.setOrderId(orderEvent.getOrderId());
                exportHistory.setProductId(item.getProductId());
                exportHistory.setProviderId(inventory.get().getProviderId());
                exportHistory.setQuantity(item.getQuantity());
                exportHistory.setType(InventoryType.EXPORT.name());

                importExportService.saveHistory(exportHistory);
                inventoryService.saveInventory(inventory.get());
            }
        }

        if(!orderEvent.getInventoryStatus().equals(InventoryStatus.OUT_OF_STOCK.name())){
            orderEvent.setMessage("Inventory Check Successful!");
            orderEvent.setInventoryStatus(InventoryStatus.SUCCESS.name());
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, ROUTING_KEY_ORDER, orderEvent);
        }

        // Return items quantity to stockQuantity in Inventory when inventory is OUT_OF_STOCK
        // Reason: even though inventory status is set to OUT_OF_STOCK as soon as there is one
        // item not qualify "stockQuantity > quantity", other items are already saved to database.
        // So we need to revert this action by adding it back to stock.
        if(orderEvent.getInventoryStatus().equals(InventoryStatus.OUT_OF_STOCK.name())){
            List<ImportExportHistory> historyItems = importExportService.getAllHistoriesByOrderId(orderEvent.getOrderId());
            for (ImportExportHistory item : historyItems){
                Optional<Inventory> inventory = inventoryService.findInventoryByProductId(item.getProductId());
                if(inventory.isPresent()){
                    inventory.get().setStockQuantity(inventory.get().getStockQuantity() + item.getQuantity());
                    inventoryService.saveInventory(inventory.get());
                }
            }
            importExportService.deleteAllHistoryByOrderId(orderEvent.getOrderId());
        }
    }
}
