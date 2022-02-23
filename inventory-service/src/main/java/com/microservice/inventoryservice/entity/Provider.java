package com.microservice.inventoryservice.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "providers")
@ToString
public class Provider extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String status;

    @OneToMany(mappedBy="provider")
    private Set<Inventory> inventories;
}
