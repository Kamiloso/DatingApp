package page.se3.datingapp.infrastructure.database.jdbc.account;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

public interface AccountJdbcRepository extends ListCrudRepository<@NonNull AccountEntity, @NonNull String> {

    @Modifying
    @Query("""
            INSERT INTO accounts (email, password_hash, is_banned)
                VALUES (
                    :#{#accountEntity.email},
                    :#{#accountEntity.passwordHash},
                    :#{#accountEntity.isBanned}
                ) ON DUPLICATE KEY UPDATE
                    password_hash = VALUES(password_hash),
                    is_banned = VALUES(is_banned)
            """)
    void insertOrReplace(AccountEntity accountEntity);
}
