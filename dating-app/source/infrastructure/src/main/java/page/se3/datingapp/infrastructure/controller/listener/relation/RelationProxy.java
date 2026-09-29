package page.se3.datingapp.infrastructure.controller.listener.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.relation.RelationService;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.relation.RelationMatchApplyPatch;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationChatDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationMatchApplyPatchDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationMatchDto;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class RelationProxy {

    private final RelationService relationService;
    private final RelationMapper relationMapper;

    public RelationMatchDto getMatchRelation(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return MapUtils.map(
                relationService.getMatch(authKey, idMatch),
                relationMapper::toDto
        );
    }

    public RelationChatDto getChatRelation(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return MapUtils.map(
                relationService.getChat(authKey, idMatch),
                relationMapper::toDto
        );
    }

    public List<RelationMatchDto> getMatchRelations(AuthKey authKey, int withChat, boolean blocked) {
        return MapUtils.toList(
                relationService.getMatchList(authKey, withChat, blocked),
                relationMapper::toDto
        );
    }

    public List<RelationChatDto> getChatRelations(AuthKey authKey, boolean blocked) {
        return MapUtils.toList(
                relationService.getChatList(authKey, blocked),
                relationMapper::toDto
        );
    }

    public RelationMatchDto patchRelationMatchApply(
            AuthKey authKey,
            Long idMatch,
            RelationMatchApplyPatchDto relationMatchApplyPatchDto
    ) throws RelationNotFound {
        RelationMatchApplyPatch relationMatchApplyPatch = relationMapper.toDomain(relationMatchApplyPatchDto);

        return MapUtils.map(
                relationService.setMatchApply(authKey, idMatch, relationMatchApplyPatch),
                relationMapper::toDto
        );
    }
}
