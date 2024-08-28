package com.careerconnect.backend.kafka.model;

// 채팅 애플리케이션에서 주고받는 메시지
public class Message {
    private String sender;
    private String content;
    private String timestamp;

    // 파라미터가 없는 기본 생성자로, 새로운 Message 객체를 초기화할 때 사용
    public Message() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    // sender와 content 필드를 초기화, 메시지를 생성할 때 발신자와 내용을 지정
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
