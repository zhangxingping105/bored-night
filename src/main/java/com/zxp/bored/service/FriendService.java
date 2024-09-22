package com.zxp.bored.service;

import com.zxp.bored.entity.ApiResponse;
import com.zxp.bored.model.response.FriendDetailResp;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 好友服务
 *
 * @description: 好友服务
 * @author: zxp
 * @date: 2024/9/20 21:50
 */
public interface FriendService {
    ResponseEntity<ApiResponse<Boolean>> addFriend(String userAccount, String friendAccount);

    ResponseEntity<ApiResponse<Boolean>> deleteFriend(String userAccount, String friendAccount);

    ResponseEntity<ApiResponse<List<FriendDetailResp>>> queryFriendDetail(String account);
}
