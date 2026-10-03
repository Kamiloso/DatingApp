package page.se3.datingapp.infrastructure.controller.rest.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.message.MessageService;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatNotFound;
import page.se3.datingapp.domain.exception.abort.reason.message.MessageNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.domain.model.message.MessageSend;
import page.se3.datingapp.infrastructure.controller.rest.message.dto.MessageSendDto;
import page.se3.datingapp.infrastructure.controller.rest.message.dto.MessageDto;
import page.se3.datingapp.infrastructure.controller.service.ResponseFactory;
import page.se3.datingapp.infrastructure.controller.service.helper.Response;

import java.time.Instant;
import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class MessageProxy {

    private final MessageService messageService;
    private final MessageMapper messageMapper;
    private final ResponseFactory responseFactory;

    public Response<MessageDto> sendMessage(AuthKey authKey, Long idChat, MessageSendDto messageSendDto)
            throws ChatNotFound, ChatAccessDenied {
        MessageSend messageSend = messageMapper.toDomain(messageSendDto);

        MessageDto messageDto = Streams.map(
                messageService.send(authKey, idChat, messageSend),
                messageMapper::toDto
        );

        return responseFactory.created(messageDto, messageDto.id());
    }

    public MessageDto getMessage(AuthKey authKey, Long idChat, Long idMessage)
            throws ChatNotFound, ChatAccessDenied, MessageNotFound {
        return Streams.map(
                messageService.get(authKey, idChat, idMessage),
                messageMapper::toDto
        );
    }

    public List<MessageDto> fetchMessages(AuthKey authKey, Long idChat, Instant lastFetchTime)
            throws ChatNotFound, ChatAccessDenied {
        return Streams.toList(
                messageService.fetch(authKey, idChat, lastFetchTime),
                messageMapper::toDto
        );
    }
}
