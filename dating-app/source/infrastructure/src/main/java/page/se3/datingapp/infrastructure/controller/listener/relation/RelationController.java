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
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationMatchApplyPatchDto;

import java.util.List;

@RestController
@Validated
@Tag(name = "Relations")
@RequestMapping("/api/relations")
@RequiredArgsConstructor
public class RelationController {

    private final RelationProxy relationProxy;

    @GetMapping("/{idMatch}/match")
    @Operation(summary = "Gets match relation")
    public RelationMatchDto getMatchRelation(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idMatch
    ) throws RelationNotFound {
        return relationProxy.getMatchRelation(authKey, idMatch);
    }

    @GetMapping("/{idMatch}/chat")
    @Operation(summary = "Gets chat relation")
    public RelationChatDto getChatRelation(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idMatch
    ) throws RelationNotFound {
        return relationProxy.getChatRelation(authKey, idMatch);
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
        return relationProxy.getMatchRelations(authKey, withChat, blocked);
    }

    @GetMapping("/chat")
    @Operation(summary = "Gets all chat relations")
    public List<RelationChatDto> getChatRelations(
            @AuthIdentity
            AuthKey authKey,

            @RequestParam
            Boolean blocked
    ) {
        return relationProxy.getChatRelations(authKey, blocked);
    }

    @PatchMapping("/{idMatch}/apply")
    @Operation(summary = "Patches apply state of match relation")
    public RelationMatchDto patchRelationMatchApply(
            @AuthIdentity
            AuthKey authKey,

            @PathVariable
            Long idMatch,

            @RequestBody
            RelationMatchApplyPatchDto relationMatchApplyPatchDto
    ) throws RelationNotFound {
        return relationProxy.patchRelationMatchApply(authKey, idMatch, relationMatchApplyPatchDto);
    }
}
