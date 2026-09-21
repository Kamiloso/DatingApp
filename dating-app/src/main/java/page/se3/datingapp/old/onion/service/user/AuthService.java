package page.se3.datingapp.old.onion.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.infrastructure.exception.reason.AccountNotFound;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.struct.dto.table.User;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountService accountService;

    public AuthKey getAuthKey(Jwt jwt) throws AccountNotFound {
        User userAccount = accountService.getUserAccount(jwt);
        return new AuthKey(userAccount.id(), userAccount.email());
    }
}
