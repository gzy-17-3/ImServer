package com.gzy.im.service;

import com.gzy.im.model.Token;
import com.gzy.im.repository.TokenRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TokenService {
    @Resource
    TokenRepository tokenRepository;

    public Token find(String token){
        Token tokenByToken = tokenRepository.findTokenByToken(token);
        return tokenByToken;
    }
}
