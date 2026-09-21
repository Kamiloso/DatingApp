package page.se3.datingapp.old.onion.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.infrastructure.exception.reason.AccountAlreadyExists;
import page.se3.datingapp.old.infrastructure.exception.reason.AccountNotFound;
import page.se3.datingapp.old.infrastructure.exception.reason.DataIntegrityViolation;
import page.se3.datingapp.old.model.struct.dto.table.User;
import page.se3.datingapp.old.model.request.UserAccountRegisterRequest;
import page.se3.datingapp.old.model.struct.post.UserPost;
import page.se3.datingapp.old.onion.accessor.UserAccessor;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserAccessor userAccessor;

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

        UserPost userPost = userAccountRegisterRequest.derivePost(email);

        try {
            return userAccessor.tryPost(userPost);
        } catch (DataIntegrityViolation e) {
            throw new AccountAlreadyExists();
        }
    }
}
