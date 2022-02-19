package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.entity.TransactionHistory;

import java.util.List;

public interface TransactionService {
    TransactionHistory saveTransaction(TransactionHistory transaction);
    List<TransactionHistory> getAllTransactionsBySenderId(String senderId);
}
