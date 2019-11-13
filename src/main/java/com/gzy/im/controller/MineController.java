package com.gzy.im.controller;

import com.gzy.im.model.Account;
import com.gzy.im.service.AccountService;
import com.gzy.im.service.AppUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("mine/")
public class MineController {
    @Resource
    AccountService accountService;
//    获取当前用户信息
    @GetMapping("/{uid}")
    Object account(@PathVariable Long uid, @AuthenticationPrincipal AppUserDetailsService.AppUserDetails appUserDetails){
        System.out.println(appUserDetails);

        // 判断是不是当前用户

        if (!uid.equals(appUserDetails.account.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return accountService.getAccount(uid);
    }

//    修改信息
    @PutMapping("/{uid}")
    Object updateAccount(@PathVariable Long uid,
                         @AuthenticationPrincipal AppUserDetailsService.AppUserDetails appUserDetails,
                         @RequestBody Account account
    ){
        if (!uid.equals(appUserDetails.account.getId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return accountService.updateAccount(uid,account);
    }

//    修改密码


//    修改头像
//    上传头像
}
