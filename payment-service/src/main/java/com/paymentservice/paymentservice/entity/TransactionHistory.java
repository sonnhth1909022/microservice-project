package com.paymentservice.paymentservice.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_history")
@ToString
public class TransactionHistory extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String senderId;
    private String receiverId;
    private long orderId;
    private String paymentType;
    private String status;
    private double amount;
    private String message;
}
