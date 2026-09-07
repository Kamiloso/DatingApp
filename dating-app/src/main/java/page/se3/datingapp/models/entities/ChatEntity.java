package page.se3.datingapp.models.entities;

import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@With
@Table("chat")
public record ChatEntity(
        @Id Long id,
        Long idUserFirst,
        Long idUserSecond,
        Boolean isUpgraded
) {}
