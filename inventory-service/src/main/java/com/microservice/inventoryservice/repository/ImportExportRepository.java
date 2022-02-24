package com.microservice.inventoryservice.repository;

import com.microservice.inventoryservice.entity.ImportExportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImportExportRepository extends JpaRepository<ImportExportHistory, Long> {
}
