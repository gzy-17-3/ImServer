package com.gzy.im.controller;

import com.gzy.im.core.security.AppUserDetails;
import com.gzy.im.dto.ChatDTO;
import com.gzy.im.model.Chat;
import com.gzy.im.service.ChatService;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Resource
    ChatService chatService;

    /**
     * 发送消息 接口
     * @param appUserDetails
     * @param session_id 路径参数 连接 id
     * @param para 发送的数据
     * @return
     */
    @PostMapping("/{session_id}")
    Chat send(@AuthenticationPrincipal AppUserDetails appUserDetails, @PathVariable Long session_id, @RequestBody SendPara para){
        return chatService.sendChat(session_id,appUserDetails.account.getId(),para.getToaid(),para.getContent());
    }

    /**
     * 加载 聊天室数据
     * @param appUserDetails
     * @param session_id 会话 id
     * @param lastChatId 已经加载了的数据的id，如果为 0  从头加载
     * @return
     */
    @GetMapping("/{session_id}")
    List<ChatDTO> loadChatList(@AuthenticationPrincipal AppUserDetails appUserDetails,@PathVariable Long session_id,@RequestParam(defaultValue = "0") Long lastChatId){
        return chatService.loadChatList(appUserDetails.account.getId(),session_id,lastChatId);
    }

}

@Data
class SendPara{
    // 发送给谁
    private Long toaid;
    // 发送的内容
    private String content;
}
