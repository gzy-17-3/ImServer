package com.gzy.im.controller;

import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.service.FriendService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Resource
    FriendService friendService;

    @DeleteMapping("/{uid}")
    void delete(@AuthenticationPrincipal AppUserDetails appUserDetails,@PathVariable Long uid){
        Long curId = appUserDetails.account.getId();
        friendService.deleteRelational(curId,uid);
    }
}
