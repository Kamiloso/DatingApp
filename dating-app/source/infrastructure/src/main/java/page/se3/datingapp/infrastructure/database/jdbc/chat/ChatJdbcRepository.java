package page.se3.datingapp.infrastructure.database.jdbc.chat;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatJdbcRepository extends ListCrudRepository<@NonNull ChatEntity, @NonNull Long> {

    Optional<ChatEntity> findByIdMatch(Long idMatch);

    @Query("SELECT * FROM chats WHERE id_match IN (:idMatchList)")
    List<ChatEntity> findAllByIdMatchList(List<Long> idMatchList);

    @Query("""
            INSERT IGNORE INTO chats (begin_time, is_upgraded, id_match)
                VALUES (
                    :#{#chatEntity.beginTime},
                    :#{#chatEntity.isUpgraded},
                    :#{#chatEntity.idMatch}
                )
            """)
    void saveIfNotExists(ChatEntity chatEntity);
}
