package page.se3.datingapp.infrastructure.rest.controller.message;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.message.MessageService;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.ChatNotFound;
import page.se3.datingapp.application.service.message.struct.MessageFetchResponse;
import page.se3.datingapp.domain.model.user.UserKey;
import page.se3.datingapp.application.service.message.struct.MessageSendRequest;
import page.se3.datingapp.domain.model.message.Message;
import page.se3.datingapp.infrastructure.rest.controller.message.dto.MessageSendRequestDto;
import page.se3.datingapp.infrastructure.rest.controller.message.dto.MessageFetchResponseDto;
import page.se3.datingapp.infrastructure.rest.controller.message.dto.MessageDto;
import page.se3.datingapp.infrastructure.rest.JwtProcessor;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MessageProxy {

    private final JwtProcessor jwtProcessor;
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    public MessageDto sendMessage(Jwt jwt, Long idChat, MessageSendRequestDto messageSendRequestDto)
            throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        UserKey userKey = jwtProcessor.getUserKey(jwt);

        MessageSendRequest messageSendRequest = messageMapper.toDomain(messageSendRequestDto);
        Message message = messageService.send(userKey, idChat, messageSendRequest);

        return messageMapper.toDto(message);
    }

    public CompletableFuture<MessageFetchResponseDto> fetchMessages(
            Jwt jwt,
            Long idChat,
            Instant lastFetchTime,
            Integer maxListSize,
            Long waitTimeout
    ) throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        UserKey userKey = jwtProcessor.getUserKey(jwt);

        CompletableFuture<MessageFetchResponse> delayedResult = messageService.fetch(
                userKey, idChat, lastFetchTime, maxListSize, waitTimeout
        );

        return delayedResult.thenApply(messageMapper::toDto);
    }
}
