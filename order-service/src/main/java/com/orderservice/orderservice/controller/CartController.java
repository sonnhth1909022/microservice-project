package com.orderservice.orderservice.controller;

import com.orderservice.orderservice.dto.cart.*;
import com.orderservice.orderservice.entity.Cart;
import com.orderservice.orderservice.entity.CartItem;
import com.orderservice.orderservice.entity.Product;
import com.orderservice.orderservice.entity.User;
import com.orderservice.orderservice.service.CartItemService;
import com.orderservice.orderservice.service.CartService;
import com.orderservice.orderservice.service.ProductService;
import com.orderservice.orderservice.service.UserService;
import com.orderservice.orderservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.orderservice.orderservice.service.UserServiceImpl.userToken;

@RestController
@RequestMapping("api/v1/cart/")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartItemMapper cartItemMapper;

    @Autowired
    private UserService userService;


    @PostMapping("add")
    public ResponseEntity<?> addToCart(@RequestHeader("token") String accessToken, @RequestParam long productId) {
        System.out.println(accessToken);
        userToken = accessToken;
        if (userToken == "") {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("You must login to do this action!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        if (accessToken.equals(userToken)) {
            Optional<User> user = userService.findUserById(accessToken);
            Optional<Cart> userCart = cartService.findCartByUserId(accessToken);
            Optional<Product> product = productService.findProductById(productId);
            if (user.isPresent()) {
                if (product.isPresent()) {
                    if (userCart.isPresent()) {
                        Optional<CartItem> userCartItem = cartItemService.findCartItemByCartIdAndProductId(userCart.get().getId(), productId);
                        if (userCartItem.isPresent()) {
                            userCartItem.get().setQuantity(userCartItem.get().getQuantity() + 1);
                            cartItemService.saveCartItem(userCartItem.get());

                            CartItemDto cartItemDto = cartItemMapper.INSTANCE.cartItemToCartItemDto(userCartItem.get());
                            return new ResponseEntity<>(RESTResponse.success(cartItemDto
                                    , "Add this item to Cart successful!"), HttpStatus.OK);
                        }
                        CartItem cartItem = new CartItem();
                        cartItem.setProductId(productId);
                        cartItem.setProductName(product.get().getName());
                        cartItem.setQuantity(1);
                        cartItem.setThumbnail(product.get().getThumbnail());
                        cartItem.setUnitPrice(product.get().getPrice());
                        cartItem.setCartId(userCart.get().getId());
                        cartItemService.saveCartItem(cartItem);

                        CartItemDto cartItemDto = cartItemMapper.INSTANCE.cartItemToCartItemDto(cartItem);
                        return new ResponseEntity<>(RESTResponse.success(cartItemDto
                                , "Add this item to Cart successful!"), HttpStatus.OK);
                    }
                    Cart cartSave = new Cart();
                    cartSave.setUserId(user.get().getUserId());
                    cartSave.setUserName(user.get().getUserName());
                    cartService.saveCart(cartSave);
                    CartItem cartItem = new CartItem();
                    cartItem.setCartId(cartSave.getId());
                    cartItem.setProductId(productId);
                    cartItem.setProductName(product.get().getName());
                    cartItem.setQuantity(1);
                    cartItem.setThumbnail(product.get().getThumbnail());
                    cartItem.setUnitPrice(product.get().getPrice());
                    cartItemService.saveCartItem(cartItem);
                    cartService.findCartById(cartSave.getId());
                    cartService.saveCart(cartSave);

                    CartItemDto cartItemDto = cartItemMapper.INSTANCE.cartItemToCartItemDto(cartItem);
                    return new ResponseEntity<>(RESTResponse.success(cartItemDto
                            , "Add this item to Cart successful!"), HttpStatus.OK);
                }
                return new ResponseEntity<>(new RESTResponse.Error()
                        .checkErrorWithMessage("Product not found!")
                        .build(), HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Wrong user Token! You must make a new login!")
                .build(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("items")
    public ResponseEntity<?> getCart(@RequestHeader("token") String accessToken) {
        userToken = accessToken;
        if (userToken == "") {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("You must login to do this action!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        if (accessToken.equals(userToken)) {
            Optional<Cart> cart = cartService.findCartByUserId(accessToken);
            if (cart.isPresent()) {
                CartDto cartDto = cartMapper.INSTANCE.cartToCartDto(cart.get());
                return new ResponseEntity<>(RESTResponse.success(cartDto
                        , "Get Cart successful!"), HttpStatus.OK);
            }
            return new ResponseEntity<>(RESTResponse.success(""
                    , "Cart is empty!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Wrong user Token! You must make a new login!")
                .build(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateCart(@RequestHeader("token") String accessToken, @RequestBody CartUpdateDto cartUpdateDto) {
        userToken = accessToken;
        if (userToken == "") {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("You must login to do this action!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        if (accessToken.equals(userToken)) {
            Optional<Cart> userCart = cartService.findCartByUserId(accessToken);
            if (userCart.isPresent()) {
                Optional<CartItem> userCartItem = cartItemService.findCartItemByCartIdAndProductId(userCart.get().getId(), cartUpdateDto.getProductId());
                if (userCartItem.isPresent()) {
                    userCartItem.get().setQuantity(userCartItem.get().getQuantity() + cartUpdateDto.getQuantity());
                    cartItemService.saveCartItem(userCartItem.get());

                    CartItemDto cartItemDto = cartItemMapper.INSTANCE.cartItemToCartItemDto(userCartItem.get());
                    return new ResponseEntity<>(RESTResponse.success(cartItemDto
                            , "Update quantity successful!"), HttpStatus.OK);
                }
                return new ResponseEntity<>(new RESTResponse.Error()
                        .checkErrorWithMessage("Cart Item not found!")
                        .build(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(RESTResponse.success(""
                    , "Cart is empty!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Wrong user Token! You must make a new login!")
                .build(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("remove")
    public ResponseEntity<?> removeItem(@RequestHeader("token") String accessToken, @RequestParam long productId){
        userToken = accessToken;
        if (userToken == "") {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("You must login to do this action!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        if(accessToken.equals(userToken)){
            Optional<Cart> userCart = cartService.findCartByUserId(accessToken);
            if(userCart.isPresent()){
                Optional<CartItem> userCartItem = cartItemService.findCartItemByCartIdAndProductId(userCart.get().getId(), productId);
                if(userCartItem.isPresent()){
                    cartItemService.deleteCartItemById(userCartItem.get().getId());
                    return new ResponseEntity<>(new RESTResponse.SuccessNoData()
                            .setMessage("remove item successful !")
                            .build(), HttpStatus.OK);
                }
                return new ResponseEntity<>(new RESTResponse.Error()
                        .checkErrorWithMessage("Cart Item not found or already removed!")
                        .build(), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(RESTResponse.success(""
                    , "Cart is empty!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Wrong user Token! You must make a new login!")
                .build(), HttpStatus.NOT_FOUND);
    }

    @Transactional
    @DeleteMapping("clear")
    public ResponseEntity<?> clearCart(@RequestHeader("token") String accessToken){
        userToken = accessToken;
        if (userToken == "") {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("You must login to do this action!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        if(accessToken.equals(userToken)){
            Optional<Cart> userCart = cartService.findCartByUserId(accessToken);
            if(userCart.isPresent()){
                List<CartItem> userCartItemList = cartItemService.findAllCartItemsByCartId(userCart.get().getId());
                if(userCartItemList.isEmpty()){
                    return new ResponseEntity<>(RESTResponse.success(""
                            , "Cart is empty!"), HttpStatus.OK);
                }
                cartItemService.deleteAllCartItemsByCartId(userCart.get().getId());
                return new ResponseEntity<>(new RESTResponse.SuccessNoData()
                        .setMessage("Clear Cart successful !")
                        .build(), HttpStatus.OK);
            }
            return new ResponseEntity<>(RESTResponse.success(""
                    , "Cart is empty!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Wrong user Token! You must make a new login!")
                .build(), HttpStatus.NOT_FOUND);
    }
}
