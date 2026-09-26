package page.se3.datingapp.infrastructure.jdbc.database.block;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface BlockJdbcRepository extends ListCrudRepository<@NonNull BlockEntity, @NonNull Long> {

    List<BlockEntity> findAllByIdUserOwner(Long idUserOwner);

    @Query("""
            SELECT * FROM blocks
                WHERE
                    id_user_blocked = :idUserBlocked AND
                    is_silent = 0
            """)
    List<BlockEntity> findAllByIdUserBlockedWhereNotSilent(Long idUserBlocked);

    @Query("""
            SELECT * FROM matches
                WHERE
                    id_user_owner IN (:idUserList) OR
                    id_user_blocked IN (:idUserList)
            """)
    List<BlockEntity> findAllByIdUserList(List<Long> idUserList);
}
