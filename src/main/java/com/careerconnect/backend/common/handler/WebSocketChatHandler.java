package com.careerconnect.backend.common.handler;

import com.careerconnect.backend.common.producer.KafkaProducer;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final KafkaProducer kafkaProducer;

    public WebSocketChatHandler(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        kafkaProducer.sendMessage("chat_topic", payload);
        session.sendMessage(new TextMessage("Message sent to Kafka: " + payload));
    }
}
