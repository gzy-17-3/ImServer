package com.gzy.im.core.security;

import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.model.Account;
import com.gzy.im.service.AccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class AppUserDetailsService implements UserDetailsService {


    @Resource
    AccountService accountService;

    /**
     *
     * @param username 泛指能代表 user 的东西  比如 uid  username  token
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //
        Account account = accountService.getAccount(Long.valueOf(username));

        if(account == null){
            throw new UsernameNotFoundException(username);
        }

        return new AppUserDetails(account);
    }



}


