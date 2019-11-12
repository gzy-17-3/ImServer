package com.gzy.im.controller;

import com.gzy.im.model.Account;
import com.gzy.im.model.Token;
import com.gzy.im.service.AuthService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    AuthService service;

    //    注册
//    signin
    @PostMapping("/signin")
    public Object signin(@Valid @RequestBody SigninPara para ){
        // 注册的事情
        return service.signin(para.getPhone(),para.getPassword());
    }

    //    登陆

    @PostMapping("/login")
    public Object login(@Valid @RequestBody SigninPara para ){
        Token loginV = service.login(para.getPhone(), para.getPassword());

        if (loginV == null){
            return ResponseEntity.notFound().build();
        }

        return loginV;
    }

}

@Getter
@Setter
class SigninPara {
    @NotBlank(message = "手机号不能为空")
    String phone;
    @NotBlank(message = "密码不能为空")
    String password;
}