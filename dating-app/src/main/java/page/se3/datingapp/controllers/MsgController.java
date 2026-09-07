package page.se3.datingapp.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.models.dto.Message;
import page.se3.datingapp.models.requests.MessageSendRequest;
import page.se3.datingapp.services.AuthService;
import page.se3.datingapp.services.MsgService;

import java.time.Instant;
import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class MsgController {

    private final AuthService authService;
    private final MsgService msgService;

    @PostMapping("api/msg/send")
    public void send(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestBody
            @Valid
            MessageSendRequest messageSendRequest
    ) {
        authService.loginAndRun(
                jwt,
                user -> msgService.send(messageSendRequest, user)
        );
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
    ) {
        return authService.loginAndReturn(
                jwt,
                user -> msgService.fetch(idChat, lastFetchTime, user)
        );
    }
}
