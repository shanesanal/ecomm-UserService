package com.ecommerce.userservice.dtos;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
