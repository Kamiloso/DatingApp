package page.se3.datingapp.onion.repository;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.model.struct.entity.MessageEntity;

import java.time.Instant;
import java.util.List;

public interface MessageRepository extends ListCrudRepository<@NonNull MessageEntity, @NonNull Long> {

    @Query("""
            SELECT * FROM messages
                WHERE id_chat = :idChat AND send_time > :sendTime
                ORDER BY send_time
                LIMIT :limit
            """)
    List<MessageEntity> findByIdChatAfterSendTimeWithLimit(Long idChat, Instant sendTime, Integer limit);
}
