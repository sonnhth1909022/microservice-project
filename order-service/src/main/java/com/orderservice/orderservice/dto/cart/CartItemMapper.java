package com.orderservice.orderservice.dto.cart;

import com.orderservice.orderservice.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CartItemMapper {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    //CartItemDto mappers
    CartItem cartItemDtoToCartItem(CartItemDto cartItemDto);
    CartItemDto cartItemToCartItemDto(CartItem cartItem);
}
