package com.zxp.bored.controller;

import com.zxp.bored.entity.ApiResponse;
import com.zxp.bored.model.response.FriendDetailResp;
import com.zxp.bored.service.FriendService;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 好友访问控制
 *
 * @description: 好友访问控制
 * @author: zxp
 * @date: 2024/9/20 22:32
 */

@RestController
@RequestMapping("/api/friend")
public class FriendController {

    @Resource
    private FriendService friendService;

    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> addFriend(@RequestBody String friendAccount, @RequestHeader String account) {
        return friendService.addFriend(account, friendAccount);
    }

    @DeleteMapping("/{friendAccount}")
    public ResponseEntity<ApiResponse<Boolean>> deleteFriend(@PathVariable String friendAccount, @RequestHeader String account) {
        return friendService.deleteFriend(account, friendAccount);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FriendDetailResp>>> queryFriend(@RequestHeader String account) {
        return friendService.queryFriendDetail(account);
    }
}
