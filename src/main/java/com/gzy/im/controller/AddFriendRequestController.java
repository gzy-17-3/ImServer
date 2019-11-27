package com.gzy.im.controller;

import com.gzy.im.core.exception.ForbiddenException;
import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.model.Account;
import com.gzy.im.model.AddFriendRequest;
import com.gzy.im.service.AccountService;
import com.gzy.im.service.AddFriendRequestService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/add_friend_request")
public class AddFriendRequestController {



    @Resource
    AddFriendRequestService addFriendRequestService;

    @Resource
    AccountService accountService;

    //    获取申请添加为好友列表
    // 获取 所有 未处理 申请添加好友数据
    @GetMapping("/")
    List<AddFriendRequest> index(@AuthenticationPrincipal AppUserDetails appUserDetails) {
        // 判断是不是当前用户
        return addFriendRequestService.getUndisposedList(appUserDetails.account.getId());
    }

    //    查找用户信息 根据 用户名
    @GetMapping("/find")
//    List<Account> account(@AuthenticationPrincipal AppUserDetails appUserDetails, String name) {
    List<Account> find(String name) {
        return accountService.findUsersByName(name);
    }


    //    申请添加为好友

    /**
     * @param appUserDetails 当前被授权的用户 (发起人)
     */
    @PostMapping("/apply")
    void apply(@AuthenticationPrincipal AppUserDetails appUserDetails,
               @Valid @RequestBody ApplyPara applyPara){
        addFriendRequestService.applyAdd(appUserDetails.account.getId(),applyPara.toUid, applyPara.verifiInfo);
    }


    //    应答 添加为好友请求
    @PostMapping("/reply")
    void reply(@AuthenticationPrincipal AppUserDetails appUserDetails,
               @Valid @RequestBody ReplyPara para
                ){
        addFriendRequestService.reply(appUserDetails.account.getId(),para.requestid,para.opt);
    }


}

@Getter
@Setter
class ApplyPara {
    @NotNull
    Long toUid;
    @NotEmpty
    String verifiInfo;
}

@Getter
@Setter
class ReplyPara {
    @NotNull
    Long requestid;
    @NotNull
    @Max(3)
    @Min(0)
    Long opt;
}
