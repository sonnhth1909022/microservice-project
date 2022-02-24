package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.ImportExportHistory;

public interface ImportExportService {
    ImportExportHistory saveHistory(ImportExportHistory history);
}
