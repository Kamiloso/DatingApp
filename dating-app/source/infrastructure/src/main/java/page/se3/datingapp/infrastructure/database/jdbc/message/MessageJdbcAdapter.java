package page.se3.datingapp.infrastructure.database.jdbc.message;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.message.repository.MessageRepository;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageJdbcAdapter implements MessageRepository {

    private final MessageJdbcRepository messageJdbcRepository;
    private final MessageMapper messageMapper;

    @Override
    public Optional<Message> findByIdAndIdChat(Long id, Long idChat) {
        return messageJdbcRepository.findByIdAndIdChat(id, idChat).map(messageMapper::toDomain);
    }

    @Override
    public List<Message> findAllByIdChatForApplicationPurposes(
            Long idChat, Instant lastFetchTime, Integer maxListSize
    ) {
        return Streams.toList(
                messageJdbcRepository.findAllByIdChatForApplicationPurposes(idChat, lastFetchTime, maxListSize),
                messageMapper::toDomain
        );
    }

    @Override
    public Message trySave(Message message) throws DatabaseConflictException {
        MessageEntity messageEntity = messageMapper.toEntity(message);

        try {
            messageEntity = messageJdbcRepository.save(messageEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseConflictException(e);
        }

        return messageMapper.toDomain(messageEntity);
    }
}
