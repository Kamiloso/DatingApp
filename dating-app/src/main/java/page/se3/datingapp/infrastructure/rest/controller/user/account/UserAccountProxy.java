package page.se3.datingapp.infrastructure.rest.controller.user.account;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.service.user.account.UserAccountService;
import page.se3.datingapp.domain.exception.abort.reason.AccountAlreadyExists;
import page.se3.datingapp.domain.exception.abort.reason.AccountNotFound;
import page.se3.datingapp.application.service.user.account.struct.UserRegisterRequest;
import page.se3.datingapp.domain.model.user.account.UserAccount;
import page.se3.datingapp.infrastructure.rest.uri.Response;
import page.se3.datingapp.infrastructure.rest.uri.UriPattern;
import page.se3.datingapp.infrastructure.rest.controller.user.account.dto.UserAccountRegisterRequestDto;
import page.se3.datingapp.infrastructure.rest.controller.user.account.dto.UserAccountDto;
import page.se3.datingapp.infrastructure.rest.JwtProcessor;

@Service
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
            UserAccountRegisterRequestDto userAccountRegisterRequestDto
    ) throws AccountAlreadyExists {
        String email = jwtProcessor.getEmail(jwt);

        UserRegisterRequest userRegisterRequest = userAccountMapper.toDomain(userAccountRegisterRequestDto);
        UserAccount userAccount = userAccountService.register(email, userRegisterRequest);

        return Response.created(
                userAccountMapper.toDto(userAccount),
                UriPattern.current()
        );
    }
}
