package com.gzy.im.service;

import com.gzy.im.dto.ChatSessionDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceTest {

    @Resource
    ChatService chatService;

    @Test
    void creaetChatSession() {
        ChatSessionDTO session = chatService.createSession(4L, 5L);
        assertNotNull(session.getId());
    }

    @Test
    void showAllChatSession() {
        List<ChatSessionDTO> chatSessionDTOS = chatService.showAllSession(5L);

//        assertNotNull(session.getId());
    }


}