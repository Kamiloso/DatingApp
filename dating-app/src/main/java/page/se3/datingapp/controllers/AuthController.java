package page.se3.datingapp.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import page.se3.datingapp.models.dto.User;
import page.se3.datingapp.models.requests.UserRegisterRequest;
import page.se3.datingapp.services.AuthService;

@RestController
@Validated
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("api/auth/login")
    public User login(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt
    ) {
        return authService.login(jwt);
    }

    @PostMapping("api/auth/register")
    public User register(
            @AuthenticationPrincipal
            @NotNull
            Jwt jwt,

            @RequestBody
            @Valid
            UserRegisterRequest userRegisterRequest
    ) {
        return authService.register(jwt, userRegisterRequest);
    }
}
