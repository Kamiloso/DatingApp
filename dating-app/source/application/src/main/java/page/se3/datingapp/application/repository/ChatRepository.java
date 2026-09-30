package page.se3.datingapp.application.repository;

import page.se3.datingapp.domain.model.relation.chat.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository {

    Optional<Chat> findById(Long id);

    Optional<Chat> findByIdMatch(Long id);

    List<Chat> findAllByIdMatchList(List<Long> idMatchList);

    void createIfNotExists(Chat chat);
}
