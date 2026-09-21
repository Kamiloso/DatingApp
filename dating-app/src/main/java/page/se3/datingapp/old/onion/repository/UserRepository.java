package page.se3.datingapp.old.onion.repository;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.old.model.struct.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<@NonNull UserEntity, @NonNull Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("""
            SELECT * FROM users
                WHERE
                    ready_index = :readyIndex AND
                    is_participating = 1
                FOR UPDATE
            """)
    List<UserEntity> findByReadyIndexWhereIsParticipatingForUpdate(Integer readyIndex);

    @Modifying
    @Query("""
            UPDATE users
                SET is_participating = 0
                WHERE id IN (:idList)
            """)
    void resetIsParticipatingByIdList(List<Long> idList);
}
