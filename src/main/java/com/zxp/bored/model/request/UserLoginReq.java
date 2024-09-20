package com.zxp.bored.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 用户登录请求
 *
 * @description: 用户登录请求
 * @author: zxp
 * @date: 2024/9/17 11:56
 */
@Data
public class UserLoginReq {

    @NotNull(message = "account is empty")
    private String account;
    @NotNull(message = "password is empty")
    private String password;
}
