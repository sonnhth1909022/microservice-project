package com.orderservice.orderservice.controller;

import com.orderservice.orderservice.dto.order.OrderDto;
import com.orderservice.orderservice.dto.order.OrderMapper;
import com.orderservice.orderservice.dto.order.OrderPrepareDto;
import com.orderservice.orderservice.entity.Cart;
import com.orderservice.orderservice.entity.CartItem;
import com.orderservice.orderservice.entity.Order;
import com.orderservice.orderservice.entity.OrderDetail;
import com.orderservice.orderservice.enums.OrderStatus;
import com.orderservice.orderservice.service.*;
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
@RequestMapping("api/v1/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailService orderDetailService;

    @Transactional
    @PostMapping("submit")
    public ResponseEntity submitOrder(@RequestHeader("token") String accessToken, @RequestBody OrderPrepareDto orderPrepareDto){
        if (userToken == "") {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("You must login to do this action!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        if(accessToken.equals(userToken)){
            Optional<Cart> userCart = cartService.findCartByUserId(accessToken);
            double totalPrice = 0;
            if(userCart.isPresent()){
                List<CartItem> cartItemList = cartItemService.findAllCartItemsByCartId(userCart.get().getId());
                if(cartItemList.isEmpty()){
                    return new ResponseEntity<>(RESTResponse.success(""
                            , "Cart is empty!"), HttpStatus.OK);
                }
                Order orderSave = new Order();
                orderSave.setUserId(accessToken);
                orderSave.setName(orderPrepareDto.getName());
                orderSave.setPhone(orderPrepareDto.getPhone());
                orderSave.setEmail(orderPrepareDto.getEmail());
                orderSave.setAddress(orderPrepareDto.getAddress());
                orderService.saveOrder(orderSave);
                for(CartItem item: cartItemList){
                    totalPrice += item.getQuantity()*item.getUnitPrice();
                    OrderDetail orderItem = new OrderDetail();
                    orderItem.setOrderId(orderSave.getOrderId());
                    orderItem.setProductId(item.getProductId());
                    orderItem.setProductName(item.getProductName());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setUnitPrice(item.getUnitPrice());
                    orderDetailService.saveOrderDetail(orderItem);
                }
                if(totalPrice <= 0){
                    throw new RuntimeException("Cart total price Not valid!");
                }
                orderSave.setTotalPrice(totalPrice);
                orderSave.setOrderStatus(OrderStatus.PENDING.name());
                orderService.findOrderById(orderSave.getOrderId());
                orderService.saveOrder(orderSave);

                cartItemService.deleteAllCartItemsByCartId(userCart.get().getId());

                return new ResponseEntity<>(RESTResponse.success(orderMapper.INSTANCE.orderToOrderDto(orderSave)
                        ,"Create Order successful"), HttpStatus.OK);
            }
            return new ResponseEntity<>(RESTResponse.success(""
                    , "Cart is empty!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Wrong user Token! You must make a new login!")
                .build(), HttpStatus.NOT_FOUND);
    }


    @GetMapping("list")
    public ResponseEntity<?> getAllOrdersByUserId(@RequestHeader("Token") String accessToken){
        if (userToken == "") {
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("You must login to do this action!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        if(accessToken.equals(userToken)){
            List<Order> userOrders = orderService.findAllOrdersByUserId(accessToken);
            if(userOrders.isEmpty()){
                return new ResponseEntity<>(RESTResponse.success(""
                        , "You don't have any order yet!"), HttpStatus.OK);
            }
            List<OrderDto> lsOrdersDto = orderMapper.lsOrderToOrderDto(userOrders);
            return new ResponseEntity<>(RESTResponse.success(lsOrdersDto
                    , "Get All User Orders successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Wrong user Token! You must make a new login!")
                .build(), HttpStatus.NOT_FOUND);
    }
}
