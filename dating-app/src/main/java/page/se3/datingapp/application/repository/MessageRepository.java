package page.se3.datingapp.application.repository;

import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;
import java.util.List;

public interface MessageRepository {

    List<Message> findAllByIdChatForApplicationPurposes(Long idChat, Instant lastFetchTime, Integer maxListSize);

    Message trySave(Message message) throws DataIntegrityViolation;
}
