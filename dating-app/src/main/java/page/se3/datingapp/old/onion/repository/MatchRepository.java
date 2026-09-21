package page.se3.datingapp.old.onion.repository;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.old.model.struct.entity.MatchEntity;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends ListCrudRepository<@NonNull MatchEntity, @NonNull Long> {

    @Query("SELECT * FROM matches WHERE id = :id FOR UPDATE")
    Optional<MatchEntity> findByIdForUpdate(Long id);

    @Query("""
            SELECT * FROM matches
                WHERE (id_user_first = :idUser OR id_user_second = :idUser)
            """)
    List<MatchEntity> findByIdUser(Long idUser);

    @Query("""
            UPDATE matches
                SET is_applied_by_first = :isAppliedByFirst
                WHERE id = :id
            """)
    void updateIsAppliedByFirstById(Long id, Boolean isAppliedByFirst);

    @Query("""
            UPDATE matches
                SET is_applied_by_second = :isAppliedBySecond
                WHERE id = :id
            """)
    void updateIsAppliedBySecondById(Long id, Boolean isAppliedBySecond);
}
