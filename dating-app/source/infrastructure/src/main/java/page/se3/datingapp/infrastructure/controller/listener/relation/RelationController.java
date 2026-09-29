package page.se3.datingapp.infrastructure.controller.listener.relation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.annotation.AuthIdentity;
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
            @AuthIdentity
            AuthKey authKey
    ) {
        return relationProxy.getMatchRelationList(authKey);
    }

    @GetMapping("/chat")
    @Operation(summary = "Gets all chat relations that are not known to be blocked")
    public List<RelationChatDto> getChatRelationList(
            @AuthIdentity
            AuthKey authKey
    ) {
        return relationProxy.getChatRelationList(authKey);
    }

    @GetMapping("/{idMatch}/apply")
    @Operation(summary = "Gets apply state of relation")
    public RelationApplyDto getRelationApply(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idMatch
    ) throws RelationNotFound {
        return relationProxy.getRelationApply(authKey, idMatch);
    }

    @PatchMapping("/{idMatch}/apply")
    @Operation(summary = "Patches apply state of relation")
    public RelationApplyDto patchRelationApply(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idMatch,

            @RequestBody
            RelationApplyPatchDto relationApplyPatchDto
    ) throws RelationNotFound {
        return relationProxy.patchRelationApply(authKey, idMatch, relationApplyPatchDto);
    }
}
