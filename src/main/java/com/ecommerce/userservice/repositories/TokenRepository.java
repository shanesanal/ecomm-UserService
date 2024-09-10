package com.ecommerce.userservice.repositories;

import com.ecommerce.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {


    @Override
    Token save(Token token);




    Optional<Token> findByTokenAndDeleted(String token, boolean deleted);

     Optional<Token> findByTokenAndDeletedAndExpiryAtGreaterThan(String token, boolean deleted, Date date);
}
