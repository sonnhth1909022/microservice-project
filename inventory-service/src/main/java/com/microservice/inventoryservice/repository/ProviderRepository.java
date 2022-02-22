package com.microservice.inventoryservice.repository;

import com.microservice.inventoryservice.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
