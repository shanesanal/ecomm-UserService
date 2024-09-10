package com.ecommerce.userservice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SendEmailDto {
    private String subject;
    private String from ;
    private String to;
    private String body;

}
