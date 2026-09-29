package page.se3.datingapp.infrastructure.controller.listener.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.chat.ChatNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageSendDto;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageDto;

import java.time.Instant;
import java.util.List;

@RestController
@Validated
@Tag(name = "Messages")
@RequestMapping("/api/chat/{idChat}/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageProxy messageProxy;

    @PostMapping
    @Operation(summary = "Sends a message")
    public MessageDto sendMessage(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idChat,

            @RequestBody
            @NotNull
            @Valid
            MessageSendDto messageSendDto
    ) throws ChatNotFound, ChatAccessDenied {
        return messageProxy.sendMessage(authKey, idChat, messageSendDto);
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
