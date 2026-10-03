package page.se3.datingapp.infrastructure.database.jdbc.match;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface MatchJdbcRepository extends ListCrudRepository<@NonNull MatchEntity, @NonNull Long> {

    @Query("""
            SELECT * FROM matches
                WHERE
                    (
                        (matches.id_user_first = :idUser AND matches.id_user_second = :idUserOther) OR
                        (matches.id_user_first = :idUserOther AND matches.id_user_second = :idUser)
                    ) AND
            """ + Preset.PASSES_BLOCKED_OR_IS_NOT_BLOCKED)
    Optional<MatchEntity> findByIdUserDirectForApplicationPurposes(Long idUser, Long idUserOther, Boolean blocked);

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
                    ) AND
            """ + Preset.PASSES_BLOCKED_OR_IS_NOT_BLOCKED)
    List<MatchEntity> findAllByIdUserForApplicationPurposes(Long idUser, Boolean blocked);

    @Modifying
    @Query("""
            UPDATE matches
                SET
                    is_applied_first = CASE WHEN id_user_first = :idUser
                        THEN :isApplied
                        ELSE is_applied_first
                    END,
                    is_applied_second = CASE WHEN id_user_second = :idUser
                        THEN :isApplied
                        ELSE is_applied_second
                    END
                WHERE id = :id
            """)
    void updateIsAppliedOfIdUserById(Long id, Long idUser, Boolean isApplied);

    class Preset {
        private static final String PASSES_BLOCKED_OR_IS_NOT_BLOCKED = """
            (
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
            """;
    }
}
