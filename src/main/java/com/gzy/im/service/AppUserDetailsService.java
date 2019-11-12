package com.gzy.im.service;

import com.gzy.im.model.Account;
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

    public static class AppUserDetails implements UserDetails{

        public Account account;

        public AppUserDetails(Account account) {
            this.account = account;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new ArrayList<>();
        }

        @Override
        public String getPassword() {
            return account.getPassword();
        }

        @Override
        public String getUsername() {
            return account.getId().toString();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}


