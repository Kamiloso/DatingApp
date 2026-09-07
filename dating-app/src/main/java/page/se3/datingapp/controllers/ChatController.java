package page.se3.datingapp.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import page.se3.datingapp.services.AuthService;
import page.se3.datingapp.services.ChatService;

@RestController
@Validated
@RequiredArgsConstructor
public class ChatController {

    private final AuthService authService;
    private final ChatService chatService;

    @GetMapping("api/user/chats")
    public void chats(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestParam
            Boolean value
    ) {
        authService.loginAndReturn(
                jwt,
                chatService::getByUser
        );
    }
}
