package page.se3.datingapp.model.struct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("chats")
public record ChatEntity(
        @Id Long id,
        Long idUserFirst,
        Long idUserSecond,
        Boolean isUpgraded
) {}
