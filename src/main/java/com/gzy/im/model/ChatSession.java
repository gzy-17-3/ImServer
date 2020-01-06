package com.gzy.im.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ChatSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aid1;
    private Long aid2;

    // 最后一次信息的 id
    private Long lastChatId;

    @CreatedDate
    private LocalDateTime createdDate;
    // 最后一次更新的时间
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}