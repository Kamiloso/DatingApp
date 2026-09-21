DROP TABLE IF EXISTS blocks;
DROP TABLE IF EXISTS messages;
DROP TABLE IF EXISTS chats;
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS match_groups;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
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
    ready_index INT NOT NULL,

    INDEX idx_user_ready_indicator (is_participating, ready_index)
);

CREATE TABLE match_groups (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    match_time TIMESTAMP(3) NOT NULL,
    match_count_limit INT NOT NULL,
    ready_index INT NOT NULL
);

CREATE TABLE matches (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_user_first BIGINT NOT NULL,
    id_user_second BIGINT NOT NULL,
    score INT NOT NULL,
    is_double_match BOOLEAN NOT NULL,
    is_applied_by_first BOOLEAN NOT NULL,
    is_applied_by_second BOOLEAN NOT NULL,
    id_match_group BIGINT NOT NULL,

    CONSTRAINT unq_match_user_pair UNIQUE (id_user_first, id_user_second),
    CONSTRAINT chk_match_user_pair CHECK (id_user_first < id_user_second),

    CONSTRAINT fk_match_user_first FOREIGN KEY (id_user_first) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_match_user_second FOREIGN KEY (id_user_second) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_match_match_group FOREIGN KEY (id_match_group) REFERENCES match_groups(id) ON DELETE CASCADE
);

CREATE TABLE chats (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_user_first BIGINT NOT NULL,
    id_user_second BIGINT NOT NULL,
    begin_time TIMESTAMP(3) NOT NULL,
    is_upgraded BOOLEAN NOT NULL,

    CONSTRAINT unq_chat_user_pair UNIQUE (id_user_first, id_user_second),
    CONSTRAINT chk_chat_user_pair CHECK (id_user_first < id_user_second),

    CONSTRAINT fk_chat_user_first FOREIGN KEY (id_user_first) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_chat_user_second FOREIGN KEY (id_user_second) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    message VARCHAR(2047) NOT NULL,
    send_time TIMESTAMP(3) NOT NULL,
    id_chat BIGINT NOT NULL,
    id_user_sender BIGINT NOT NULL,

    CONSTRAINT fk_message_chat FOREIGN KEY (id_chat) REFERENCES chats(id) ON DELETE CASCADE,
    CONSTRAINT fk_message_user FOREIGN KEY (id_user_sender) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE blocks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    id_user_owner BIGINT NOT NULL,
    id_user_blocked BIGINT NOT NULL,

    CONSTRAINT unq_block_user_pair UNIQUE (id_user_owner, id_user_blocked),

    CONSTRAINT fk_block_user_owner FOREIGN KEY (id_user_owner) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_block_user_blocked FOREIGN KEY (id_user_blocked) REFERENCES users(id) ON DELETE CASCADE
);
