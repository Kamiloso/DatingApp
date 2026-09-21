package page.se3.datingapp.old.onion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.old.infrastructure.exception.reason.*;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.struct.dto.MatchReport;
import page.se3.datingapp.old.model.struct.dto.table.Match;
import page.se3.datingapp.old.onion.service.user.AuthService;
import page.se3.datingapp.old.onion.service.relation.MatchService;

import java.util.List;

@RestController
@Validated
@Tag(name = "Matches")
@RequestMapping("/api/relations/matches")
@RequiredArgsConstructor
public class MatchController {

    private final AuthService authService;
    private final MatchService matchService;

    @GetMapping
    @Operation(summary = "Gets all owned matches")
    public List<Match> getOwnedMatches(
            @AuthenticationPrincipal
            Jwt jwt,

            @RequestParam
            Boolean accepted,

            @RequestParam
            Boolean blocked
    ) throws AccountNotFound {
        AuthKey authKey = authService.getAuthKey(jwt);
        return matchService.getList(authKey, blocked, accepted);
    }

    @PatchMapping("/{idMatch}")
    @Operation(summary = "Sets whether you want to create a chat from a match")
    public MatchReport setMatchIsApplied(
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idMatch,

            @RequestParam
            Boolean isApplied
    ) throws AccountNotFound, MatchNotFound, MatchAccessDenied, MatchAlreadyAccepted, ChatCreationFailed {
        AuthKey authKey = authService.getAuthKey(jwt);
        return matchService.setIsApplied(authKey, idMatch, isApplied);
    }
}
