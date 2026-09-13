package page.se3.datingapp.model.struct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("messages")
public record MessageEntity(
        @Id Long id,
        String message,
        Instant sendTime,
        Long idUserSender,
        Long idChat
) {}
