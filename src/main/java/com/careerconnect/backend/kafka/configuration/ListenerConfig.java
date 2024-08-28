package com.careerconnect.backend.kafka.configuration;


import com.careerconnect.backend.kafka.constants.KafkaConstants;
import com.careerconnect.backend.kafka.model.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

// Kafka 메시지를 소비하기 위해 필요한 설정, 최신 메시지부터 소비
@EnableKafka
@Configuration
public class ListenerConfig {

    // 카프카 리스너 팩토리 생성: 카프카 토픽에서 메세지를 수신
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, Message> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Message> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 컨슈머 팩토리를 사용하여 메시지를 소비
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // 카프카에서 메시지를 읽어 올 때 사용할 '소비자' 설정
    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigurations(), new StringDeserializer(), new JsonDeserializer<>(Message.class));
    }

    // 카프카 소비자가 서버와 통신하는 방법 정의
    @Bean
    public Map<String, Object> consumerConfigurations() {
        Map<String, Object> configurations = new HashMap<>();
        // 카프카 브로커의 주소 설정
        configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.KAFKA_BROKER);
        // 소비자가 속한 그룹 설정 (동일한 그룹ID로 다른 소비자와 메시지 공유 가능)
        configurations.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConstants.GROUP_ID);
        configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configurations.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return configurations;
    }
}