package page.se3.datingapp.old.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.infrastructure.exception.reason.DataIntegrityViolation;
import page.se3.datingapp.old.model.struct.dto.table.Message;
import page.se3.datingapp.old.model.struct.entity.MessageEntity;
import page.se3.datingapp.old.model.mapper.table.MessageMapper;
import page.se3.datingapp.old.model.struct.post.MessagePost;
import page.se3.datingapp.old.onion.repository.MessageRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageAccessor {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public List<Message> getNextMessageListById(Long idChat, Instant lastFetchTime, Integer maxListSize) {
        return MapUtils.toList(
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
