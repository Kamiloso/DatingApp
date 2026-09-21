package page.se3.datingapp.old.onion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import page.se3.datingapp.old.infrastructure.exception.reason.AccountNotFound;
import page.se3.datingapp.old.infrastructure.exception.reason.ChatAccessDenied;
import page.se3.datingapp.old.infrastructure.exception.reason.ChatNotFound;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.request.MsgSendRequest;
import page.se3.datingapp.old.model.struct.dto.table.Message;
import page.se3.datingapp.old.onion.service.user.AuthService;
import page.se3.datingapp.old.onion.service.msg.MsgService;

import java.time.Instant;
import java.util.List;

@RestController
@Validated
@Tag(name = "Messages")
@RequestMapping("/api/chat/{idChat}")
@RequiredArgsConstructor
public class MsgController {

    private final AuthService authService;
    private final MsgService msgService;

    @PostMapping("/msg")
    @Operation(summary = "Sends a message")
    public Message sendMessage(
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idChat,

            @RequestBody
            @NotNull
            @Valid
            MsgSendRequest msgSendRequest
    ) throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        AuthKey authKey = authService.getAuthKey(jwt);
        return msgService.send(authKey, idChat, msgSendRequest);
    }

    @GetMapping("/msg")
    @Operation(summary = "Downloads a message list (uses long polling)")
    public DeferredResult<@NonNull List<Message>> fetchMessageList(
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idChat,

            @RequestParam
            Instant lastFetchTime,

            @RequestParam
            @Positive
            @Max(64)
            Integer maxListSize,

            @RequestParam
            @PositiveOrZero
            @Max(10_000)
            Long waitTimeout
    ) throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        AuthKey authKey = authService.getAuthKey(jwt);
        return msgService.fetchMessageList(authKey, idChat, lastFetchTime, maxListSize, waitTimeout);
    }
}
