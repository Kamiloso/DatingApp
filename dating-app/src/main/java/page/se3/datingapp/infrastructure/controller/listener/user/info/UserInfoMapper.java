package page.se3.datingapp.infrastructure.controller.listener.user.info;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.user.info.UserBriefInfo;
import page.se3.datingapp.domain.model.user.info.UserKnownInfo;
import page.se3.datingapp.domain.model.user.info.UserTrustedInfo;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoBriefDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoKnownDto;
import page.se3.datingapp.infrastructure.controller.listener.user.info.dto.UserInfoTrustedDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserInfoMapper {

    UserInfoBriefDto toDto(UserBriefInfo userBriefInfo);
    UserInfoKnownDto toDto(UserKnownInfo userKnownInfo);
    UserInfoTrustedDto toDto(UserTrustedInfo userTrustedInfo);
}
