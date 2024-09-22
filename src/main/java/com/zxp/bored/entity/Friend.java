package com.zxp.bored.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 好友
 *
 * @description: 好友
 * @author: zxp
 * @date: 2024/9/20 21:42
 */
@Data
@Entity
@Table(indexes = {@Index(name = "friend_idx1", columnList = "userAccount"),
        @Index(name = "friend_idx2", columnList = "friendAccount")})
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userAccount;

    @Column(nullable = false)
    private String friendAccount;

    private Date createTime;

    public Friend(String userAccount, String friendAccount, Date createTime) {
        this.userAccount = userAccount;
        this.friendAccount = friendAccount;
        this.createTime = createTime;
    }
}
