package page.se3.datingapp.infrastructure.database.jdbc.global;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("globals")
public record GlobalEntity(
        @Id String colKey,
        String colValue
) {}
