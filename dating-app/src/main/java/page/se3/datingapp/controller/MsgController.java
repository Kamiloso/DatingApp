package page.se3.datingapp.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.infrastructure.abort.reason.ChatAccessDenied;
import page.se3.datingapp.infrastructure.abort.reason.ChatNotFound;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.infrastructure.abort.reason.UserNotFound;
import page.se3.datingapp.model.struct.dto.Message;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.request.MsgSendRequest;
import page.se3.datingapp.service.AuthService;
import page.se3.datingapp.service.MsgService;

import java.time.Instant;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class MsgController {

    private final AuthService authService;
    private final MsgService msgService;

    @PostMapping("api/msg/send")
    public Message send(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestBody
            @Valid
            MsgSendRequest msgSendRequest

    ) throws UserNotFound, ChatNotFound, ChatAccessDenied, ReferencedResourceGone {
        User user = authService.login(jwt);
        return msgService.send(msgSendRequest, user.id());
    }

    @GetMapping("api/msg/fetch")
    public List<Message> fetch(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestParam
            Long idChat,

            @RequestParam
            Instant lastFetchTime

    ) throws UserNotFound, ChatNotFound, ChatAccessDenied {
        User user = authService.login(jwt);
        return msgService.fetch(idChat, lastFetchTime, user.id());
    }
}
