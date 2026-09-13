DROP TABLE IF EXISTS blocks;
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS match_groups;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS chats;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
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
    quiz_answers VARCHAR(2047) NOT NULL,
    is_participating BOOLEAN NOT NULL,
    ready_index INT NOT NULL
);

CREATE TABLE chats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_user_first BIGINT NOT NULL,
    id_user_second BIGINT NOT NULL,
    is_upgraded BOOLEAN NOT NULL,

    CONSTRAINT fk_chat_user_first FOREIGN KEY (id_user_first) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_chat_user_second FOREIGN KEY (id_user_second) REFERENCES users(id) ON DELETE CASCADE,

    INDEX idx_chat_user_pair (id_user_first, id_user_second),
    INDEX idx_chat_user_second (id_user_second)
);

CREATE TABLE messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    message VARCHAR(2047) NOT NULL,
    send_time TIMESTAMP(3) NOT NULL,
    id_chat BIGINT NOT NULL,
    id_user_sender BIGINT NOT NULL,

    CONSTRAINT fk_message_chat FOREIGN KEY (id_chat) REFERENCES chats(id) ON DELETE CASCADE,
    CONSTRAINT fk_message_user FOREIGN KEY (id_user_sender) REFERENCES users(id) ON DELETE CASCADE,

    INDEX idx_message_chat (id_chat)
);

CREATE TABLE match_groups (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    match_time TIMESTAMP(3) NOT NULL,
    match_count_limit INT NOT NULL,
    ready_index INT NOT NULL
);

CREATE TABLE matches (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_user_main BIGINT NOT NULL,
    id_user_match BIGINT NOT NULL,
    score INT NOT NULL,
    id_match_group BIGINT NOT NULL,

    CONSTRAINT fk_match_user_main FOREIGN KEY (id_user_main) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_match_user_match FOREIGN KEY (id_user_match) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_match_match_group FOREIGN KEY (id_match_group) REFERENCES match_groups(id) ON DELETE CASCADE,

    INDEX idx_match_user_pair (id_user_main, id_user_match),
    INDEX idx_match_user_match (id_user_match),
    INDEX idx_match_match_group (id_match_group)
);

CREATE TABLE blocks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    id_user_owner BIGINT NOT NULL,
    id_user_blocked BIGINT NOT NULL,

    CONSTRAINT unq_block_user_pair UNIQUE (id_user_owner, id_user_blocked),

    CONSTRAINT fk_block_user_owner FOREIGN KEY (id_user_owner) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_block_user_blocked FOREIGN KEY (id_user_blocked) REFERENCES users(id) ON DELETE CASCADE,

    INDEX idx_block_user_blocked (id_user_blocked)
);
