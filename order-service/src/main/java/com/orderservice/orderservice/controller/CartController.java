package com.orderservice.orderservice.controller;

import com.orderservice.orderservice.dto.cart.CartItemDto;
import com.orderservice.orderservice.entity.CartItem;
import com.orderservice.orderservice.entity.Product;
import com.orderservice.orderservice.entity.User;
import com.orderservice.orderservice.service.CartService;
import com.orderservice.orderservice.service.ProductService;
import com.orderservice.orderservice.service.UserService;
import com.orderservice.orderservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/cart/")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @PostMapping("add")
    public ResponseEntity<?> addToCart(@RequestHeader String accessToken, @RequestBody CartItemDto cartItemDto){
        Optional<Product> product = productService.findProductById(cartItemDto.getProductId());
        Optional<User> user = userService.findUserById(accessToken);
        if(user.isPresent()){
            if (product.isPresent()){
                CartItem cartItem = new CartItem();
                cartItem.setProductId(cartItemDto.getProductId());
                cartItem.setProductName(product.get().getName());
                cartItem.setUnitPrice(product.get().getPrice());
                cartItem.setQuantity(cartItemDto.getQuantity());
                return new ResponseEntity<>(RESTResponse.success(cartService.addToCart(cartItem)
                        ,"Add to Cart successful"), HttpStatus.OK);
            }
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("Product not found!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("You must login to add item to cart!")
                .build(), HttpStatus.NOT_FOUND);
    }


}
