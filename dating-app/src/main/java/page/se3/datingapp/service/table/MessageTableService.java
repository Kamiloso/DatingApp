package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.struct.entity.MessageEntity;
import page.se3.datingapp.model.mapper.MessageMapper;
import page.se3.datingapp.model.struct.post.MessagePost;
import page.se3.datingapp.repository.MessageRepository;

import java.time.Instant;
import java.util.List;

public interface MessageTableService {
    List<Message> getNextMessageListById(Long idChat, Instant lastFetchTime, Integer maxListSize);
    Message tryPost(MessagePost messagePost) throws ReferencedResourceGone;
}

@Service
@RequiredArgsConstructor
class MessageTableServiceImpl implements MessageTableService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<Message> getNextMessageListById(Long idChat, Instant lastFetchTime, Integer maxListSize) {
        return StreamMap.fromList(
                messageRepository.findByIdChatAfterSendTimeWithLimit(idChat, lastFetchTime, maxListSize),
                messageMapper::toDto
        );
    }

    @Override
    public Message tryPost(MessagePost messagePost) throws ReferencedResourceGone {
        MessageEntity messageEntity = messageMapper.toEntity(messagePost);

        try {
            messageEntity = messageRepository.save(messageEntity);
        } catch (DataIntegrityViolationException e) {
            throw new ReferencedResourceGone();
        }

        return messageMapper.toDto(messageEntity);
    }
}
