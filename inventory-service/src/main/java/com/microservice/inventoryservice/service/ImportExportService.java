package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.ImportExportHistory;

import java.util.List;

public interface ImportExportService {
    ImportExportHistory saveHistory(ImportExportHistory history);
    List<ImportExportHistory> getAllHistories();
    List<ImportExportHistory> getAllHistoriesByOrderId(String orderId);
    void deleteAllHistoryByOrderId(String orderId);
}
