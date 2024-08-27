DROP TABLE IF EXISTS user_chat;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS chats;
DROP TABLE IF EXISTS users;

-- 유저 테이블
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(50) NOT NULL,
    username VARCHAR(20) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- 채팅방 테이블
CREATE TABLE chats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    chat_type ENUM('SELF', 'PRIVATE', 'GROUP') NOT NULL,
    name VARCHAR(20),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_private BOOLEAN DEFAULT FALSE,
    created_by INT
);

-- 메시지 테이블
CREATE TABLE messages (
    id INT AUTO_INCREMENT PRIMARY KEY,
    chat_id INT,
    sender_id INT,
    content TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE
);

-- 유저-채팅방 관계 테이블
CREATE TABLE user_chat (
    user_id INT,
    chat_id INT,
    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    role VARCHAR(20) DEFAULT 'member',
    PRIMARY KEY (user_id, chat_id)
);

-- 외래 키 제약 조건 추가
ALTER TABLE chats ADD CONSTRAINT FK_chats_created_by FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL;

ALTER TABLE messages ADD CONSTRAINT FK_messages_chat_id FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE;
ALTER TABLE messages ADD CONSTRAINT FK_messages_sender_id FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_chat ADD CONSTRAINT FK_user_chat_user_id FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
ALTER TABLE user_chat ADD CONSTRAINT FK_user_chat_chat_id FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE;
