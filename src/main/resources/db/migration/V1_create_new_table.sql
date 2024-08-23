-- 유저 테이블
CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(8) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- 채팅방 테이블
CREATE TABLE chats (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_type ENUM('SELF', 'PRIVATE', 'GROUP') NOT NULL,  -- 채팅 유형: 나에게 보내는 채팅, 일대일 채팅, 그룹 채팅
    name VARCHAR(20),                                    -- 채팅방 이름 (그룹 채팅일 경우에만 사용)
    description TEXT,                                     -- 채팅방 설명 (그룹 채팅일 경우에만 사용)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_private BOOLEAN DEFAULT FALSE,                     -- 비공개 채팅방 여부
    created_by BIGINT,                                    -- 채팅방 생성자 (일대일 채팅 또는 그룹 채팅일 경우)
    FOREIGN KEY (created_by) REFERENCES user(id) ON DELETE SET NULL
);

-- 메시지 테이블
CREATE TABLE messages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    chat_id BIGINT,                                       -- chats 테이블의 외래 키
    sender_id BIGINT,                                     -- 메시지 보낸 사용자
    content TEXT NOT NULL,                                -- 메시지 내용
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES user(id) ON DELETE CASCADE
);

-- 유저-채팅방 관계 테이블
CREATE TABLE user_chat (
    user_id BIGINT,
    chat_id BIGINT,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(20) DEFAULT 'member',                   -- 사용자의 채팅방 역할 (ex: admin, member)
    PRIMARY KEY (user_id, chat_id),
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);
