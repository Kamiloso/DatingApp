package page.se3.datingapp.infrastructure.controller.rest.user.account;

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
import page.se3.datingapp.domain.exception.abort.reason.account.AccountPatchDenied;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountBasePatchDto;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountDetailPatchDto;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountDto;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountInsertionFailed;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountRegisterDto;

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
            @AuthIdentity
            String email
    ) throws AccountNotFound {
        return userAccountProxy.getUserAccount(email);
    }

    @PostMapping
    @Operation(summary = "Registers a new user account")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<@NonNull UserAccountDto> registerUserAccount(
            @AuthIdentity
            String email,

            @Valid
            @NotNull
            @RequestBody
            UserAccountRegisterDto userAccountRegisterDto
    ) throws AccountInsertionFailed {
        return userAccountProxy.registerUserAccount(email, userAccountRegisterDto).get();
    }

    @PatchMapping("/detail")
    @Operation(summary = "Patches user account detailed information")
    public UserAccountDto patchUserAccountDetailInfo(
            @AuthIdentity
            AuthKey authKey,

            @Valid
            @NotNull
            @RequestBody
            UserAccountDetailPatchDto userAccountDetailPatchDto
    ) throws AccountNotFound {
        return userAccountProxy.patchUserAccountDetail(authKey, userAccountDetailPatchDto);
    }

    @PatchMapping("/base")
    @Operation(summary = "Patches user account base information (only once per account)")
    public UserAccountDto patchUserAccountBaseInfo(
            @AuthIdentity
            AuthKey authKey,

            @Valid
            @NotNull
            @RequestBody
            UserAccountBasePatchDto userAccountBasePatchDto
    ) throws AccountNotFound, AccountPatchDenied {
        return userAccountProxy.patchUserAccountBase(authKey, userAccountBasePatchDto);
    }
}
