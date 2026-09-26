package page.se3.datingapp.infrastructure.controller.listener.message;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.ChatAccessDenied;
import page.se3.datingapp.domain.exception.abort.reason.ChatNotFound;
import page.se3.datingapp.infrastructure.controller.Template;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageSendDto;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessagesFetchedDto;
import page.se3.datingapp.infrastructure.controller.listener.message.dto.MessageDto;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

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
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idChat,

            @RequestBody
            @NotNull
            @Valid
            MessageSendDto messageSendDto
    ) throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        return messageProxy.sendMessage(jwt, idChat, messageSendDto);
    }

    @GetMapping
    @Operation(summary = "Downloads a message list (uses long polling)")
    public CompletableFuture<MessagesFetchedDto> fetchMessages(
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idChat,

            @RequestParam
            Instant lastFetchTime,

            @RequestParam
            @Positive
            @Max(Template.LIST_SIZE_64)
            Integer maxListSize,

            @RequestParam
            @PositiveOrZero
            @Max(10_000)
            Long waitTimeout
    ) throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        return messageProxy.fetchMessages(jwt, idChat, lastFetchTime, maxListSize, waitTimeout);
    }
}
