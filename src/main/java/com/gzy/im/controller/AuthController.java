package com.gzy.im.controller;

import com.gzy.im.core.exception.NotFoundException;
import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.model.Account;
import com.gzy.im.model.Token;
import com.gzy.im.service.AuthService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public Account signin(@Valid @RequestBody SigninPara para ){
        // 注册的事情
        return service.signin(para.getPhone(),para.getPassword());
    }

    //    登陆

    @PostMapping("/login")
    public Token login(@Valid @RequestBody SigninPara para ){
        Token loginV = service.login(para.getPhone(), para.getPassword());

        if (loginV == null){
            throw new NotFoundException();
        }

        return loginV;
    }

    @PostMapping("/update_password")
    public void updatePassword(@AuthenticationPrincipal AppUserDetails appUserDetails,
                               @Valid @RequestBody UpdatePwd updatePwd
    ){
        service.updatePWD(appUserDetails.account.getId(),updatePwd.getOldPassword(),updatePwd.getNewPassword());
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
@Getter
@Setter
class UpdatePwd{
    @NotBlank()
    String oldPassword;
    @NotBlank()
    String newPassword;
}