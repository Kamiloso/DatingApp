package page.se3.datingapp.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.infrastructure.abort.reason.UserNotFound;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.service.AuthService;
import page.se3.datingapp.service.table.UserService;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @PatchMapping("api/user/is-participating")
    public void isParticipating(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestParam
            Boolean value

    ) throws UserNotFound {
        User user = authService.login(jwt);
        userService.updateIsParticipatingById(user.id(), value);
    }
}
