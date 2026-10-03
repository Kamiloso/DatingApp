package page.se3.datingapp.infrastructure.database.jdbc.block;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("blocks")
public record BlockEntity(
        @Id Long id,
        Long idUserOwner,
        Long idUserBlocked,
        Boolean isSilent
) {}
