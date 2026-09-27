package page.se3.datingapp.infrastructure.controller.listener.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.relation.RelationService;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.RelationNotFound;
import page.se3.datingapp.domain.model.relation.apply.RelationApply;
import page.se3.datingapp.application.service.relation.struct.RelationApplyPatch;
import page.se3.datingapp.domain.model.relation.chat.RelationChat;
import page.se3.datingapp.domain.model.relation.match.RelationMatch;
import page.se3.datingapp.domain.model.user.UserKey;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationChatDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationApplyPatchDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationMatchDto;
import page.se3.datingapp.infrastructure.controller.listener.relation.dto.RelationApplyDto;
import page.se3.datingapp.infrastructure.controller.service.JwtProcessor;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class RelationProxy {

    private final JwtProcessor jwtProcessor;
    private final RelationService relationService;
    private final RelationMapper relationMapper;

    public List<RelationMatchDto> getMatchRelationList(Jwt jwt) throws AccountNotFound {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        List<RelationMatch> relationMatchList = relationService.getMatchList(userKey);

        return MapUtils.toList(relationMatchList, relationMapper::toDto);
    }

    public List<RelationChatDto> getChatRelationList(Jwt jwt) throws AccountNotFound {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        List<RelationChat> relationChatList = relationService.getChatList(userKey);

        return MapUtils.toList(relationChatList, relationMapper::toDto);
    }

    public RelationApplyDto getRelationApply(Jwt jwt, Long idMatch) throws AccountNotFound, RelationNotFound {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        RelationApply relationApply = relationService.getApply(userKey, idMatch);

        return relationMapper.toDto(relationApply);
    }

    public RelationApplyDto patchRelationApply(
            Jwt jwt,
            Long idMatch,
            RelationApplyPatchDto relationApplyPatchDto
    ) throws AccountNotFound, RelationNotFound {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        RelationApplyPatch relationApplyPatch = relationMapper.toDomain(relationApplyPatchDto);
        RelationApply relationApply = relationService.setApply(userKey, idMatch, relationApplyPatch);

        return relationMapper.toDto(relationApply);
    }
}
