package page.se3.datingapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.abort.reason.UserAlreadyExists;
import page.se3.datingapp.infrastructure.abort.reason.UserNotFound;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.request.UserRegisterRequest;
import page.se3.datingapp.model.struct.post.UserPost;
import page.se3.datingapp.service.table.UserService;

import java.util.Objects;

public interface AuthService {
    User login(Jwt jwt) throws UserNotFound;
    User register(Jwt jwt, UserRegisterRequest userRegisterRequest) throws UserAlreadyExists;
}

@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {

    private final UserService userService;

    @Override
    public User login(Jwt jwt) throws UserNotFound {
        String email = Objects.requireNonNull(jwt.getClaimAsString("email"));

        return userService
                .getByEmail(email)
                .orElseThrow(UserNotFound::new);
    }

    @Override
    public User register(Jwt jwt, UserRegisterRequest userRegisterRequest) throws UserAlreadyExists {
        String email = Objects.requireNonNull(jwt.getClaimAsString("email"));
        UserPost userPost = userRegisterRequest.upgrade(email);

        return userService.tryPost(userPost);
    }
}
