package com.zxp.bored.handler;

import com.zxp.bored.service.MessageService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zxp
 * @date: 2024/9/12 21:48
 */
@Slf4j
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final MessageService messageService;

    public ChatWebSocketHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userAccount = getUserAccount(session);
        if (userAccount != null) {
            log.info("用户建立连接：" + userAccount);
            sessions.put(userAccount, session);
            sendOfflineMessage(userAccount);
        }

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String payload = message.getPayload();
        // 解析消息，假设消息格式为 "receiverId:messageContent"
        String[] parts = payload.split("\u0001", 2);
        if (parts.length == 2) {
            String receiverAccount = parts[0];
            String messageContent = parts[1];

            // 消息发送直接保存数据库，等待用户登录后再次推送
            sendOnlineMessage(session, receiverAccount, messageContent);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userAccount = getUserAccount(session);
        if (userAccount != null) {
            log.info("用户退出连接：" + userAccount);
            sessions.remove(userAccount);
        }
    }

    private String getUserAccount(WebSocketSession session) {
        Map<String, List<String>> params = getParams(session);
        return params.get("account") == null ? null : params.get("account").get(0);
    }

    private Map<String, List<String>> getParams(WebSocketSession session) {
        MultiValueMap<String, String> params = UriComponentsBuilder.fromUriString(session.getUri().toString()).build()
            .getQueryParams();
        return params;
    }

    private void sendOfflineMessage(String account) throws IOException {
        WebSocketSession receiverSession = sessions.get(account);
        if (receiverSession != null && receiverSession.isOpen()) {
            Map<Long, String> unsentMap = messageService.getUnsentMessages(account);
            unsentMap.forEach((k, v) -> {
                try {
                    receiverSession.sendMessage(new TextMessage(v));
                    messageService.setReceiptTime(k);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void sendOnlineMessage(WebSocketSession session, String receiverAccount, String messageContent) throws IOException {
        String userAccount = getUserAccount(session);
        messageService.addMessage(userAccount, receiverAccount, messageContent);
        sendOfflineMessage(receiverAccount);
    }
}
