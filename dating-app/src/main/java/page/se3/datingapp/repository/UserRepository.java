package page.se3.datingapp.repository;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.model.struct.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends ListCrudRepository<@NonNull UserEntity, @NonNull Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query("""
            SELECT * FROM users
                WHERE
                    is_participating = 1 AND
                    ready_index = :readyIndex
                FOR UPDATE
            """)
    List<UserEntity> findForUpdateByReadyIndexWhereIsParticipating(Integer readyIndex);

    @Modifying
    @Query("""
            UPDATE users
                SET is_participating = 0
                WHERE id IN (:idList)
            """)
    void resetIsParticipatingByIdList(List<Long> idList);
}
