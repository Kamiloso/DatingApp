package page.se3.datingapp.models.entities;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@With
@Table("message")
public record MessageEntity(
        @Id Long id,
        String message,
        Instant sendTime,
        Long idUserSender,
        Long idChat
) {}
