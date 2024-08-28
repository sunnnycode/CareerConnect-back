package com.careerconnect.backend.kafka.constants;


public class KafkaConstants {
    // 토픽의 이름을 정의
    public static final String KAFKA_TOPIC = "kafka-chat-3";
    // Kafka 소비자가 속한 그룹 ID를 정의, 동일한 그룹에 속한 소비자들은 메시지를 분산하여 처리 가능
    public static final String GROUP_ID = "kafka-sandbox";
    // Kafka 브로커의 주소를 정의, Kafka 클러스터 내에서 메시지를 주고받을 때 사용되는 브로커의 위치를 지정
    public static final String KAFKA_BROKER = "localhost:9092";
}
