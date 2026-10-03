package page.se3.datingapp.infrastructure.database.jdbc.user;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserJdbcRepository extends ListCrudRepository<@NonNull UserEntity, @NonNull Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT * FROM users WHERE id = :id FOR UPDATE")
    Optional<UserEntity> findByIdForUpdate(Long id);
    
    @Query("SELECT * FROM users WHERE id = :id AND is_active = 1")
    Optional<UserEntity> findByIdWhereIsActive(Long id);

    @Query("""
            SELECT * FROM users
                WHERE
                    matching_index = :matchingIndex AND
                    is_active = 1
            """)
    List<UserEntity> findAllWhereReady(Integer matchingIndex);
}
