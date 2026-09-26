package page.se3.datingapp.infrastructure.rest.controller.user.account;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.application.service.user.account.struct.UserRegisterRequest;
import page.se3.datingapp.domain.model.user.account.UserAccount;
import page.se3.datingapp.infrastructure.rest.controller.user.account.dto.UserAccountRegisterRequestDto;
import page.se3.datingapp.infrastructure.rest.controller.user.account.dto.UserAccountDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserAccountMapper {

    UserAccountDto toDto(UserAccount userAccount);
    UserRegisterRequest toDomain(UserAccountRegisterRequestDto userAccountRegisterRequestDto);
}
