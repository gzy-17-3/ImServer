package com.gzy.im.controller;

import com.gzy.im.core.exception.ForbiddenException;
import com.gzy.im.core.exception.NotFoundException;
import com.gzy.im.model.Account;
import com.gzy.im.service.AccountService;
import com.gzy.im.service.AppUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/*

 异常
 2
 1. 运行时异常 RuntimeException
    - 不需要捕获
 2. 普通异常 Exception
    - 需要进行处理

 */



@RestController
@RequestMapping("mine/")
public class MineController {
    @Resource
    AccountService accountService;
//    获取当前用户信息
    @GetMapping("/{uid}")
    Account account(@PathVariable Long uid, @AuthenticationPrincipal AppUserDetailsService.AppUserDetails appUserDetails)  {
        System.out.println(appUserDetails);


        // 判断是不是当前用户

        if (!uid.equals(appUserDetails.account.getId())){
            throw new ForbiddenException();
        }

        return accountService.getAccount(uid);
    }

//    修改信息
    @PutMapping("/{uid}")
    Account updateAccount(@PathVariable Long uid,
                         @AuthenticationPrincipal AppUserDetailsService.AppUserDetails appUserDetails,
                         @RequestBody Account account
    ){
        if (!uid.equals(appUserDetails.account.getId())){
            throw new ForbiddenException();
        }

        return accountService.updateAccount(uid,account);
    }

//    修改密码


//    修改头像
//    上传头像
}
