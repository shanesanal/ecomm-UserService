package com.ecommerce.userservice.services;

import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User signUp(String name,String email, String password);

    Token login(String email, String password);

    User validateToken(String token);

    Void logout(String token);
}
