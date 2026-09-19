package page.se3.datingapp.onion.controller;

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
import page.se3.datingapp.model.struct.dto.Match;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.onion.service.user.AccountService;
import page.se3.datingapp.onion.service.chat.ChatService;

import java.util.List;

@RestController
@Validated
@Tag(name = "Relations")
@RequestMapping("/api/relations")
@RequiredArgsConstructor
public class RelationController {

    private final AccountService accountService;
    private final ChatService chatService;

    @GetMapping("/chats")
    @Operation(summary = "Gets all owned chats")
    public List<Chat> getOwnedChatList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = accountService.getAuthKey(jwt);
        return chatService.getList(authKey);
    }

    @GetMapping("/matches")
    @Operation(summary = "Gets all active matches")
    public List<Match> getActiveMatchList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = accountService.getAuthKey(jwt);

        throw new RuntimeException("TODO"); // TODO: finish
    }
}
