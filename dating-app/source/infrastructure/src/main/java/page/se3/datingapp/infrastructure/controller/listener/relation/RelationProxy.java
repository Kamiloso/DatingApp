package page.se3.datingapp.infrastructure.controller.listener.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.relation.RelationService;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.application.service.relation.struct.RelationApplyPatch;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationChatDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationApplyPatchDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationMatchDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationApplyDto;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class RelationProxy {

    private final RelationService relationService;
    private final RelationMapper relationMapper;

    public List<RelationMatchDto> getMatchRelationList(AuthKey authKey) {
        return MapUtils.toList(
                relationService.getMatchList(authKey),
                relationMapper::toDto
        );
    }

    public List<RelationChatDto> getChatRelationList(AuthKey authKey) {
        return MapUtils.toList(
                relationService.getChatList(authKey),
                relationMapper::toDto
        );
    }

    public RelationApplyDto getRelationApply(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return MapUtils.map(
                relationService.getApply(authKey, idMatch),
                relationMapper::toDto
        );
    }

    public RelationApplyDto patchRelationApply(
            AuthKey authKey,
            Long idMatch,
            RelationApplyPatchDto relationApplyPatchDto
    ) throws RelationNotFound {
        RelationApplyPatch relationApplyPatch = relationMapper.toDomain(relationApplyPatchDto);

        return MapUtils.map(
                relationService.setApply(authKey, idMatch, relationApplyPatch),
                relationMapper::toDto
        );
    }
}
