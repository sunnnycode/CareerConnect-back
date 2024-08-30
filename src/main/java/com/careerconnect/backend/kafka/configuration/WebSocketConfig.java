package com.careerconnect.backend.kafka.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


// WebSocket을 통해 실시간 메시징 기능을 구현하는 데 필요한 설정
// STOMP 프로토콜을 사용하여 클라이언트와 서버 간의 메시지 전송을 관리
@Configuration
// WebSocket 메시지 브로커(처리)를 활성화
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // WebSocket 엔드포인트를 등록
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 엔드포인트: /ws-chat
        // withSockJS(): 클라이언트가 WebSocket을 사용할 수 없는 경우 대체 하기 위함
        registry.addEndpoint("/ws-chat").setAllowedOrigins("http://localhost:3000").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // app 경로: 클라이언트가 서버로 메시지를 보낼 때 이 경로를 사용해야 함
        registry.setApplicationDestinationPrefixes("/app");
        // topic 경로: 클라이언트는 /topic/으로 시작하는 경로를 구독하여 메시지를 받을 수 있음
        registry.enableSimpleBroker("/topic/");
    }
}
