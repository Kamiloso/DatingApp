package page.se3.datingapp.infrastructure.controller.rest.user.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.user.UserAccountService;
import page.se3.datingapp.domain.model.user.account.UserAccountBasePatch;
import page.se3.datingapp.domain.model.user.account.UserAccountDetailPatch;
import page.se3.datingapp.domain.model.user.account.UserAccountRegister;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountInsertionFailed;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountNotFound;
import page.se3.datingapp.domain.exception.abort.reason.account.AccountPatchDenied;
import page.se3.datingapp.domain.model.user.AuthKey;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountBasePatchDto;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountDetailPatchDto;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountRegisterDto;
import page.se3.datingapp.infrastructure.controller.service.Response;
import page.se3.datingapp.infrastructure.controller.rest.user.account.dto.UserAccountDto;

@Service
@AutoRetry
@RequiredArgsConstructor
public class UserAccountProxy {

    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;

    public UserAccountDto getUserAccount(String email) throws AccountNotFound {
        return Streams.map(
                userAccountService.get(email),
                userAccountMapper::toDto
        );
    }

    public ResponseEntity<@NonNull UserAccountDto> registerUserAccount(
            String email,
            UserAccountRegisterDto userAccountRegisterDto
    ) throws AccountInsertionFailed {
        UserAccountRegister userAccountRegister = userAccountMapper.toDomain(userAccountRegisterDto);

        UserAccountDto userAccountDto = Streams.map(
                userAccountService.register(email, userAccountRegister),
                userAccountMapper::toDto
        );

        return Response.created(userAccountDto);
    }

    public UserAccountDto patchUserAccountDetail(
            AuthKey authKey,
            UserAccountDetailPatchDto userAccountDetailPatchDto
    ) throws AccountNotFound {
        UserAccountDetailPatch userAccountDetailPatch = userAccountMapper.toDomain(userAccountDetailPatchDto);

        return Streams.map(
                userAccountService.patchDetail(authKey, userAccountDetailPatch),
                userAccountMapper::toDto
        );
    }

    public UserAccountDto patchUserAccountBase(
            AuthKey authKey,
            UserAccountBasePatchDto userAccountBasePatchDto
    ) throws AccountNotFound, AccountPatchDenied {
        UserAccountBasePatch userAccountBasePatch = userAccountMapper.toDomain(userAccountBasePatchDto);

        return Streams.map(
                userAccountService.patchBase(authKey, userAccountBasePatch),
                userAccountMapper::toDto
        );
    }
}
