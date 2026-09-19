package page.se3.datingapp.onion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.infrastructure.abort.reason.AccountNotFound;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.UserHigher;
import page.se3.datingapp.model.struct.dto.UserLower;
import page.se3.datingapp.onion.service.user.AccountService;
import page.se3.datingapp.onion.service.user.UserService;

import java.util.List;

@RestController
@Validated
@Tag(name = "Users")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final AccountService accountService;
    private final UserService userService;

    @GetMapping("/lower")
    @Operation(summary = "Gets all permitted lower user information")
    public List<UserLower> getUserLowerList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = accountService.getAuthKey(jwt);
        return userService.getLowerList(authKey);
    }

    @GetMapping("/higher")
    @Operation(summary = "Gets all permitted higher user information")
    public List<UserHigher> getUserHigherList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        AuthKey authKey = accountService.getAuthKey(jwt);
        return userService.getHigherList(authKey);
    }
}
