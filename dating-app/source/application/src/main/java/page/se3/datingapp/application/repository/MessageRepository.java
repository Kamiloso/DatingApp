package page.se3.datingapp.application.repository;

import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;
import java.util.List;

public interface MessageRepository {

    List<Message> findAllByIdChatForApplicationPurposes(Long idChat, Instant lastFetchTime, Integer maxListSize);

    Message trySave(Message message) throws DatabaseConflictException;
}
