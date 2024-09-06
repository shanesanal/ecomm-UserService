package com.ecommerce.userservice.dtos;

import com.ecommerce.userservice.models.User;
import lombok.Data;

@Data
public class SignUpResponseDto {
  private User user;

  private ResponseStatus status;
}
