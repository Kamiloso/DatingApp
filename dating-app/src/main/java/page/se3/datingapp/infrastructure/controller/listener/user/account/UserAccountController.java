package page.se3.datingapp.infrastructure.controller.listener.user.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountDto;
import page.se3.datingapp.domain.exception.abort.reason.AccountAlreadyExists;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountRegisterDto;

@RestController
@Validated
@Tag(name = "Account")
@RequestMapping("/api/users/account")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountProxy userAccountProxy;

    @GetMapping
    @Operation(summary = "Gets user account information")
    public UserAccountDto getUserAccount(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        return userAccountProxy.getUserAccount(jwt);
    }

    @PostMapping
    @Operation(summary = "Registers a new user account")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<@NonNull UserAccountDto> registerUserAccount(
            @AuthenticationPrincipal
            Jwt jwt,

            @RequestBody
            @NotNull
            @Valid
            UserAccountRegisterDto userAccountRegisterDto
    ) throws AccountAlreadyExists {
        return userAccountProxy.registerUserAccount(jwt, userAccountRegisterDto);
    }

    // TODO: add patch 1 (minor, not restricted)

    // TODO: add patch 2 (major, restricted)
}
