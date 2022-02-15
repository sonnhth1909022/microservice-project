package com.orderservice.orderservice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_details")
@ToString
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long productId;
    private String productName;
    private int quantity;
    private double unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false,updatable = false)
    private Order order;

    @Column(name = "order_id")
    private String orderId;
}
