package com.zxp.bored.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

/**
 * 消息记录
 *
 * @description: 消息记录
 * @author: zxp
 * @date: 2024/9/12 21:45
 */
@Data
@Entity
@Table(indexes = {@Index(name = "message_idx1", columnList = "senderAccount"),
    @Index(name = "message_idx2", columnList = "receiverAccount")})
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String senderAccount;

    @Column(nullable = false)
    private String receiverAccount;
    private String content;
    private Date createTime;
    private Date receiptTime;
}
