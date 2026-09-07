package page.se3.datingapp.controllers;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.services.AuthService;
import page.se3.datingapp.services.UserService;

@RestController
@Validated
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @PatchMapping("api/user/update-is-participating")
    public void updateIsParticipating(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestParam
            Boolean value
    ) {
        authService.loginAndRun(
                jwt,
                user -> userService.updateIsParticipating(user, value)
        );
    }
}
