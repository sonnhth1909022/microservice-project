package com.paymentservice.paymentservice.repository;

import com.paymentservice.paymentservice.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionHistory,Long> {
    List<TransactionHistory> getAllBySenderId(String senderId);
}
