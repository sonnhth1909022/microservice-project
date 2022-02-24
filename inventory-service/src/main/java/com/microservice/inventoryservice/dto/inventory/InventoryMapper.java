package com.microservice.inventoryservice.dto.inventory;

import com.microservice.inventoryservice.entity.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface InventoryMapper {
    InventoryMapper INSTANCE = Mappers.getMapper(InventoryMapper.class);

    //InventoryDto mappers
    Inventory inventoryDtoToInventory(InventoryDto inventoryDto);
    InventoryDto inventoryToInventoryDto(Inventory inventory);
    List<InventoryDto> lsInventoryToInventoryDto(List<Inventory> inventories);
}
