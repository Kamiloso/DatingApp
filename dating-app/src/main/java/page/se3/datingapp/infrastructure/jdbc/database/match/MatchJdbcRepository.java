package page.se3.datingapp.infrastructure.jdbc.database.match;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface MatchJdbcRepository extends ListCrudRepository<@NonNull MatchEntity, @NonNull Long> {

    @Query("""
            SELECT * FROM matches
                WHERE
                    id_user_first IN (:idUserList) OR
                    id_user_second IN (:idUserList)
            """)
    List<MatchEntity> findAllByIdUserList(List<Long> idUserList);

    @Query("""
            SELECT * FROM matches
                WHERE
                    (
                        matches.id_user_first = :idUser OR
                        matches.id_user_second = :idUser
                    ) AND (
                        :blocked = 1 OR
                        NOT EXISTS (
                            SELECT 1 FROM blocks
                                WHERE
                                    (
                                        (
                                            blocks.id_user_owner = matches.id_user_first AND
                                            blocks.id_user_blocked = matches.id_user_second
                                        ) OR (
                                            blocks.id_user_owner = matches.id_user_second AND
                                            blocks.id_user_blocked = matches.id_user_first
                                        )
                                    ) AND (
                                        blocks.id_user_owner = :idUser OR
                                        (
                                            blocks.id_user_blocked = :idUser AND
                                            blocks.is_silent = 0
                                        )
                                    )
                        )
                    )
            """)
    List<MatchEntity> findAllByIdUserForApplicationPurposes(Long idUser, Boolean blocked);

    @Modifying
    @Query("""
            UPDATE matches
                SET is_applied_by_first = :isAppliedByFirst
                WHERE id = :id
            """)
    void updateIsAppliedByFirstById(Long id, Boolean isAppliedByFirst);

    @Modifying
    @Query("""
            UPDATE matches
                SET is_applied_by_second = :isAppliedBySecond
                WHERE id = :id
            """)
    void updateIsAppliedBySecondById(Long id, Boolean isAppliedBySecond);
}
