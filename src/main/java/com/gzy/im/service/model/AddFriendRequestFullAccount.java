package com.gzy.im.service.model;

import com.gzy.im.model.Account;
import com.gzy.im.model.AddFriendRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class AddFriendRequestFullAccount {
    private Long id;

    private Long aid;
    private Account account;

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

    private LocalDateTime createdDate;

}