package page.se3.datingapp.application.service.message.repository;

import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Optional<Message> findByIdAndIdChat(Long id, Long idChat);

    List<Message> findAllByIdChatForApplicationPurposes(Long idChat, Instant lastFetchTime, Integer maxListSize);

    Message trySave(Message message) throws DatabaseConflictException;
}
