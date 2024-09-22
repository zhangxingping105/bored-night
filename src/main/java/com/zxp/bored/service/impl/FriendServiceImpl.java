package com.zxp.bored.service.impl;

import com.zxp.bored.dao.FriendDao;
import com.zxp.bored.entity.ApiResponse;
import com.zxp.bored.entity.Friend;
import com.zxp.bored.model.response.FriendDetailResp;
import com.zxp.bored.repository.FriendRepository;
import com.zxp.bored.service.FriendService;
import com.zxp.bored.service.UserCheckService;
import com.zxp.bored.util.ResponseUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 好友服务实现
 *
 * @description: 好友服务实现
 * @author: zxp
 * @date: 2024/9/20 21:50
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Resource
    private UserCheckService userCheckService;

    @Resource
    private FriendRepository friendRepository;

    @Resource
    private FriendDao friendDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity<ApiResponse<Boolean>> addFriend(String userAccount, String friendAccount) {
        if(StringUtils.isEmpty(userAccount) || StringUtils.isEmpty(friendAccount)){
            return ResponseUtil.error(HttpStatus.NOT_FOUND.value(), "用户ID不存在");
        }
        if(userAccount.equalsIgnoreCase(friendAccount)){
            return ResponseUtil.error(HttpStatus.CONFLICT.value(), "不允许添加自己");
        }
        int count = friendRepository.countByUserAccountAndFriendAccount(userAccount, friendAccount);
        if (count > 0) {
            return ResponseUtil.error(HttpStatus.NOT_ACCEPTABLE.value(), "不允许重复添加");
        }
        boolean userExist = userCheckService.findUserExist(friendAccount);
        boolean friendExist = userCheckService.findUserExist(friendAccount);
        if (userExist && friendExist) {
            Date now = new Date();
            Friend friend1 = new Friend(userAccount, friendAccount, now);
            friendRepository.save(friend1);
            Friend friend2 = new Friend(friendAccount, userAccount, now);
            friendRepository.save(friend2);
            return ResponseUtil.success(true);
        }
        return ResponseUtil.error(HttpStatus.NOT_FOUND.value(), "用户不存在");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResponseEntity<ApiResponse<Boolean>> deleteFriend(String userAccount, String friendAccount) {
        friendRepository.deleteByUserAccountAndFriendAccount(userAccount, friendAccount);
        friendRepository.deleteByUserAccountAndFriendAccount(friendAccount, userAccount);
        return ResponseUtil.success(true);
    }


    @Override
    public ResponseEntity<ApiResponse<List<FriendDetailResp>>> queryFriendDetail(String account) {
        List<FriendDetailResp> details = friendDao.queryFriendDetail(account);
        return ResponseUtil.success(details);
    }

}
