package com.ecommerce.userservice.controllers;

import com.ecommerce.userservice.dtos.*;
import com.ecommerce.userservice.dtos.ResponseStatus;
import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.services.UserService;
import com.ecommerce.userservice.services.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public SignUpResponseDto signup(@RequestBody  SignUpRequestDto requestDto) throws JsonProcessingException {

        User user = userService.signUp(requestDto.getName(), requestDto.getEmail(), requestDto.getPassword());

        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setStatus(ResponseStatus.SUCCESS);
        responseDto.setUser(user);

        return responseDto;


    }


    @GetMapping("/validate-token/{token}")
    public UserDto validateToken(@PathVariable("token") String token){
        User user = userService.validateToken(token);

   return UserDto.fromUser(user);


    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        ResponseEntity<Void> responseEntity = null;
        try {


            userService.logout(logoutRequestDto.getToken());
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
