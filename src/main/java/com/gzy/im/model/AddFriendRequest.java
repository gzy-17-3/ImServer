package com.gzy.im.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class AddFriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long aid;
    private Long toaid;
    private String verifiInfo;
    private Integer operation;
    //            未操作
    //		0
    //    接受
    //		1
    //    忽略
    //		2
    //    拒绝
    //		3

    @CreatedDate
    private LocalDateTime createdDate;


}
