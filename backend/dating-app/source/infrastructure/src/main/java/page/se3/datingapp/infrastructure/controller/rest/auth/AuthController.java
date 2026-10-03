package page.se3.datingapp.infrastructure.controller.rest.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.auth.EmailValidationFailed;
import page.se3.datingapp.domain.exception.abort.reason.auth.InvalidCredentials;
import page.se3.datingapp.domain.exception.abort.reason.auth.InvalidVerificationCode;
import page.se3.datingapp.domain.exception.abort.reason.endpoint.DevelopmentOnlyEndpoint;
import page.se3.datingapp.infrastructure.controller.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.rest.auth.dto.*;

@RestController
@Validated
@Tag(name = "Authorization")
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthProxy authProxy;

    @PostMapping("/api/auth/actions/send-verification-code")
    @Operation(description = "Sends verification code to a specific email")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<@NonNull Void> sendVerificationCode(
            @Valid
            @NotNull
            @RequestBody
            SendVerificationCodeDto sendVerificationCodeDto
    ) throws EmailValidationFailed {
        return authProxy.sendVerificationCode(sendVerificationCodeDto).get();
    }

    @PostMapping("/api/auth/actions/set-password-by-verification-code")
    @Operation(description = "Sets password by verification code")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<@NonNull Void> setPasswordByVerificationCode(
            @Valid
            @NotNull
            @RequestBody
            SetPasswordByVerificationCodeDto setPasswordByVerificationCodeDto
    ) throws InvalidVerificationCode {
        return authProxy.setPasswordByVerificationCode(setPasswordByVerificationCodeDto).get();
    }

    @PostMapping("/api/auth/actions/set-password")
    @Operation(description = "Sets password while in session")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<@NonNull Void> setPasswordBySession(
            @AuthIdentity
            String email,

            @Valid
            @NotNull
            @RequestBody
            SetPasswordDto setPasswordDto
    ) {
        return authProxy.setPasswordBySession(email, setPasswordDto).get();
    }

    @PostMapping("/api/auth/jwt/requests/login")
    @Operation(description = "Generates JWT token by login")
    public JwtTokenDto computeJwtByLogin(
            @Valid
            @NotNull
            @RequestBody
            LoginDto loginDto
    ) throws EmailValidationFailed, InvalidCredentials {
        return authProxy.computeJwtByLogin(loginDto);
    }

    @PostMapping("/api/auth/jwt/requests/debug-login")
    @Operation(description = "Generates JWT token for debug purposes")
    public JwtTokenDto getJwtByDebugLogin(
            @Valid
            @NotNull
            @RequestBody
            DebugLoginDto debugLoginDto
    ) throws DevelopmentOnlyEndpoint, EmailValidationFailed {
        return authProxy.getJwtByDebugLogin(debugLoginDto);
    }

    @GetMapping("/api/auth/jwt/regenerated")
    @Operation(description = "Refreshes JWT token while in session")
    public JwtTokenDto regenerateJwtBySession(
            @AuthIdentity
            String email
    ) {
        return authProxy.regenerateJwtBySession(email);
    }
}
