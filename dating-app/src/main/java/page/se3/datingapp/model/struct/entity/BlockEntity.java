package page.se3.datingapp.model.struct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("blocks")
public record BlockEntity(
        @Id Long id,
        Long idUserOwner,
        Long idUserBlocked
) {}
