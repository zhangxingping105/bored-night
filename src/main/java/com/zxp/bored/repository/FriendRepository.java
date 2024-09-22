package com.zxp.bored.repository;

import com.zxp.bored.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 好友JPA
 *
 * @description: 好友JPA
 * @author: zxp
 * @date: 2024/9/20 21:54
 */
@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    int deleteByUserAccountAndFriendAccount(String userAccount, String friendAccount);
    int countByUserAccountAndFriendAccount(String userAccount, String friendAccount);
}
