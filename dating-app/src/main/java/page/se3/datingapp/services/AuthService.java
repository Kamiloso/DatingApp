package page.se3.datingapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import page.se3.datingapp.aborting.AbortResponseException;
import page.se3.datingapp.models.mappers.UserMapper;
import page.se3.datingapp.models.dto.User;
import page.se3.datingapp.models.requests.UserRegisterRequest;
import page.se3.datingapp.models.entities.UserEntity;
import page.se3.datingapp.repositories.UserRepository;
import page.se3.datingapp.aborting.AbortType;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public interface AuthService {
    User login(Jwt jwt);
    User register(Jwt jwt, UserRegisterRequest userRegisterRequest);

    default void loginAndRun(Jwt jwt, Consumer<User> consumer) {
        consumer.accept(login(jwt));
    }

    default <R> R loginAndReturn(Jwt jwt, Function<User, R> function) {
        return function.apply(login(jwt));
    }
}

@Service
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User login(Jwt jwt) {
        String email = Objects.requireNonNull(jwt.getClaimAsString("email"));

        return userRepository
                .findByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new AbortResponseException(AbortType.USER_NOT_FOUND));
    }

    public User register(Jwt jwt, UserRegisterRequest userRegisterRequest) {
        String email = Objects.requireNonNull(jwt.getClaimAsString("email"));
        UserEntity userEntity = userMapper.toEntity(userRegisterRequest, email);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (DuplicateKeyException e) {
            throw new AbortResponseException(AbortType.USER_ALREADY_EXISTS);
        }

        return userMapper.toDto(userEntity);
    }
}
