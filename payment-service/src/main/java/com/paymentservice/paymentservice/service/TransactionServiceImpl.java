package com.paymentservice.paymentservice.service;

import com.paymentservice.paymentservice.entity.TransactionHistory;
import com.paymentservice.paymentservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionHistory saveTransaction(TransactionHistory transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionHistory> getAllTransactionsBySenderId(String senderId) {
        return transactionRepository.getAllBySenderId(senderId);
    }
}
