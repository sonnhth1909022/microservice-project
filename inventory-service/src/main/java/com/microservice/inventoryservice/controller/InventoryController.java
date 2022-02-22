package com.microservice.inventoryservice.controller;

import com.microservice.inventoryservice.dto.inventory.InventoryDto;
import com.microservice.inventoryservice.entity.Inventory;
import com.microservice.inventoryservice.service.InventoryService;
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


    @GetMapping("list")
    public ResponseEntity<?> getAllInventoryProducts(){
        return new ResponseEntity<>(RESTResponse.success(inventoryService.getAllInventory()
                ,"get all inventory products successful!"), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> createProduct(@RequestBody InventoryDto inventoryDto){
        Inventory inventory = new Inventory();
        inventory.setProductId(inventoryDto.getProductId());
        inventory.setStockQuantity(inventoryDto.getStockQuantity());
        inventoryService.saveInventory(inventory);
        return new ResponseEntity<>(RESTResponse.success(inventory
                ,"add inventory successful!"), HttpStatus.OK);
    }

    @PutMapping("update/stock")
    public ResponseEntity<?> updateStock(@RequestParam long productId, @RequestParam int stockQuantity){
        Optional<Inventory> inventory = inventoryService.findInventoryByProductId(productId);
        if(inventory.isPresent()){
            if(stockQuantity <= 0){
                return new ResponseEntity<>(new RESTResponse.Error()
                        .badRequestWithMessage("quantity must be greater 0!")
                        .build(), HttpStatus.BAD_REQUEST);
            }
            inventory.get().setStockQuantity(stockQuantity);
            inventoryService.saveInventory(inventory.get());
            return new ResponseEntity<>(RESTResponse.success(inventory
                    ,"update stock successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Product not found!")
                .build(), HttpStatus.NOT_FOUND);
    }

}
