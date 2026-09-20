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
import page.se3.datingapp.onion.service.PostGetService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserAccessor userAccessor;
    private final PostGetService postGetService;

    public User getUserAccount(Jwt jwt) throws AccountNotFound {
        String email = Objects.requireNonNull(jwt.getClaimAsString("email"));

        return userAccessor.getByEmail(email)
                .orElseThrow(AccountNotFound::new);
    }

    public User registerUserAccount(
            Jwt jwt,
            UserAccountRegisterRequest userAccountRegisterRequest
    ) throws AccountAlreadyExists {
        String email = Objects.requireNonNull(jwt.getClaimAsString("email"));

        UserPost userPost = postGetService.getUser(userAccountRegisterRequest, email);

        try {
            return userAccessor.tryPost(userPost);
        } catch (DataIntegrityViolation e) {
            throw new AccountAlreadyExists();
        }
    }

    public AuthKey getAuthKey(Jwt jwt) throws AccountNotFound {
        User userAccount = getUserAccount(jwt);
        return new AuthKey(userAccount.id(), userAccount.email());
    }
}
