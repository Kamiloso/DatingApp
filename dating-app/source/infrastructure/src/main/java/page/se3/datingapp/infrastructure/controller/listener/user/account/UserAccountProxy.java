package page.se3.datingapp.infrastructure.controller.listener.user.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.user.account.UserAccountService;
import page.se3.datingapp.application.service.user.account.struct.UserAccountBasePatch;
import page.se3.datingapp.application.service.user.account.struct.UserAccountDetailPatch;
import page.se3.datingapp.application.service.user.account.struct.UserAccountRegister;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountAlreadyExists;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountPatchDenied;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountBasePatchDto;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountDetailPatchDto;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountRegisterDto;
import page.se3.datingapp.infrastructure.controller.uri.Response;
import page.se3.datingapp.infrastructure.controller.uri.UriPattern;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountDto;

@Service
@AutoRetry
@RequiredArgsConstructor
public class UserAccountProxy {

    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;

    public UserAccountDto getUserAccount(String email) throws AccountNotFound {
        return MapUtils.map(
                userAccountService.get(email),
                userAccountMapper::toDto
        );
    }

    public ResponseEntity<@NonNull UserAccountDto> registerUserAccount(
            String email,
            UserAccountRegisterDto userAccountRegisterDto
    ) throws AccountAlreadyExists {
        UserAccountRegister userAccountRegister = userAccountMapper.toDomain(userAccountRegisterDto);

        UserAccountDto userAccountDto = MapUtils.map(
                userAccountService.register(email, userAccountRegister),
                userAccountMapper::toDto
        );

        return Response.created(userAccountDto, UriPattern.current());
    }

    public UserAccountDto patchUserAccountDetail(
            AuthKey authKey,
            UserAccountDetailPatchDto userAccountDetailPatchDto
    ) throws AccountNotFound {
        UserAccountDetailPatch userAccountDetailPatch = userAccountMapper.toDomain(userAccountDetailPatchDto);

        return MapUtils.map(
                userAccountService.patchDetail(authKey, userAccountDetailPatch),
                userAccountMapper::toDto
        );
    }

    public UserAccountDto patchUserAccountBase(
            AuthKey authKey,
            UserAccountBasePatchDto userAccountBasePatchDto
    ) throws AccountNotFound, AccountPatchDenied {
        UserAccountBasePatch userAccountBasePatch = userAccountMapper.toDomain(userAccountBasePatchDto);

        return MapUtils.map(
                userAccountService.patchBase(authKey, userAccountBasePatch),
                userAccountMapper::toDto
        );
    }
}
