package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.ImportExportHistory;
import com.microservice.inventoryservice.repository.ImportExportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImportExportServiceImpl implements ImportExportService{

    @Autowired
    private ImportExportRepository importExportRepository;

    @Override
    public ImportExportHistory saveHistory(ImportExportHistory history) {
        return importExportRepository.save(history);
    }

    @Override
    public Optional<ImportExportHistory> findExportHistoryByOrderIdAndProductId(String orderId, long productId) {
        return importExportRepository.findByOrderIdAndProductId(orderId, productId);
    }

    @Override
    public List<ImportExportHistory> getAllHistories() {
        return importExportRepository.findAll();
    }

    @Override
    public List<ImportExportHistory> findAllHistoriesByOrderId(String orderId) {
        return importExportRepository.findAllByOrderId(orderId);
    }

    @Override
    public void deleteAllHistoryByOrderId(String orderId) {
        this.importExportRepository.deleteAllByOrderId(orderId);
    }

    @Override
    public void deleteHistoryByOrderIdAndProductId(String orderId, long productId) {
        this.importExportRepository.deleteByOrderIdAndProductId(orderId, productId);
    }
}
