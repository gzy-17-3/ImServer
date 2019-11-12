package com.gzy.im.core;

import com.gzy.im.model.Token;
import com.gzy.im.service.AppUserDetailsService;
import com.gzy.im.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class TokenAuthenticationFilter extends BasicAuthenticationFilter {

    AppUserDetailsService userDetailsService;
    TokenService tokenService;

    public TokenAuthenticationFilter(AuthenticationManager authenticationManager, AppUserDetailsService userDetailsService,TokenService tokenService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    /// 获取 -> 登录信息
    private Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        //  Authorization :  Bearer dasdasdasdasdsadasd

        if (token != null) {

            String tokenValue = token.replace("Bearer ", "").trim();
            Token token1 = tokenService.find(tokenValue);
            Long userid = token1.getUserid();

            UserDetails userDetails = userDetailsService.loadUserByUsername(userid.toString());
            return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        }
        return null;
    }
}
