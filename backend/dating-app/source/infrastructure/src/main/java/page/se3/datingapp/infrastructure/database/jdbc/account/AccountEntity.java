package page.se3.datingapp.infrastructure.database.jdbc.account;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("accounts")
public record AccountEntity(
        @Id String email,
        String passwordHash,
        Boolean isBanned
) {}
