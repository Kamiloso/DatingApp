package page.se3.datingapp.infrastructure.controller.listener.user.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.user.info.UserInfoService;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoBriefDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoKnownDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoTrustedDto;

@Service
@AutoRetry
@RequiredArgsConstructor
public class UserInfoProxy {

    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;

    public UserInfoBriefDto getUserInfoBrief(AuthKey authKey, Long idUser) throws RelationNotFound {
        return MapUtils.map(
                userInfoService.getBrief(authKey, idUser),
                userInfoMapper::toDto
        );
    }

    public UserInfoKnownDto getUserInfoKnown(AuthKey authKey, Long idUser) throws RelationNotFound {
        return MapUtils.map(
                userInfoService.getKnown(authKey, idUser),
                userInfoMapper::toDto
        );
    }

    public UserInfoTrustedDto getUserInfoTrusted(AuthKey authKey, Long idUser) throws RelationNotFound {
        return MapUtils.map(
                userInfoService.getTrusted(authKey, idUser),
                userInfoMapper::toDto
        );
    }
}
