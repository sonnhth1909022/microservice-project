package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.ImportExportHistory;

import java.util.List;
import java.util.Optional;

public interface ImportExportService {
    ImportExportHistory saveHistory(ImportExportHistory history);
    Optional<ImportExportHistory> findExportHistoryByOrderIdAndProductId(String orderId, long productId);
    List<ImportExportHistory> getAllHistories();
    List<ImportExportHistory> findAllHistoriesByOrderId(String orderId);
    void deleteAllHistoryByOrderId(String orderId);
    void deleteHistoryByOrderIdAndProductId(String orderId, long productId);
}
