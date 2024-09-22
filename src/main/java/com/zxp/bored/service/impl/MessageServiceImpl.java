package com.zxp.bored.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zxp.bored.entity.Message;
import com.zxp.bored.repository.MessageRepository;
import com.zxp.bored.service.MessageService;
import com.zxp.bored.service.UserCheckService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 消息服务实现
 *
 * @description: 消息服务实现
 * @author: zxp
 * @date: 2024/9/15 21:04
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private UserCheckService userCheckService;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public boolean addMessage(String senderAccount, String receiverAccount, String content) {
        if (!userCheckService.findUserExist(senderAccount)) {
            return false;
        }
        if (!userCheckService.findUserExist(receiverAccount)) {
            return false;
        }
        Message message = new Message();
        message.setSenderAccount(senderAccount);
        message.setReceiverAccount(receiverAccount);
        message.setContent(content);
        message.setCreateTime(new Date());
        messageRepository.save(message);
        return true;
    }

    @Override
    public Map<Long, String> getUnsentMessages(String account) throws JsonProcessingException {
        List<Message> unsentMessages = messageRepository.findAllByReceiptTimeIsNullAndReceiverAccount(account);
        Map<Long, String> messageMap = new HashMap<>();
        if (unsentMessages == null) {
            return messageMap;
        }
        for (Message message : unsentMessages) {
            messageMap.put(message.getId(), objectMapper.writeValueAsString(message));
        }
        return messageMap;
    }

    @Override
    public void setReceiptTime(Long messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setReceiptTime(new Date());
            messageRepository.save(message);
        }
    }
}
