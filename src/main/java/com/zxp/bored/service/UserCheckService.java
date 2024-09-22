package com.zxp.bored.service;

/**
 * 用户校验服务
 *
 * @description: 用户校验服务
 * @author: zxp
 * @date: 2024/9/20 22:28
 */
public interface UserCheckService {

    /**
     * @description 查询用户是否存在
     * @param account 账号
     * @return  是否存在
     * @date 2024/9/15 21:17
     */
    boolean findUserExist(String account);
}
