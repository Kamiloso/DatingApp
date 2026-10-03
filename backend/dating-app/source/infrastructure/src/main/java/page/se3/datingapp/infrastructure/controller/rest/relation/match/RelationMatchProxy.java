package page.se3.datingapp.infrastructure.controller.rest.relation.match;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.relation.RelationMatchService;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.relation.match.RelationMatchApplyPatch;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.rest.relation.match.dto.RelationMatchApplyPatchDto;
import page.se3.datingapp.infrastructure.controller.rest.relation.match.dto.RelationMatchDto;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class RelationMatchProxy {

    private final RelationMatchService relationMatchService;
    private final RelationMatchMapper relationMatchMapper;

    public RelationMatchDto getMatchRelation(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return Streams.map(
                relationMatchService.getMatch(authKey, idMatch),
                relationMatchMapper::toDto
        );
    }

    public List<RelationMatchDto> getMatchRelations(AuthKey authKey, int withChat, boolean blocked) {
        return Streams.toList(
                relationMatchService.getMatchList(authKey, withChat, blocked),
                relationMatchMapper::toDto
        );
    }

    public RelationMatchDto patchRelationMatchApply(
            AuthKey authKey,
            Long idMatch,
            RelationMatchApplyPatchDto relationMatchApplyPatchDto
    ) throws RelationNotFound {
        RelationMatchApplyPatch relationMatchApplyPatch = relationMatchMapper.toDomain(relationMatchApplyPatchDto);

        return Streams.map(
                relationMatchService.setMatchApply(authKey, idMatch, relationMatchApplyPatch),
                relationMatchMapper::toDto
        );
    }
}
