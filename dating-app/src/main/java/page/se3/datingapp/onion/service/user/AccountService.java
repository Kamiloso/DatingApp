package page.se3.datingapp.onion.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.abort.reason.AccountAlreadyExists;
import page.se3.datingapp.infrastructure.abort.reason.AccountNotFound;
import page.se3.datingapp.infrastructure.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.infrastructure.struct.AuthKey;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.request.UserAccountRegisterRequest;
import page.se3.datingapp.model.struct.post.UserPost;
import page.se3.datingapp.onion.accessor.UserAccessor;

import java.util.Objects;

public interface AccountService {
    User getUserAccount(Jwt jwt) throws AccountNotFound;
    User registerUserAccount(Jwt jwt, UserAccountRegisterRequest userAccountRegisterRequest) throws AccountAlreadyExists;

    default AuthKey getAuthKey(Jwt jwt) throws AccountNotFound {
        User userAccount = getUserAccount(jwt);
        return new AuthKey(userAccount.id(), userAccount.email());
    }
}

@Service
@RequiredArgsConstructor
class AccountServiceImpl implements AccountService {

    private final UserAccessor userAccessor;

    @Override
    public User getUserAccount(Jwt jwt) throws AccountNotFound {
        String email = Objects.requireNonNull(jwt.getClaimAsString("email"));

        return userAccessor.getByEmail(email)
                .orElseThrow(AccountNotFound::new);
    }

    @Override
    public User registerUserAccount(Jwt jwt, UserAccountRegisterRequest userAccountRegisterRequest) throws AccountAlreadyExists {
        String email = Objects.requireNonNull(jwt.getClaimAsString("email"));
        UserPost userPost = userAccountRegisterRequest.upgrade(email);

        try {
            return userAccessor.tryPost(userPost);
        } catch (DataIntegrityViolation e) {
            throw new AccountAlreadyExists();
        }
    }
}
