package com.gzy.im.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"phone"})
        }
)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // +86 1330000000
    // 字段唯一
    @Column(length = 130)
    private String phone;
    private String name;
    @JsonIgnore
    private String password;
    private String bio;
//            个性签名
    private String avatar;
//            头像
    private LocalDate birthday;
//            生日
    // 优化  int  4 * byte  50
    private Integer gender;
//            性别

}
