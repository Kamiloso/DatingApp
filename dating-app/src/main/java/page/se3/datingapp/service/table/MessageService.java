package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.struct.entity.MessageEntity;
import page.se3.datingapp.model.mapper.MessageMapper;
import page.se3.datingapp.model.struct.post.MessagePost;
import page.se3.datingapp.repository.MessageRepository;

import java.time.Instant;
import java.util.List;

public interface MessageService {
    List<Message> getNextMessageListByIdChat(Long idChat, Instant lastFetchTime);
    Message tryPost(MessagePost messagePost) throws ReferencedResourceGone;
}

@Service
@RequiredArgsConstructor
class MessageServiceImpl implements MessageService {

    private final AppConfig appConfig;
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<Message> getNextMessageListByIdChat(Long idChat, Instant lastFetchTime) {
        AppConfig.Limits limitsConfig = appConfig.limits();
        Integer maxListReturnSize = limitsConfig.maxListReturnSize();

        return messageRepository
                .findByIdChatAfterSendTimeWithLimit(idChat, lastFetchTime, maxListReturnSize)
                .stream()
                .map(messageMapper::toDto)
                .toList();
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
