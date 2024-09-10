package com.ecommerce.userservice.services;

import com.ecommerce.userservice.dtos.SendEmailDto;
import com.ecommerce.userservice.models.Token;
import com.ecommerce.userservice.models.User;
import com.ecommerce.userservice.repositories.TokenRepository;
import com.ecommerce.userservice.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final TokenRepository tokenRepository;
    private KafkaTemplate<String,String> kafkaTemplate;
    private ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository, KafkaTemplate kafkaTemplate, ObjectMapper objectMapper) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public User signUp(String name, String email, String password) throws JsonProcessingException {
        Optional<User> optionalUser = repository.findByEmail(email);

        User user = null;

        if(optionalUser.isPresent())
        {

        }
        else{
            user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setHashedPassword(bCryptPasswordEncoder.encode(password));

            user = repository.save(user);

            /*
            I want to publish this event to message queue
             */
            SendEmailDto sendEmailDto = new SendEmailDto();
            sendEmailDto.setTo(email);
            sendEmailDto.setSubject("signedup");
            sendEmailDto.setBody("Welcome to our platform");
            sendEmailDto.setFrom("shanemathewsanal@gmail.com");
            kafkaTemplate.send("send-email",
                    objectMapper.writeValueAsString(sendEmailDto));



        }
        return user;
    }

    @Override
    public Token login(String email, String password) {
        Optional<User> optionalUser = repository.findByEmail(email);

        User user = null;
        if (optionalUser.isEmpty()) {

        } else {
            user = optionalUser.get();
            if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
                return null;
            }

            Token token = createToken(user);
            tokenRepository.save(token);

            return token;

        }
        return null;
    }

    private Token createToken(User user){
        Token token = new Token();
        token.setUser(user);
        token.setToken(RandomStringUtils.randomAlphanumeric(128));

        LocalDate localDate = LocalDate.now();
        LocalDate thirtyDaysLater = localDate.plus(30, ChronoUnit.DAYS);

        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        token.setExpiryAt(expiryDate);
        return token;
    }

    @Override
    public User validateToken(String token) {
       Optional<Token> optionalToken = tokenRepository.findByTokenAndDeletedAndExpiryAtGreaterThan(token, false, new Date());

       if(optionalToken.isEmpty()){
           return null;
       }

    return optionalToken.get().getUser();

    }

    @Override
    public void logout(String token) {
        Optional<Token> optionalToken = tokenRepository.findByTokenAndDeleted(token, false);
        if(optionalToken.isEmpty()){
            //throw new TokenNotFoundException();
        }

        Token  token1 = optionalToken.get();
        token1.setDeleted(true);
        tokenRepository.save(token1);

    }
}
