package page.se3.datingapp.model.struct.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("matches")
public record MatchEntity(
        @Id Long id,
        Long idUserFirst,
        Long idUserSecond,
        Integer score,
        Boolean isDoubleMatch,
        Boolean isAppliedByFirst,
        Boolean isAppliedBySecond,
        Long idMatchGroup
) {}
