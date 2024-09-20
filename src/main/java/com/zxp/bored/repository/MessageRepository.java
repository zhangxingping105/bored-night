package com.zxp.bored.repository;

import com.zxp.bored.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息JPA
 *
 * @description: 消息JPA
 * @author: zxp
 * @date: 2024/9/20 21:15
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByReceiptTimeIsNullAndReceiverAccount(String receiverAccount);
}
