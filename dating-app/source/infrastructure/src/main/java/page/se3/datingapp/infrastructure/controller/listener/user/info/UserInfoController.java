package page.se3.datingapp.infrastructure.controller.listener.user.info;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoBriefDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoKnownDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoTrustedDto;

@RestController
@Validated
@Tag(name = "Users")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoProxy userInfoProxy;

    @GetMapping("/brief/idUser")
    @Operation(summary = "Gets user brief information (permitted by match)")
    public UserInfoBriefDto getUserInfoBrief(
            @AuthIdentity
            AuthKey authKey,

            @RequestParam
            Long idUser
    ) throws RelationNotFound {
        return userInfoProxy.getUserInfoBrief(authKey, idUser);
    }

    @GetMapping("/known/idUser")
    @Operation(summary = "Gets user known information (permitted by chat)")
    public UserInfoKnownDto getUserInfoKnown(
            @AuthIdentity
            AuthKey authKey,

            @RequestParam
            Long idUser
    ) throws RelationNotFound {
        return userInfoProxy.getUserInfoKnown(authKey, idUser);
    }

    @GetMapping("/trusted/idUser")
    @Operation(summary = "Gets user trusted information (permitted by upgraded chat)")
    public UserInfoTrustedDto getUserInfoTrusted(
            @AuthIdentity
            AuthKey authKey,

            @RequestParam
            Long idUser
    ) throws RelationNotFound {
        return userInfoProxy.getUserInfoTrusted(authKey, idUser);
    }
}
