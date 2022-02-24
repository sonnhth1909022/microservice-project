package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.ImportExportHistory;
import com.microservice.inventoryservice.repository.ImportExportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportExportServiceImpl implements ImportExportService{

    @Autowired
    private ImportExportRepository importExportRepository;

    @Override
    public ImportExportHistory saveHistory(ImportExportHistory history) {
        return importExportRepository.save(history);
    }

    @Override
    public List<ImportExportHistory> getAllHistories() {
        return importExportRepository.findAll();
    }

    @Override
    public List<ImportExportHistory> getAllHistoriesByOrderId(String orderId) {
        return importExportRepository.findAllByOrderId(orderId);
    }

    @Override
    public void deleteAllHistoryByOrderId(String orderId) {
        this.importExportRepository.deleteAllByOrderId(orderId);
    }
}
