package page.se3.datingapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import page.se3.datingapp.infrastructure.abort.reason.AccountNotFound;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.Chat;
import page.se3.datingapp.service.AccountService;
import page.se3.datingapp.service.ChatService;

import java.util.List;

@RestController
@Validated
@Tag(name = "Chats")
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {

    private final AccountService accountService;
    private final ChatService chatService;

    @GetMapping
    @Operation(summary = "Gets all owned chats")
    public List<Chat> getOwnedChatList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = accountService.getAuthKey(jwt);
        return chatService.getList(authKey);
    }
}
