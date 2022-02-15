package com.paymentservice.paymentservice.entity;

import lombok.*;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@MappedSuperclass
public class BaseEntity {
    private LocalDate createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
    }
}
