package page.se3.datingapp.infrastructure.controller.listener.relation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.RelationNotFound;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationChatDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationMatchDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationApplyDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationApplyPatchDto;

import java.util.List;

@RestController
@Validated
@Tag(name = "Relations")
@RequestMapping("/api/relations")
@RequiredArgsConstructor
public class RelationController {

    private final RelationProxy relationProxy;

    @GetMapping("/match")
    @Operation(summary = "Gets all match relations that are not known to be blocked")
    public List<RelationMatchDto> getMatchRelationList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        return relationProxy.getMatchRelationList(jwt);
    }

    @GetMapping("/chat")
    @Operation(summary = "Gets all chat relations that are not known to be blocked")
    public List<RelationChatDto> getChatRelationList(
            @AuthenticationPrincipal
            Jwt jwt
    ) throws AccountNotFound {
        return relationProxy.getChatRelationList(jwt);
    }

    @GetMapping("/{idMatch}/apply")
    @Operation(summary = "Gets apply state of relation")
    public RelationApplyDto getRelationApply(
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idMatch
    ) throws AccountNotFound, RelationNotFound {
        return relationProxy.getRelationApply(jwt, idMatch);
    }

    @PatchMapping("/{idMatch}/apply")
    @Operation(summary = "Patches apply state of relation")
    public RelationApplyDto patchRelationApply(
            @AuthenticationPrincipal
            Jwt jwt,

            @PathVariable
            Long idMatch,

            @RequestBody
            RelationApplyPatchDto relationApplyPatchDto
    ) throws AccountNotFound, RelationNotFound {
        return relationProxy.patchRelationApply(jwt, idMatch, relationApplyPatchDto);
    }
}
