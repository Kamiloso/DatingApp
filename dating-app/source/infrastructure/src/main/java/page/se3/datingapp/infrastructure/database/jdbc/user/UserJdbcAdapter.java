package page.se3.datingapp.infrastructure.database.jdbc.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.transaction.RequiresTransaction;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.application.service.user.repository.UserRepository;
import page.se3.datingapp.domain.model.user.User;
import page.se3.datingapp.domain.exception.DatabaseConflictException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserJdbcAdapter implements UserRepository {

    private final UserJdbcRepository userJdbcRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJdbcRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    @RequiresTransaction
    public Optional<User> findByIdForUpdate(Long id) {
        return userJdbcRepository.findByIdForUpdate(id).map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByIdWhereIsActive(Long id) {
        return userJdbcRepository.findByIdWhereIsActive(id).map(userMapper::toDomain);
    }

    @Override
    @RequiresTransaction
    public List<User> findAllWhereReady(Integer matchingIndex) {
        return Streams.toList(
                userJdbcRepository.findAllWhereReady(matchingIndex),
                userMapper::toDomain
        );
    }

    @Override
    public User trySave(User user) throws DatabaseConflictException {
        UserEntity userEntity = userMapper.toEntity(user);

        try {
            userEntity = userJdbcRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseConflictException(e);
        }

        return userMapper.toDomain(userEntity);
    }
}
