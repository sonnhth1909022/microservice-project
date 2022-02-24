package com.microservice.inventoryservice.controller;

import com.microservice.inventoryservice.service.ImportExportService;
import com.microservice.inventoryservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/history/")
public class HistoryController {

    @Autowired
    private ImportExportService importExportService;

    @GetMapping("list")
    public ResponseEntity<?> getAllImportExportHistories(){
        return new ResponseEntity<>(RESTResponse.success((importExportService.getAllHistories())
                ,"get all import and export history successful!"), HttpStatus.OK);
    }
}
