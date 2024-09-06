package com.ecommerce.userservice.models;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "roles")
public class Role extends BaseModel{
    private String name;


}
