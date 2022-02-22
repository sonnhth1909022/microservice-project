package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.Provider;

import java.util.List;
import java.util.Optional;

public interface ProviderService {
    Provider saveProvider(Provider provider);
    Optional<Provider> findProviderById(long id);
    void deleteProviderById(long id);
    List<Provider> getAllProviders();
}
