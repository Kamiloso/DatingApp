package page.se3.datingapp.infrastructure.controller.rest.user.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.user.UserAccountService;
import page.se3.datingapp.domain.model.user.account.UserAccountBasePatch;
import page.se3.datingapp.domain.model.user.account.UserAccountDetailPatch;
import page.se3.datingapp.domain.model.user.account.UserAccountRegister;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountInsertionFailed;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotRegistered;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountPatchDenied;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountBasePatchDto;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountDetailPatchDto;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountRegisterDto;
import page.se3.datingapp.infrastructure.controller.service.ResponseFactory;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountDto;
import page.se3.datingapp.infrastructure.controller.service.helper.Response;

@Service
@AutoRetry
@RequiredArgsConstructor
public class UserAccountProxy {

    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;
    private final ResponseFactory responseFactory;

    public UserAccountDto getUserAccount(String email) throws AccountNotRegistered {
        return Streams.map(
                userAccountService.get(email),
                userAccountMapper::toDto
        );
    }

    public Response<UserAccountDto> registerUserAccount(
            String email,
            UserAccountRegisterDto userAccountRegisterDto
    ) throws AccountInsertionFailed {
        UserAccountRegister userAccountRegister = userAccountMapper.toDomain(userAccountRegisterDto);

        UserAccountDto userAccountDto = Streams.map(
                userAccountService.register(email, userAccountRegister),
                userAccountMapper::toDto
        );

        return responseFactory.created(userAccountDto);
    }

    public UserAccountDto patchUserAccountDetail(
            AuthKey authKey,
            UserAccountDetailPatchDto userAccountDetailPatchDto
    ) throws AccountNotRegistered {
        UserAccountDetailPatch userAccountDetailPatch = userAccountMapper.toDomain(userAccountDetailPatchDto);

        return Streams.map(
                userAccountService.patchDetail(authKey, userAccountDetailPatch),
                userAccountMapper::toDto
        );
    }

    public UserAccountDto patchUserAccountBase(
            AuthKey authKey,
            UserAccountBasePatchDto userAccountBasePatchDto
    ) throws AccountNotRegistered, AccountPatchDenied {
        UserAccountBasePatch userAccountBasePatch = userAccountMapper.toDomain(userAccountBasePatchDto);

        return Streams.map(
                userAccountService.patchBase(authKey, userAccountBasePatch),
                userAccountMapper::toDto
        );
    }
}
