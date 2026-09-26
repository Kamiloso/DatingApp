package page.se3.datingapp.infrastructure.controller.listener.user.account;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import page.se3.datingapp.application.service.user.account.struct.UserAccountRegister;
import page.se3.datingapp.domain.model.user.account.UserAccount;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountRegisterDto;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserAccountMapper {

    UserAccountDto toDto(UserAccount userAccount);
    UserAccountRegister toDomain(UserAccountRegisterDto userAccountRegisterDto);
}
