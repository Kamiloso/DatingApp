package page.se3.datingapp.infrastructure.database.jdbc.matchgroup;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("match_groups")
public record MatchGroupEntity(
        @Id Long id,
        Instant matchTime,
        Integer matchCountLimit,
        Integer readyIndex
) {}
