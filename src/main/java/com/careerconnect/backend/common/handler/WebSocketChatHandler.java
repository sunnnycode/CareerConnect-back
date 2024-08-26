// WebSocketChatHandler.java
package com.careerconnect.backend.common.handler;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public WebSocketChatHandler(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        kafkaTemplate.send("chat_topic", payload);
        session.sendMessage(new TextMessage("Message sent to Kafka: " + payload));
    }
}
