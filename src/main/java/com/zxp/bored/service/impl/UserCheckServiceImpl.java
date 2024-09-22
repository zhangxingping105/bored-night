package com.zxp.bored.service.impl;

import com.zxp.bored.entity.User;
import com.zxp.bored.repository.UserRepository;
import com.zxp.bored.service.UserCheckService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 用户校验服务实现
 *
 * @description: 用户校验服务实现
 * @author: zxp
 * @date: 2024/9/20 22:28
 */

@Service
public class UserCheckServiceImpl implements UserCheckService {

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean findUserExist(String account) {
        User user = userRepository.findUserByAccount(account);
        return user == null ? false : true;
    }
}
