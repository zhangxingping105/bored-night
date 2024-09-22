package com.zxp.bored.service;

import com.zxp.bored.entity.ApiResponse;
import com.zxp.bored.entity.User;
import com.zxp.bored.model.request.UserLoginReq;
import org.springframework.http.ResponseEntity;

/**
 * 用户服务
 *
 * @description: 用户服务
 * @author: zxp
 * @date: 2024/9/12 22:01
 */
public interface UserService {

    /**
     * @description 添加用户
     * @param user 用户
     * @return 结果
     * @date 2024/9/12 22:03
     */
    boolean addUser(User user);

    /**
     * @description 查询用户信息
     * @param username 用户名
     * @return  用户信息
     * @date 2024/9/12 22:05
     */
    User findUserByUsername(String username);

    /**
     * @description 登录
     * @param req 请求参数
     * @return token
     * @date 2024/9/17 11:57
     */
    ResponseEntity<ApiResponse<String>> login(UserLoginReq req);
}
