package page.se3.datingapp.repositories;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.models.entities.UserEntity;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<@NonNull UserEntity, @NonNull Long> {

    Optional<UserEntity> findByEmail(String email);

    @Modifying
    @Query("""
            UPDATE user
                SET is_participating = :isParticipating
                WHERE id = :id
            """)
    void updateIsParticipating(Long id, Boolean isParticipating);
}
