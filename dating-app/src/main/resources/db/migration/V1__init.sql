DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS chat;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    gender ENUM('MAN', 'WOMAN', 'OTHER') NOT NULL,
    age INT NOT NULL,
    field_of_study VARCHAR(255) NOT NULL,
    description VARCHAR(2047) NOT NULL,
    target_gender ENUM('MAN', 'WOMAN', 'OTHER', 'ANY') NOT NULL,
    target_relationship ENUM('FRIENDSHIP', 'ROMANTIC', 'BOTH') NOT NULL,
    quiz_answers VARCHAR(255) NOT NULL,
    is_participating BOOLEAN NOT NULL,
    ready_index INT NOT NULL
);

CREATE TABLE chat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_user_first BIGINT NOT NULL,
    id_user_second BIGINT NOT NULL,
    is_upgraded BOOLEAN NOT NULL,
    CONSTRAINT fk_chat_user_first FOREIGN KEY (id_user_first) REFERENCES user(id) ON DELETE CASCADE,
    CONSTRAINT fk_chat_user_second FOREIGN KEY (id_user_second) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE message (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    message VARCHAR(2047) NOT NULL,
    send_time TIMESTAMP NOT NULL,
    id_chat BIGINT NOT NULL,
    id_user_sender BIGINT NOT NULL,
    CONSTRAINT fk_message_chat FOREIGN KEY (id_chat) REFERENCES chat(id) ON DELETE CASCADE,
    CONSTRAINT fk_message_user FOREIGN KEY (id_user_sender) REFERENCES user(id) ON DELETE CASCADE
);

CREATE INDEX idx_user_email ON user(email);
CREATE INDEX idx_chat_user_pair ON chat(id_user_first, id_user_second);
CREATE INDEX idx_chat_user_second ON chat(id_user_second);
CREATE INDEX idx_message_chat ON message(id_chat);