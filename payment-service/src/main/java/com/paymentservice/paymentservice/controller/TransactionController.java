package com.paymentservice.paymentservice.controller;

import com.paymentservice.paymentservice.entity.TransactionHistory;
import com.paymentservice.paymentservice.service.TransactionService;
import com.paymentservice.paymentservice.ulti.RESTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transaction/")
@CrossOrigin("*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("history")
    public ResponseEntity<?> getHistory(@RequestHeader("token") String accessToken){
        List<TransactionHistory> transactions = transactionService.getAllTransactionsBySenderId(accessToken);
        if (transactions.isEmpty()){
            return new ResponseEntity<>(new RESTResponse.Error()
                    .checkErrorWithMessage("No Transactions made by this user yet!")
                    .build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(RESTResponse.success(transactions
                ,"Get list transaction history successful!"), HttpStatus.OK);
    }
}
