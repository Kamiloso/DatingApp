package page.se3.datingapp.infrastructure.controller.listener.user.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.user.info.UserInfoService;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoBriefDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoKnownDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoTrustedDto;

import java.util.List;

@Service
@AutoRetry
@RequiredArgsConstructor
public class UserInfoProxy {

    private final UserInfoService userInfoService;
    private final UserInfoMapper userInfoMapper;

    public List<UserInfoBriefDto> getUserInfoBriefList(AuthKey authKey) {
        return MapUtils.toList(
                userInfoService.getBriefList(authKey),
                userInfoMapper::toDto
        );
    }

    public List<UserInfoKnownDto> getUserInfoKnownList(AuthKey authKey) {
        return MapUtils.toList(
                userInfoService.getKnownList(authKey),
                userInfoMapper::toDto
        );
    }

    public List<UserInfoTrustedDto> getUserInfoTrustedList(AuthKey authKey) {
        return MapUtils.toList(
                userInfoService.getTrustedList(authKey),
                userInfoMapper::toDto
        );
    }
}
