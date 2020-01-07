package com.gzy.im.controller;

import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.model.Chat;
import com.gzy.im.service.ChatService;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    ChatService chatService;

    @PostMapping("/{session_id}")
    Chat send(@AuthenticationPrincipal AppUserDetails appUserDetails, @PathVariable Long session_id, @RequestBody SendPara para){
        return chatService.sendChat(session_id,appUserDetails.account.getId(),para.getToaid(),para.getContent());
    }

}

@Data
class SendPara{
    private Long toaid;
    private String content;
}
