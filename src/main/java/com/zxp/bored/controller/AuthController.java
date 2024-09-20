package com.zxp.bored.controller;

import com.zxp.bored.entity.ApiResponse;
import com.zxp.bored.model.request.UserLoginReq;
import com.zxp.bored.service.UserService;
import com.zxp.bored.util.ResponseUtil;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限访问控制
 *
 * @description: 权限访问控制
 * @author: zxp
 * @date: 2024/9/17 13:39
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserLoginReq req) {
        return userService.login(req);
    }

}
