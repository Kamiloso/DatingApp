package page.se3.datingapp.infrastructure.jdbc.database.message;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.repository.MessageRepository;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.domain.model.message.Message;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageJdbcAdapter implements MessageRepository {

    private final MessageJdbcRepository messageJdbcRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<Message> findAllByIdChatForApplicationPurposes(
            Long idChat,
            Instant lastFetchTime,
            Integer maxListSize
    ) {
        return MapUtils.toList(
                messageJdbcRepository.findAllByIdChatForApplicationPurposes(idChat, lastFetchTime, maxListSize),
                messageMapper::toDomain
        );
    }

    @Override
    public Message trySave(Message message) throws DataIntegrityViolation {
        MessageEntity messageEntity = messageMapper.toEntity(message);

        try {
            messageEntity = messageJdbcRepository.save(messageEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return messageMapper.toDomain(messageEntity);
    }
}
