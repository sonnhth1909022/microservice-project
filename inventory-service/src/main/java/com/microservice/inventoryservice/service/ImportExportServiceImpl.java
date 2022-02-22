package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.ImportExportHistory;
import com.microservice.inventoryservice.repository.ImportExportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportExportServiceImpl implements ImportExportService{

    @Autowired
    private ImportExportRepository importExportRepository;

    @Override
    public ImportExportHistory saveHistory(ImportExportHistory history) {
        return importExportRepository.save(history);
    }
}
