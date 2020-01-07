package com.gzy.im.dto;

import com.gzy.im.model.Account;
import com.gzy.im.model.ChatSession;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
public class ChatDTO {
    private Long id;

    private Long sessionid;
    private Long fromaid;
    private Long toaid;

    private String content;
    private LocalDateTime date;

    private Account from;
    private Account to;

}
