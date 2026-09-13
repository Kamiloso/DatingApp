package page.se3.datingapp.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import page.se3.datingapp.infrastructure.abort.reason.UserNotFound;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.service.AuthService;
import page.se3.datingapp.service.table.ChatService;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
public class ChatController {

    private final AuthService authService;
    private final ChatService chatService;

    @GetMapping("api/user/chats")
    public List<Chat> chats(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt

    ) throws UserNotFound {
        User user = authService.login(jwt);
        return chatService.getByIdUser(user.id());
    }
}
