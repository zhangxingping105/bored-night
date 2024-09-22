package com.zxp.bored.dao;

import com.zxp.bored.model.response.FriendDetailResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好友Mapper
 *
 * @description: 好友Mapper
 * @author: zxp
 * @date: 2024/9/20 23:35
 */

@Mapper
public interface FriendDao {
    List<FriendDetailResp> queryFriendDetail(@Param("account") String account);
}
