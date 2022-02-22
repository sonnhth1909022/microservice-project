package com.microservice.inventoryservice.dto.provider;

import com.microservice.inventoryservice.entity.Provider;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ProviderMapper {
    ProviderMapper INSTANCE = Mappers.getMapper(ProviderMapper.class);

    //ProviderDto mappers
    Provider providerDtoToProvider(ProviderDto providerDto);
    ProviderDto providerToProviderDto(Provider provider);

    //ProviderDtoRes mappers
    Provider providerDtoResToProvider(ProviderDtoRes providerDtoRes);
    ProviderDtoRes providerToProviderDtoRes(Provider provider);
    List<ProviderDtoRes> lsProviderToProviderDtoRes(List<Provider> providers);
}
