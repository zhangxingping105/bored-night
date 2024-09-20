package com.zxp.bored.service.impl;

import com.zxp.bored.entity.ApiResponse;
import com.zxp.bored.entity.User;
import com.zxp.bored.model.request.UserLoginReq;
import com.zxp.bored.repository.UserRepository;
import com.zxp.bored.service.UserService;
import com.zxp.bored.util.JwtUtil;
import com.zxp.bored.util.ResponseUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现
 *
 * @description: 用户服务实现
 * @author: zxp
 * @date: 2024/9/12 22:02
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public UserServiceImpl(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean addUser(User user) {
        User existUser = userRepository.findUserByAccount(user.getAccount());
        if (existUser == null) {
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public boolean findUserExist(String account) {
        User user = userRepository.findUserByAccount(account);
        return user == null ? false : true;
    }

    @Override
    public ResponseEntity<ApiResponse<String>> login(UserLoginReq req) {
        User existUser = userRepository.findUserByAccount(req.getAccount());
        if (existUser != null && existUser.getPassword().equals(req.getPassword())) {
            return ResponseUtil.success(jwtUtil.generateToken(req.getAccount()));
        }
        return ResponseUtil.error(HttpStatus.UNAUTHORIZED.value(), "账户密码不正确");
    }
}
