package com.paymentservice.paymentservice.controller;

import com.paymentservice.paymentservice.entity.Wallet;
import com.paymentservice.paymentservice.service.WalletService;
import com.paymentservice.paymentservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/account/")
@CrossOrigin("*")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("balance")
    public ResponseEntity<?> getAccount(@RequestHeader("token") String accessToken){
        Optional<Wallet> wallet = walletService.findWalletByUserId(accessToken);
        if (wallet.isPresent()){
            return new ResponseEntity<>(RESTResponse.success(wallet.get()
                    ,"Get wallet successful!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new RESTResponse.Error()
                .checkErrorWithMessage("Wallet not found!")
                .build(), HttpStatus.NOT_FOUND);
    }
}
