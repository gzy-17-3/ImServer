package com.gzy.im.controller;

import com.gzy.im.core.exception.ForbiddenException;
import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.model.Account;
import com.gzy.im.model.AddFriendRequest;
import com.gzy.im.service.AccountService;
import com.gzy.im.service.AddFriendRequestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/add_friend_request")
public class AddFriendRequestController {


//    申请添加为好友
//    应答添加为好友

    @Resource
    AddFriendRequestService addFriendRequestService;

    @Resource
    AccountService accountService;

    //    获取申请添加为好友列表
    // 获取 所有 未处理 申请添加好友数据
    @GetMapping("/{uid}")
    List<AddFriendRequest> index(@PathVariable Long uid, @AuthenticationPrincipal AppUserDetails appUserDetails) {
        // 判断是不是当前用户

        if (!uid.equals(appUserDetails.account.getId())) {
            throw new ForbiddenException();
        }

        return addFriendRequestService.getUndisposedList(uid);
    }

    //    查找用户信息 根据 用户名
    @GetMapping("/find")
//    List<Account> account(@AuthenticationPrincipal AppUserDetails appUserDetails, String name) {
    List<Account> find(String name) {
        return accountService.findUsersByName(name);
    }


}
