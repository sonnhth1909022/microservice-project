package com.microservice.inventoryservice.dto.provider;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProviderDtoRes {
    private long id;

    private String name;
    private String address;
    private String phone;
    private String email;
    private String status;
}
