package com.zxp.bored.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * 用户
 *
 * @description: 用户
 * @author: zxp
 * @date: 2024/9/12 21:43
 */
@Data
@Entity
@Table(indexes = {@Index(name = "user_idx1", columnList = "account", unique = true)})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    @Column(nullable = false)
    private String account;
    private String username;
    private String password;
}
