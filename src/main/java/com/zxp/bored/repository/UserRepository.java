package com.zxp.bored.repository;

import com.zxp.bored.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户JPA
 *
 * @description: 用户JPA
 * @author: zxp
 * @date: 2024/9/20 21:08
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByAccount(String account);

    User findUserByUsername(String username);
}
