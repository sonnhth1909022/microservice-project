package com.orderservice.orderservice.dto.cart;

import com.orderservice.orderservice.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    //CartDto mappers
    Cart cartDtoToCart(CartDto cartDto);
    CartDto cartToCartDto(Cart cart);
    Set<CartDto> lsCartToCartDto(Set<Cart> carts);
}
