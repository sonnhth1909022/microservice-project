package com.microservice.inventoryservice.controller;

import com.microservice.inventoryservice.dto.provider.ProviderDto;
import com.microservice.inventoryservice.dto.provider.ProviderMapper;
import com.microservice.inventoryservice.entity.Provider;
import com.microservice.inventoryservice.enums.ProviderStatus;
import com.microservice.inventoryservice.service.ProviderService;
import com.microservice.inventoryservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private ProviderMapper providerMapper;

    @GetMapping("list")
    public ResponseEntity<?> getAllProviders(){
        return new ResponseEntity<>(RESTResponse.success(providerMapper.INSTANCE
                        .lsProviderToProviderDtoRes(providerService.getAllProviders())
                ,"get all providers successful!"), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> addProvider(@RequestBody ProviderDto providerDto){
        Provider provider = providerMapper.INSTANCE.providerDtoToProvider(providerDto);
        provider.setStatus(ProviderStatus.ACTIVE.name());
        providerService.saveProvider(provider);
        return new ResponseEntity<>(RESTResponse.success(providerDto
                ,"get all providers successful!"), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateProvider(@RequestParam long id, @RequestBody ProviderDto providerDto){
        Optional<Provider> provider = providerService.findProviderById(id);
        if (provider.isPresent()){
            provider.get().setName(providerDto.getName());
            provider.get().setPhone(providerDto.getPhone());
            provider.get().setAddress(providerDto.getAddress());
            provider.get().setEmail(providerDto.getEmail());
            providerService.saveProvider(provider.get());
            return new ResponseEntity<>(RESTResponse.success(providerDto
                    ,"Update provider successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Provider Id not found!")
                .build(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteProvider(@RequestParam long id){
        Optional<Provider> provider = providerService.findProviderById(id);
        if(provider.isPresent()){
            providerService.deleteProviderById(provider.get().getId());
            return new ResponseEntity<>(new RESTResponse.SuccessNoData()
                    .setMessage("Delete provider successful !")
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Provider Id not found!")
                .build(), HttpStatus.NOT_FOUND);
    }


    @GetMapping("get")
    public ResponseEntity<?> getProviderById(@RequestParam long id){
        Optional<Provider> provider = providerService.findProviderById(id);
        if(provider.isPresent()){
            return new ResponseEntity<>(RESTResponse.success(provider
                    ,"Found a provider with this id!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Provider not found!")
                .build(), HttpStatus.NOT_FOUND);
    }
}
