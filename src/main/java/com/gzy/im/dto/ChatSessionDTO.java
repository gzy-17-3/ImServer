package com.gzy.im.dto;

import com.gzy.im.model.Account;
import com.gzy.im.model.Chat;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class ChatSessionDTO {
    private Long id;

    // 聊天的用户
    private Account account;
    
    private LocalDateTime createdDate;
    // 最后一次更新的时间
    private LocalDateTime lastModifiedDate;

    // 双方聊天中最后一条发送的数据
    private Chat lastChat;
}
