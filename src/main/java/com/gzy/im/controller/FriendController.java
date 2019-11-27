package com.gzy.im.controller;

import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.model.Account;
import com.gzy.im.model.FriendRelational;
import com.gzy.im.service.FriendService;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Resource
    FriendService friendService;

    @GetMapping("/")
    List<Account> index(@AuthenticationPrincipal AppUserDetails appUserDetails,
                                 @RequestParam(defaultValue = "0") Integer page,
                                 @RequestParam(defaultValue = "10") Integer size){

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, size, sort);

        return friendService.findAll(pageable, appUserDetails.account.getId());
//        return friendService.findAllFriend(pageable, appUserDetails.account.getId());
    }

    @DeleteMapping("/{uid}")
    void delete(@AuthenticationPrincipal AppUserDetails appUserDetails,@PathVariable Long uid){
        Long curId = appUserDetails.account.getId();
        friendService.deleteRelational(curId,uid);
    }

    @GetMapping("/{uid}")
    Account get(@PathVariable Long uid){
        return friendService.findFriend(uid);
    }

}

