package page.se3.datingapp.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.infrastructure.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.struct.entity.MessageEntity;
import page.se3.datingapp.model.mapper.MessageMapper;
import page.se3.datingapp.model.struct.post.MessagePost;
import page.se3.datingapp.onion.repository.MessageRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageAccessor {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public List<Message> getNextMessageListById(Long idChat, Instant lastFetchTime, Integer maxListSize) {
        return StreamMap.toList(
                messageRepository.findByIdChatAfterSendTimeWithLimit(idChat, lastFetchTime, maxListSize),
                messageMapper::toDto
        );
    }

    public Message tryPost(MessagePost messagePost) throws DataIntegrityViolation {
        MessageEntity messageEntity = messageMapper.toEntity(messagePost);

        try {
            messageEntity = messageRepository.save(messageEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return messageMapper.toDto(messageEntity);
    }
}
