package com.microservice.inventoryservice.dto.provider;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProviderDto {
    private String name;
    private String address;
    private String phone;
    private String email;
}
