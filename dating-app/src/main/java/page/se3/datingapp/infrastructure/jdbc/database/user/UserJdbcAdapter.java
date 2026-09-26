package page.se3.datingapp.infrastructure.jdbc.database.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.application.repository.UserRepository;
import page.se3.datingapp.domain.model.user.User;
import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.infrastructure.jdbc.BatchProcessor;
import page.se3.datingapp.application.annotation.Transaction;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserJdbcAdapter implements UserRepository {

    private final BatchProcessor batchProcessor;
    private final UserJdbcRepository userJdbcRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJdbcRepository.findByEmail(email).map(userMapper::toDomain);
    }

    @Override
    public List<User> findAllByIdList(List<Long> idList) {
        return MapUtils.toList(
                batchProcessor.convert(idList, userJdbcRepository::findAllById),
                userMapper::toDomain
        );
    }

    @Override
    @Transaction.Mandatory
    public List<User> findAllWhereReadyForShare(Integer matchingIndex, Integer readyIndex) {
        return MapUtils.toList(
                userJdbcRepository.findAllWhereReadyForShare(matchingIndex, readyIndex),
                userMapper::toDomain
        );
    }

    @Override
    public User trySave(User user) throws DataIntegrityViolation {
        UserEntity userEntity = userMapper.toEntity(user);

        try {
            userEntity = userJdbcRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return userMapper.toDomain(userEntity);
    }
}
