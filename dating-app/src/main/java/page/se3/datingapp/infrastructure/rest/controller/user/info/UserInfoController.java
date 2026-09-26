package page.se3.datingapp.infrastructure.rest.controller.user.info;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.infrastructure.rest.controller.user.info.dto.UserInfoBriefDto;
import page.se3.datingapp.infrastructure.rest.controller.user.info.dto.UserInfoKnownDto;
import page.se3.datingapp.infrastructure.rest.controller.user.info.dto.UserInfoTrustedDto;

import java.util.List;

@RestController
@Validated
@Tag(name = "Users")
@RequestMapping("/api/readyUsers")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoProxy userInfoProxy;

    @GetMapping("/brief")
    @Operation(summary = "Gets all brief user information (permitted by match)")
    public List<UserInfoBriefDto> getUserInfoBriefList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        return userInfoProxy.getUserInfoBriefList(jwt);
    }

    @GetMapping("/known")
    @Operation(summary = "Gets all known user information (permitted by chat)")
    public List<UserInfoKnownDto> getUserInfoKnownList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        return userInfoProxy.getUserInfoKnownList(jwt);
    }

    @GetMapping("/trusted")
    @Operation(summary = "Gets all trusted user information (permitted by upgraded chat)")
    public List<UserInfoTrustedDto> getUserInfoTrustedList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        return userInfoProxy.getUserInfoTrustedList(jwt);
    }
}
