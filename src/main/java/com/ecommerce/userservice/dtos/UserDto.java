package com.ecommerce.userservice.dtos;

import com.ecommerce.userservice.models.Role;
import lombok.Data;

import java.util.List;
@Data
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
}
