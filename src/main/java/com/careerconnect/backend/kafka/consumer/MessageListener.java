package com.careerconnect.backend.kafka.consumer;


import com.careerconnect.backend.kafka.constants.KafkaConstants;
import com.careerconnect.backend.kafka.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

//  @KafkaListener를 사용하여 Kafka 토픽에서 메시지를 수신하고, 수신된 메시지를 WebSocket을 통해 클라이언트에게 전달하는 로직
@Component
public class MessageListener {


    // WebSocket을 통해 클라이언트에게 메시지를 전송할 때 사용
    @Autowired
    SimpMessagingTemplate template;

    // 지정된 토픽에서 메시지가 들어올 때마다 해당 메서드가 호출
    @KafkaListener(
            // 구독할 토픽을 지정
            topics = KafkaConstants.KAFKA_TOPIC,
            // 이 리스너가 속한 Kafka 소비자 그룹을 지정
            groupId = KafkaConstants.GROUP_ID
    )
    //  Kafka 토픽에서 메시지를 수신할 때 호출
    public void listen(Message message) {
        System.out.println("sending via kafka listener..");
        // /topic/group 경로를 구독하는 클라이언트들에게 전달
        template.convertAndSend("/topic/group", message);
    }
}

 /**
 * Kafka 메시지 수신: @KafkaListener가 설정된 listen 메서드가 지정된 Kafka 토픽에서 메시지를 수신합니다.
 * 로그 출력: 메시지가 수신되었음을 콘솔에 출력합니다.
 * WebSocket 메시지 전송: 수신된 메시지를 SimpMessagingTemplate를 사용하여 WebSocket을 통해 /topic/group 주제를 구독하는 모든 클라이언트에게 전송합니다.
  **/