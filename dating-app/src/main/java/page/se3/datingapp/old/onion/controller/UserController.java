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
import page.se3.datingapp.old.model.struct.dto.UserKnown;
import page.se3.datingapp.old.model.struct.dto.UserBrief;
import page.se3.datingapp.old.model.struct.dto.UserTrusted;
import page.se3.datingapp.old.onion.service.user.AuthService;
import page.se3.datingapp.old.onion.service.user.UserService;

import java.util.List;

@RestController
@Validated
@Tag(name = "Users")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @GetMapping("/brief")
    @Operation(summary = "Gets all brief user information that is permitted by match")
    public List<UserBrief> getUsersBrief(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = authService.getAuthKey(jwt);
        return userService.getBriefList(authKey);
    }

    @GetMapping("/known")
    @Operation(summary = "Gets all known user information that is permitted by chat")
    public List<UserKnown> getUsersKnown(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = authService.getAuthKey(jwt);
        return userService.getKnownList(authKey);
    }

    @GetMapping("/trusted")
    @Operation(summary = "Gets all trusted user information that is permitted by upgraded chat")
    public List<UserTrusted> getUsersTrusted(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = authService.getAuthKey(jwt);
        return userService.getTrustedList(authKey);
    }
}
