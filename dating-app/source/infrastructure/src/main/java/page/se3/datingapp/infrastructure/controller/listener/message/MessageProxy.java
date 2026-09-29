package page.se3.datingapp.infrastructure.controller.listener.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.message.MessageService;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.application.service.message.struct.MessageSend;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageSendDto;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageDto;

import java.time.Instant;
import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class MessageProxy {

    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageDto sendMessage(AuthKey authKey, Long idChat, MessageSendDto messageSendDto)
            throws ChatNotFound, ChatAccessDenied {
        MessageSend messageSend = messageMapper.toDomain(messageSendDto);

        return MapUtils.map(
                messageService.send(authKey, idChat, messageSend),
                messageMapper::toDto
        );
    }

    public List<MessageDto> fetchMessages(AuthKey authKey, Long idChat, Instant lastFetchTime)
            throws ChatNotFound, ChatAccessDenied {
        return MapUtils.toList(
                messageService.fetch(authKey, idChat, lastFetchTime),
                messageMapper::toDto
        );
    }
}
