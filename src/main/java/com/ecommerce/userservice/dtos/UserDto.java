package com.ecommerce.userservice.dtos;

import com.ecommerce.userservice.models.Role;
import com.ecommerce.userservice.models.User;
import lombok.Data;

import java.util.List;
@Data
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;

    public static UserDto fromUser(User user){
        if(user == null){
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
