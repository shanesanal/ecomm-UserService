package com.ecommerce.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "tokens")
public class Token extends BaseModel{

    private String token;

    @ManyToOne
    private User user;
    private Date expiryAt;


}
