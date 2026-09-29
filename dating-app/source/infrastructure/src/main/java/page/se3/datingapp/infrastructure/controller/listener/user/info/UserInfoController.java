package page.se3.datingapp.infrastructure.controller.listener.user.info;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoBriefDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoKnownDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoTrustedDto;

import java.util.List;

@RestController
@Validated
@Tag(name = "Users")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoProxy userInfoProxy;

    @GetMapping("/brief")
    @Operation(summary = "Gets all brief user information (permitted by match)")
    public List<UserInfoBriefDto> getUserInfoBriefList(
            @AuthIdentity
            AuthKey authKey
    ) {
        return userInfoProxy.getUserInfoBriefList(authKey);
    }

    @GetMapping("/known")
    @Operation(summary = "Gets all known user information (permitted by chat)")
    public List<UserInfoKnownDto> getUserInfoKnownList(
            @AuthIdentity
            AuthKey authKey
    ) {
        return userInfoProxy.getUserInfoKnownList(authKey);
    }

    @GetMapping("/trusted")
    @Operation(summary = "Gets all trusted user information (permitted by upgraded chat)")
    public List<UserInfoTrustedDto> getUserInfoTrustedList(
            @AuthIdentity
            AuthKey authKey
    ) {
        return userInfoProxy.getUserInfoTrustedList(authKey);
    }
}
