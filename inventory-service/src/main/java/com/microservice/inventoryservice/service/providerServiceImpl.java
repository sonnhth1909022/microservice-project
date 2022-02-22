package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.Provider;
import com.microservice.inventoryservice.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class providerServiceImpl implements ProviderService{

    @Autowired
    private ProviderRepository providerRepository;

    @Override
    public Provider saveProvider(Provider provider) {
        return providerRepository.save(provider);
    }

    @Override
    public Optional<Provider> findProviderById(long id) {
        return providerRepository.findById(id);
    }

    @Override
    public void deleteProviderById(long id) {
        this.providerRepository.deleteById(id);
    }
}
