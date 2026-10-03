package page.se3.datingapp.infrastructure.controller.rest.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatNotFound;
import page.se3.datingapp.domain.exception.abort.reason.message.MessageNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.rest.message.dto.MessageSendDto;
import page.se3.datingapp.infrastructure.controller.rest.message.dto.MessageDto;

import java.time.Instant;
import java.util.List;

@RestController
@Validated
@Tag(name = "Messages")
@RequestMapping("/api/chats/{idChat}/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageProxy messageProxy;

    @PostMapping
    @Operation(summary = "Sends a message")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<@NonNull MessageDto> sendMessage(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idChat,

            @Valid
            @NotNull
            @RequestBody
            MessageSendDto messageSendDto
    ) throws ChatNotFound, ChatAccessDenied {
        return messageProxy.sendMessage(authKey, idChat, messageSendDto).get();
    }

    @GetMapping("/{idMessage}")
    @Operation(summary = "Gets a specific message")
    public MessageDto getMessage(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idChat,

            @PathVariable
            Long idMessage
    ) throws ChatNotFound, ChatAccessDenied, MessageNotFound {
        return messageProxy.getMessage(authKey, idChat, idMessage);
    }

    @GetMapping
    @Operation(summary = "Gets a next persistent message list (empty when no more messages available)")
    public List<MessageDto> fetchMessages(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idChat,

            @RequestParam
            Instant lastFetchTime
    ) throws ChatNotFound, ChatAccessDenied {
        return messageProxy.fetchMessages(authKey, idChat, lastFetchTime);
    }
}
