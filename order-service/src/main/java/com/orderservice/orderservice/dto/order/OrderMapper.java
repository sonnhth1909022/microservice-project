package com.orderservice.orderservice.dto.order;

import com.orderservice.orderservice.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    //OrderDto mappers
    Order orderDtoToOrder(OrderDto orderDto);
    OrderDto orderToOrderDto(Order order);
    List<OrderDto> lsOrderToOrderDto(List<Order> orders);
}
