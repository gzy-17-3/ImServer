package com.gzy.im.controller;

import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.dto.ChatSessionDTO;
import com.gzy.im.service.ChatService;
import lombok.Data;
import org.apache.catalina.LifecycleState;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat_session")
public class ChatSessionController {

    @Resource
    ChatService chatService;

    /**
     * 创建会话
     * @param appUserDetails
     * @param para
     * @return
     */
    @PostMapping("/")
    ChatSessionDTO creat(@AuthenticationPrincipal AppUserDetails appUserDetails,@RequestBody CreateChatSessionPara para){
        ChatSessionDTO session = chatService.createSession(appUserDetails.account.getId(), para.userid);
        return session;
    }

    /**
     * 获取当前用户的会话列表
     * @param appUserDetails
     * @return
     */
    @GetMapping("/")
    List<ChatSessionDTO> all(@AuthenticationPrincipal AppUserDetails appUserDetails){
        return chatService.showAllSession(appUserDetails.account.getId());
    }


}

@Data
class CreateChatSessionPara{
    // 对方 uid
    Long userid;
}