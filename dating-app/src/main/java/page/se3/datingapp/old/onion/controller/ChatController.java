package page.se3.datingapp.old.onion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.old.infrastructure.exception.reason.AccountNotFound;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.struct.dto.table.Chat;
import page.se3.datingapp.old.onion.service.user.AuthService;
import page.se3.datingapp.old.onion.service.relation.ChatService;

import java.util.List;

@RestController
@Validated
@Tag(name = "Chats")
@RequestMapping("/api/relations/chats")
@RequiredArgsConstructor
public class ChatController {

    private final AuthService authService;
    private final ChatService chatService;

    @GetMapping
    @Operation(summary = "Gets all owned chats")
    public List<Chat> getOwnedChats(
            @AuthenticationPrincipal
            Jwt jwt,

            @RequestParam
            Boolean blocked
    ) throws AccountNotFound {
        AuthKey authKey = authService.getAuthKey(jwt);
        return chatService.getList(authKey, blocked);
    }
}
