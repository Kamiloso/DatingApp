package page.se3.datingapp.infrastructure.rest.controller.user.info;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.user.info.UserInfoService;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.model.user.info.UserBriefInfo;
import page.se3.datingapp.domain.model.user.UserKey;
import page.se3.datingapp.domain.model.user.info.UserKnownInfo;
import page.se3.datingapp.domain.model.user.info.UserTrustedInfo;
import page.se3.datingapp.infrastructure.rest.controller.user.info.dto.UserInfoBriefDto;
import page.se3.datingapp.infrastructure.rest.controller.user.info.dto.UserInfoKnownDto;
import page.se3.datingapp.infrastructure.rest.controller.user.info.dto.UserInfoTrustedDto;
import page.se3.datingapp.infrastructure.rest.JwtProcessor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserInfoProxy {

    private final JwtProcessor jwtProcessor;
    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;

    public List<UserInfoBriefDto> getUserInfoBriefList(Jwt jwt) throws AccountNotFound {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        List<UserBriefInfo> briefUserList = userInfoService.getBriefList(userKey);

        return MapUtils.toList(briefUserList, userInfoMapper::toDto);
    }

    public List<UserInfoKnownDto> getUserInfoKnownList(Jwt jwt) throws AccountNotFound {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        List<UserKnownInfo> knownUserList = userInfoService.getKnownList(userKey);

        return MapUtils.toList(knownUserList, userInfoMapper::toDto);
    }

    public List<UserInfoTrustedDto> getUserInfoTrustedList(Jwt jwt) throws AccountNotFound {
        UserKey userKey = jwtProcessor.getUserKey(jwt);
        List<UserTrustedInfo> trustedUserList = userInfoService.getTrustedList(userKey);

        return MapUtils.toList(trustedUserList, userInfoMapper::toDto);
    }
}
