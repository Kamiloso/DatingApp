package page.se3.datingapp.infrastructure.controller.listener.message;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.message.MessageService;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.ChatNotFound;
import page.se3.datingapp.application.service.message.struct.MessagesFetched;
import page.se3.datingapp.domain.model.user.UserKey;
import page.se3.datingapp.application.service.message.struct.MessageSend;
import page.se3.datingapp.domain.model.message.Message;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageSendDto;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessagesFetchedDto;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageDto;
import page.se3.datingapp.infrastructure.controller.service.JwtProcessor;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MessageProxy {

    private final JwtProcessor jwtProcessor;
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @AutoRetry
    public MessageDto sendMessage(Jwt jwt, Long idChat, MessageSendDto messageSendDto)
            throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        UserKey userKey = jwtProcessor.getUserKey(jwt);

        MessageSend messageSend = messageMapper.toDomain(messageSendDto);
        Message message = messageService.send(userKey, idChat, messageSend);

        return messageMapper.toDto(message);
    }

    // TODO: replace with some serious notifications
    public CompletableFuture<MessagesFetchedDto> fetchMessages(
            Jwt jwt,
            Long idChat,
            Instant lastFetchTime,
            Integer maxListSize,
            Long waitTimeout
    ) throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        UserKey userKey = jwtProcessor.getUserKey(jwt);

        CompletableFuture<MessagesFetched> delayedResult = messageService.fetch(
                userKey, idChat, lastFetchTime, maxListSize, waitTimeout
        );

        return delayedResult.thenApply(messageMapper::toDto);
    }
}
