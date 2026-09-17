package page.se3.datingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import page.se3.datingapp.infrastructure.abort.reason.AccountNotFound;
import page.se3.datingapp.infrastructure.abort.reason.ChatAccessDenied;
import page.se3.datingapp.infrastructure.abort.reason.ChatNotFound;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.request.MsgSendRequest;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.service.AccountService;
import page.se3.datingapp.service.msg.MsgService;

import java.time.Instant;
import java.util.List;

@RestController
@Validated
@Tag(name = "Messages")
@RequestMapping("/api/chat/{idChat}")
@RequiredArgsConstructor
public class MsgController {

    private final AccountService accountService;
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
    ) throws AccountNotFound, ChatNotFound, ChatAccessDenied, ReferencedResourceGone {
        AuthKey authKey = accountService.getAuthKey(jwt);
        return msgService.send(authKey, idChat, msgSendRequest);
    }

    @GetMapping("/msg")
    @Operation(summary = "Downloads a message list (uses long polling)")
    public DeferredResult<@NonNull List<Message>> fetchMessageList(
            @AuthenticationPrincipal
            Jwt jwt,

            @RequestHeader(value = "X-Wait-Timeout", defaultValue = "0")
            @PositiveOrZero
            @Max(10_000)
            Long waitMilliseconds,

            @PathVariable
            Long idChat,

            @RequestParam
            Instant lastFetchTime,

            @RequestParam
            @PositiveOrZero
            @Max(64)
            Integer maxListSize
    ) throws AccountNotFound, ChatNotFound, ChatAccessDenied {
        AuthKey authKey = accountService.getAuthKey(jwt);
        return msgService.fetchMessageList(authKey, waitMilliseconds, idChat, lastFetchTime, maxListSize);
    }
}
