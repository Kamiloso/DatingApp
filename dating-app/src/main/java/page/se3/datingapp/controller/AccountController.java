package page.se3.datingapp.controller;

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
import page.se3.datingapp.infrastructure.Response;
import page.se3.datingapp.infrastructure.UriPattern;
import page.se3.datingapp.infrastructure.abort.reason.AccountAlreadyExists;
import page.se3.datingapp.infrastructure.abort.reason.AccountNotFound;
import page.se3.datingapp.model.request.UserRegisterRequest;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.service.AccountService;

@RestController
@Validated
@Tag(name = "Account")
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    @Operation(summary = "Gets user account information")
    public User getUserAccount(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        return accountService.getUserAccount(jwt);
    }

    @PostMapping
    @Operation(summary = "Registers a new user account")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<@NonNull User> registerUserAccount(
            @AuthenticationPrincipal
            Jwt jwt,

            @RequestBody
            @NotNull
            @Valid
            UserRegisterRequest userRegisterRequest
    ) throws AccountAlreadyExists {
        User userAccount = accountService.registerUserAccount(jwt, userRegisterRequest);
        return Response.created(userAccount, UriPattern.current());
    }
}
