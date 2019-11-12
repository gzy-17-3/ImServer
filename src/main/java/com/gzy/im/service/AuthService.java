package com.gzy.im.service;

import com.gzy.im.model.Account;
import com.gzy.im.model.Token;
import com.gzy.im.repository.AccountRepository;
import com.gzy.im.repository.TokenRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.util.DigestUtils;

import java.util.UUID;

@Service
public class AuthService {

    @Resource
    AccountRepository accountRepository;
    @Resource
    TokenRepository tokenRepository;

    public Account signin(String phone, String password) {

        //用户表
        Account account = new Account();
        account.setPhone(phone);

        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        account.setPassword(md5Pass);

        account.setName("phone+"+phone);

        accountRepository.save(account);

        return account;

    }

    public Token login(String phone, String password) {
        Account account = accountRepository.findAccountByPhone(phone);
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!account.getPassword().equals(md5Password)) {
            return null;
        }

        Token token = new Token();
        token.setUserid(account.getId());
        String tokenVa = UUID.randomUUID().toString();
        token.setToken(tokenVa);

        tokenRepository.save(token);

        return token;
    }
}
