package page.se3.datingapp.old.model.struct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("chats")
public record ChatEntity(
        @Id Long id,
        Long idUserFirst,
        Long idUserSecond,
        Instant beginTime,
        Boolean isUpgraded
) {}
