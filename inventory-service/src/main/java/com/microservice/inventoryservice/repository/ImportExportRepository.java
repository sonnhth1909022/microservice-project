package com.microservice.inventoryservice.repository;

import com.microservice.inventoryservice.entity.ImportExportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportExportRepository extends JpaRepository<ImportExportHistory, Long> {
    List<ImportExportHistory> findAllByOrderId(String orderId);
    void deleteAllByOrderId(String orderId);
}
