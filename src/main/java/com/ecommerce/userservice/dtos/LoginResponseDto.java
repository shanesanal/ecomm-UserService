package com.ecommerce.userservice.dtos;

import com.ecommerce.userservice.models.Token;
import lombok.Data;

@Data
public class LoginResponseDto {
    private Token token;
}
