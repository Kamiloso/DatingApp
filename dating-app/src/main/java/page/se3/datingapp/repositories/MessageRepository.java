package page.se3.datingapp.repositories;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.models.entities.MessageEntity;

import java.time.Instant;
import java.util.List;

public interface MessageRepository extends ListCrudRepository<@NonNull MessageEntity, @NonNull Long> {

    @Query("""
            SELECT * FROM message
                WHERE id_chat = :idChat AND send_time > :sendTime
                ORDER BY send_time
                LIMIT :limit
            """)
    List<MessageEntity> findByIdChatAfterSendTimeWithLimit(
            Long idChat,
            Instant sendTime,
            int limit
    );
}
