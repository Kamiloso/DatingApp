package page.se3.datingapp.repositories;

import lombok.NonNull;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.models.entities.ChatEntity;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends ListCrudRepository<@NonNull ChatEntity, @NonNull Long> {

    @Query("""
            SELECT * FROM chat
                WHERE
                    id_user_first = :idUser OR
                    id_user_second = :idUser
            """)
    List<ChatEntity> findByIdUser(Long idUser);

    @Query("""
            SELECT * FROM chat
                WHERE
                    (id_user_first = :idUser1 AND id_user_second = :idUser2) OR
                    (id_user_first = :idUser2 AND id_user_second = :idUser1)
            """)
    Optional<ChatEntity> findByIdUserPair(
            Long idUser1,
            Long idUser2
    );
}
