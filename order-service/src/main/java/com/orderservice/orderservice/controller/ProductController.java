package com.orderservice.orderservice.controller;

import com.orderservice.orderservice.dto.product.ProductDto;
import com.orderservice.orderservice.dto.product.ProductMapper;
import com.orderservice.orderservice.entity.Product;
import com.orderservice.orderservice.service.ProductService;
import com.orderservice.orderservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/product/")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping("list")
    public ResponseEntity<?> getAllProducts(){
        return new ResponseEntity<>(RESTResponse.success(productService.getAllProducts()
                ,"get all products successful!"), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto productDto){
        Product product = productMapper.INSTANCE.productDtoToProduct(productDto);
        productService.saveProduct(product);
        return new ResponseEntity<>(RESTResponse.success(productDto
                ,"add product successful!"), HttpStatus.OK);
    }
}
