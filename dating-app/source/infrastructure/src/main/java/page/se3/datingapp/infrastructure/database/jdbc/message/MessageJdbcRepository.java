package page.se3.datingapp.infrastructure.database.jdbc.message;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MessageJdbcRepository extends ListCrudRepository<@NonNull MessageEntity, @NonNull Long> {

    Optional<MessageEntity> findByIdAndIdChat(Long id, Long idChat);

    @Query("""
            SELECT * FROM messages
                WHERE id_chat = :idChat AND send_time > :sendTime
                ORDER BY send_time
                LIMIT :limit
            """)
    List<MessageEntity> findAllByIdChatForApplicationPurposes(Long idChat, Instant sendTime, Integer limit);
}
