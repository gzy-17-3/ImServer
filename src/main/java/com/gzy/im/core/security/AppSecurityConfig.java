package com.gzy.im.core.security;


import com.gzy.im.core.security.AppUserDetailsService;
import com.gzy.im.core.security.TokenAuthenticationFilter;
import com.gzy.im.service.TokenService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.Resource;

@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AppUserDetailsService userDetailsService;
    @Resource
    private TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable();
        http.rememberMe().disable();
        // 禁用缓存
        http.headers().cacheControl().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                // 任何  OPTIONS 放行
                .antMatchers(HttpMethod.POST, "/auth/signin").permitAll()
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .antMatchers("/file/*").permitAll()
                // 注册放行
                .anyRequest().authenticated();
        // 任何 请求都需要进行 身份验证;
//
//        http.addFilter(new JWTLoginFilter(authenticationManager()))
//                .addFilter(new JWTAuthenticationFilter(authenticationManager(),userDetailsService));
        http.addFilter(new TokenAuthenticationFilter(authenticationManager(),userDetailsService,tokenService));

    }
}
