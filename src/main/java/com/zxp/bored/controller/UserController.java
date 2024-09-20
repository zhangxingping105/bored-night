package com.zxp.bored.controller;

import com.zxp.bored.entity.User;
import com.zxp.bored.service.UserService;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户访问控制
 *
 * @description: 用户访问控制
 * @author: zxp
 * @date: 2024/9/12 22:00
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.findUserByUsername(username);
    }

}
