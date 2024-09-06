package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.*;
import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.services.UserService;
import com.ecommerce.userservice.services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){

        Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token);
        return loginResponseDto;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signup(@RequestBody  SignUpRequestDto requestDto){

        User user = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());

        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setStatus(ResponseStatus.SUCCESS);
        responseDto.setUser(user);

        return responseDto;


    }


    public UserDto validateToken(ValidateTokenDto validateTokenDto){
        return null;

    }


    public ResponseEntity<Void> logout(LogoutRequestDto logoutRequestDto){
        return null;

    }

}
