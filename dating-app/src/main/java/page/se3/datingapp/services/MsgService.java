package page.se3.datingapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.aborting.AbortResponseException;
import page.se3.datingapp.core.Config;
import page.se3.datingapp.models.mappers.ChatMapper;
import page.se3.datingapp.models.mappers.MessageMapper;
import page.se3.datingapp.models.dto.Chat;
import page.se3.datingapp.models.dto.Message;
import page.se3.datingapp.models.dto.User;
import page.se3.datingapp.models.entities.ChatEntity;
import page.se3.datingapp.models.entities.MessageEntity;
import page.se3.datingapp.models.requests.MessageSendRequest;
import page.se3.datingapp.repositories.ChatRepository;
import page.se3.datingapp.repositories.MessageRepository;
import page.se3.datingapp.aborting.AbortType;

import java.time.Instant;
import java.util.List;

public interface MsgService {
    void send(MessageSendRequest messageSendRequest, User user);
    List<Message> fetch(Long idChat, Instant lastFetchTime, User user);
}

@Service
@RequiredArgsConstructor
class MsgServiceImpl implements MsgService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final ChatMapper chatMapper;
    private final MessageMapper messageMapper;

    public void send(MessageSendRequest messageSendRequest, User user) {
        ChatEntity chatEntity = chatRepository
                .findById(messageSendRequest.idChat())
                .orElseThrow(() -> new AbortResponseException(AbortType.CHAT_NOT_FOUND));

        Chat chat = chatMapper.toDto(chatEntity);

        MessageEntity messageEntity = messageMapper.toEntity(
                messageSendRequest,
                Instant.now(),
                user,
                chat
        );

        try {
            messageRepository.save(messageEntity);
        } catch (DataIntegrityViolationException ignored) {}
    }

    public List<Message> fetch(Long idChat, Instant lastFetchTime, User user) {
        List<MessageEntity> messageEntityList = messageRepository.findByIdChatAfterSendTimeWithLimit(
                idChat,
                lastFetchTime,
                Config.LIST_RETURN_LIMIT
        );

        return messageEntityList
                .stream()
                .map(messageMapper::toDto)
                .toList();
    }
}
