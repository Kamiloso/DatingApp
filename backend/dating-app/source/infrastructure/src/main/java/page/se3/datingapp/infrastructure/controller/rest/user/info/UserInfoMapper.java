package page.se3.datingapp.infrastructure.controller.rest.user.info;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.domain.model.user.info.UserInfoBrief;
import page.se3.datingapp.domain.model.user.info.UserInfoKnown;
import page.se3.datingapp.domain.model.user.info.UserInfoTrusted;
import page.se3.datingapp.infrastructure.controller.rest.user.info.dto.UserInfoBriefDto;
import page.se3.datingapp.infrastructure.controller.rest.user.info.dto.UserInfoKnownDto;
import page.se3.datingapp.infrastructure.controller.rest.user.info.dto.UserInfoTrustedDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserInfoMapper {

    UserInfoBriefDto toDto(UserInfoBrief userInfoBrief);
    UserInfoKnownDto toDto(UserInfoKnown userInfoKnown);
    UserInfoTrustedDto toDto(UserInfoTrusted userInfoTrusted);
}
