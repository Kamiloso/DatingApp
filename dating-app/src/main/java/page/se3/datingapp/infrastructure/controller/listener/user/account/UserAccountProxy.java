package page.se3.datingapp.infrastructure.controller.listener.user.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.AutoRetry;
import page.se3.datingapp.application.service.user.account.UserAccountService;
import page.se3.datingapp.application.service.user.account.struct.UserAccountRegister;
import page.se3.datingapp.domain.exception.abort.reason.AccountAlreadyExists;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.domain.model.user.account.UserAccount;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountRegisterDto;
import page.se3.datingapp.infrastructure.controller.uri.Response;
import page.se3.datingapp.infrastructure.controller.uri.UriPattern;
import page.se3.datingapp.infrastructure.controller.listener.user.account.dto.UserAccountDto;
import page.se3.datingapp.infrastructure.controller.service.JwtProcessor;

@Service
@AutoRetry
@RequiredArgsConstructor
public class UserAccountProxy {

    private final JwtProcessor jwtProcessor;
    private final UserAccountService userAccountService;
    private final UserAccountMapper userAccountMapper;

    public UserAccountDto getUserAccount(Jwt jwt) throws AccountNotFound {
        String email = jwtProcessor.getEmail(jwt);
        UserAccount userAccount = userAccountService.get(email);

        return userAccountMapper.toDto(userAccount);
    }

    public ResponseEntity<@NonNull UserAccountDto> registerUserAccount(
            Jwt jwt,
            UserAccountRegisterDto userAccountRegisterDto
    ) throws AccountAlreadyExists {
        String email = jwtProcessor.getEmail(jwt);

        UserAccountRegister userAccountRegister = userAccountMapper.toDomain(userAccountRegisterDto);
        UserAccount userAccount = userAccountService.register(email, userAccountRegister);

        return Response.created(
                userAccountMapper.toDto(userAccount),
                UriPattern.current()
        );
    }
}
