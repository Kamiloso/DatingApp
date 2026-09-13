package page.se3.datingapp.model.struct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("matches")
public record MatchEntity(
        @Id Long id,
        Long idUserMain,
        Long idUserMatch,
        Integer score,
        Long idMatchGroup
) {}
