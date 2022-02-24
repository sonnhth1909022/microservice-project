package com.microservice.inventoryservice.controller;

import com.microservice.inventoryservice.dto.inventory.InventoryDto;
import com.microservice.inventoryservice.dto.inventory.InventoryMapper;
import com.microservice.inventoryservice.entity.ImportExportHistory;
import com.microservice.inventoryservice.entity.Inventory;
import com.microservice.inventoryservice.entity.Provider;
import com.microservice.inventoryservice.enums.InventoryType;
import com.microservice.inventoryservice.service.ImportExportService;
import com.microservice.inventoryservice.service.InventoryService;
import com.microservice.inventoryservice.service.ProviderService;
import com.microservice.inventoryservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/inventory/")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private ImportExportService importExportService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private InventoryMapper inventoryMapper;


    @GetMapping("list")
    public ResponseEntity<?> getAllInventoryProducts(){
        return new ResponseEntity<>(RESTResponse.success(inventoryMapper.INSTANCE
                        .lsInventoryToInventoryDto(inventoryService.getAllInventory())
                ,"get all inventory products successful!"), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> addProductToInventory(@RequestBody InventoryDto inventoryDto){
        Inventory inventory = new Inventory();
        Optional<Provider> provider = providerService.findProviderById(inventoryDto.getProviderId());
        if(provider.isPresent()){
            inventory.setProductId(inventoryDto.getProductId());
            inventory.setStockQuantity(inventoryDto.getStockQuantity());
            inventory.setProviderId(provider.get().getId());
            inventoryService.saveInventory(inventory);
            return new ResponseEntity<>(RESTResponse.success(inventoryDto
                    ,"add product inventory successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Provider Id not found!")
                .build(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("update/stock")
    public ResponseEntity<?> updateStock(@RequestParam long productId, @RequestParam int quantity){
        Optional<Inventory> inventory = inventoryService.findInventoryByProductId(productId);
        if(inventory.isPresent()){
            if(quantity <= 0){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .badRequestWithMessage("quantity must be greater 0!")
                        .build(), HttpStatus.BAD_REQUEST);
            }
            inventory.get().setStockQuantity(quantity + inventory.get().getStockQuantity());
            inventoryService.saveInventory(inventory.get());

            ImportExportHistory history = new ImportExportHistory();
            history.setProductId(inventory.get().getProductId());
            history.setProviderId(inventory.get().getProviderId());
            history.setQuantity(quantity);
            history.setType(InventoryType.IMPORT.name());
            history.setOrderId("NONE");
            importExportService.saveHistory(history);


            return new ResponseEntity<>(RESTResponse.success(quantity
                    ,"update stock successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Product not found!")
                .build(), HttpStatus.NOT_FOUND);
    }

}
