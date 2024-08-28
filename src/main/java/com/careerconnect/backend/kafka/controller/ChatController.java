package com.careerconnect.backend.kafka.controller;


import com.careerconnect.backend.kafka.constants.KafkaConstants;
import com.careerconnect.backend.kafka.model.Message;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

/**
* Kafka를 통해 메시지를 전송하고 WebSocket을 통해 실시간으로 메시지를 주고받는 기능을 제공
* 이 클래스는 REST API와 WebSocket 메시지 핸들러를 결합하여 채팅 애플리케이션의 핵심 기능을 구현
**/

 @RestController
public class ChatController {

    @Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    // /api/send 엔드포인트에 POST 요청이 들어올 때 호출
    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody Message message, HttpServletRequest request) {

        // RequestContextHolder에서 username을 가져오기
        String username = (String) request.getAttribute("username");
        if (username != null) {
            message.setSender(username); // Message 객체에 username 설정
        } else {
            throw new RuntimeException("사용자 정보가 없습니다.");
        }

        // 메시지 객체에 현재 시간을 타임스탬프로 설정
        message.setTimestamp(LocalDateTime.now().toString());
        try {
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
    *    -------------- WebSocket API ----------------
    * WebSocket을 통해 /sendMessage 경로로 들어오는 메시지를 처리
    * 이 메서드가 반환하는 메시지는 /topic/group 주제를 구독하는 모든 클라이언트에게 전송
    **/

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        return message;
    }

    // 이 메서드는 새로운 사용자가 WebSocket 연결을 맺을 때 호출
    @MessageMapping("/newUser")
    @SendTo("/topic/group")
    // 새로운 사용자의 정보를 WebSocket 세션에 추가
    public Message addUser(@Payload Message message,
                           SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

}
/**
* 이 클래스는 메시지를 Kafka를 통해 백엔드에 전달하고, WebSocket을 통해 클라이언트 간 실시간 메시징을 지원
* sendMessage 메서드는 REST API 엔드포인트로, 클라이언트가 POST 요청을 통해 메시지를 보내면 이를 Kafka 토픽에 전송합니다.
* broadcastGroupMessage 메서드는 WebSocket을 통해 모든 구독자에게 메시지를 브로드캐스트합니다.
* addUser 메서드는 새로운 사용자가 WebSocket에 연결될 때 해당 사용자의 정보를 세션에 저장하고 이를 알립니다
* 이 클래스를 통해 실시간 채팅 기능을 구현할 수 있으며, Kafka를 이용한 메시지 큐잉과 WebSocket을 통한 실시간 통신이 결합된 형태로 동작합니다.
 **/