package page.se3.datingapp.infrastructure.database.jdbc.chat;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("chats")
public record ChatEntity(
        @Id Long id,
        Instant beginTime,
        Boolean isUpgraded,
        Long idMatch
) {}
