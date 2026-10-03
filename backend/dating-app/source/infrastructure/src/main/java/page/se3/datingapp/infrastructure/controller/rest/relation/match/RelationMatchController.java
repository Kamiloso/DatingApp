package page.se3.datingapp.infrastructure.controller.rest.relation.match;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.annotation.AuthIdentity;
import page.se3.datingapp.infrastructure.controller.rest.relation.match.dto.RelationMatchDto;
import page.se3.datingapp.infrastructure.controller.rest.relation.match.dto.RelationMatchApplyPatchDto;

import java.util.List;

@RestController
@Validated
@Tag(name = "Matches")
@RequestMapping("/api/relations")
@RequiredArgsConstructor
public class RelationMatchController {

    private final RelationMatchProxy relationMatchProxy;

    @GetMapping("/{idMatch}/match")
    @Operation(summary = "Gets match relation")
    public RelationMatchDto getMatchRelation(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idMatch
    ) throws RelationNotFound {
        return relationMatchProxy.getMatchRelation(authKey, idMatch);
    }

    @GetMapping("/match")
    @Operation(summary = "Gets all match relations")
    public List<RelationMatchDto> getMatchRelations(
            @AuthIdentity
            AuthKey authKey,

            @RequestParam
            Integer withChat,

            @RequestParam
            Boolean blocked
    ) {
        return relationMatchProxy.getMatchRelations(authKey, withChat, blocked);
    }

    @PatchMapping("/{idMatch}/apply")
    @Operation(summary = "Patches apply state of match relation")
    public RelationMatchDto patchRelationMatchApply(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idMatch,

            @Valid
            @NotNull
            @RequestBody
            RelationMatchApplyPatchDto relationMatchApplyPatchDto
    ) throws RelationNotFound {
        return relationMatchProxy.patchRelationMatchApply(authKey, idMatch, relationMatchApplyPatchDto);
    }
}
