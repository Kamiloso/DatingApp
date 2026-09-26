package page.se3.datingapp.infrastructure.jdbc.database.user;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserJdbcRepository extends ListCrudRepository<@NonNull UserEntity, @NonNull Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("""
            SELECT * FROM users
                WHERE
                    matching_index = :matchingIndex AND
                    ready_index = :readyIndex
                FOR SHARE
            """)
    List<UserEntity> findAllWhereReadyForShare(Integer matchingIndex, Integer readyIndex);
}
