package page.se3.datingapp.onion.repository;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.model.struct.entity.ChatEntity;

import java.util.List;

public interface ChatRepository extends ListCrudRepository<@NonNull ChatEntity, @NonNull Long> {

    @Query("""
            SELECT * FROM chats
                WHERE
                    id_user_first = :idUser OR
                    id_user_second = :idUser
            """)
    List<ChatEntity> findByIdUser(Long idUser);
}
