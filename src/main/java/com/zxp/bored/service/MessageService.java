package com.zxp.bored.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zxp.bored.entity.Message;

import java.util.List;
import java.util.Map;

/**
 * 消息服务
 *
 * @description: 消息服务
 * @author: zxp
 * @date: 2024/9/15 21:04
 */
public interface MessageService {
    /**
     * @description  保存消息
     * @param senderAccount  发送账号
     * @param receiverAccount 接收账号
     * @param content 消息
     * @return 保存结果
     * @date 2024/9/16 10:07
     */
    boolean addMessage(String senderAccount, String receiverAccount, String content);

    /**
     * @description 获取没发送的消息
     * @param userId 用户id
     * @return 消息
     * @date 2024/9/16 10:08
     */
    Map<Long, String> getUnsentMessages(String account) throws JsonProcessingException;

    /**
     * @description 设置接收时间
     * @param message 消息内容
     * @date 2024/9/16 10:19
     */
    void setReceiptTime(Long messageId);
}
