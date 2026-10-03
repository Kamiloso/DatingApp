package page.se3.datingapp.infrastructure.database.jdbc.global;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface GlobalJdbcRepository extends ListCrudRepository<@NonNull GlobalEntity, @NonNull String> {

    @Query("SELECT * FROM globals WHERE col_key = :id FOR UPDATE")
    Optional<GlobalEntity> findByIdForUpdate(String id);

    @Modifying
    @Query("""
            INSERT INTO globals (col_key, col_value)
                VALUES (:key, :value)
                ON DUPLICATE KEY UPDATE col_value = :value
            """)
    void saveOrReplace(String key, String value);
}
