package page.se3.datingapp.old.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.DivisionUtils;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.infrastructure.util.Transaction;
import page.se3.datingapp.old.infrastructure.exception.reason.DataIntegrityViolation;
import page.se3.datingapp.old.infrastructure.config.AppConfig;
import page.se3.datingapp.old.model.struct.dto.table.User;
import page.se3.datingapp.old.model.struct.entity.UserEntity;
import page.se3.datingapp.old.model.mapper.table.UserMapper;
import page.se3.datingapp.old.model.struct.post.UserPost;
import page.se3.datingapp.old.onion.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class UserAccessor {

    private final AppConfig appConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }

    public List<User> getWhereReadyForUpdate() {
        Integer readyIndex = appConfig.quiz().readyIndex();

        return MapUtils.toList(
                userRepository.findByReadyIndexWhereIsParticipatingForUpdate(readyIndex),
                userMapper::toDto
        );
    }

    public List<User> getByIdList(List<Long> idList) {
        Integer batchSize = appConfig.limits().batchSize();
        Function<List<Long>, List<UserEntity>> converter = userRepository::findAllById;

        return MapUtils.toList(
                DivisionUtils.batchConvert(idList, batchSize, converter),
                userMapper::toDto
        );
    }

    public User tryPost(UserPost userPost) throws DataIntegrityViolation {
        UserEntity userEntity = userMapper.toEntity(userPost);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return userMapper.toDto(userEntity);
    }

    @Transaction.Mandatory
    public void setNotReadyByIdList(List<Long> idList) {
        Integer batchSize = appConfig.limits().batchSize();
        Consumer<List<Long>> processor = userRepository::resetIsParticipatingByIdList;

        DivisionUtils.batchProcess(idList, batchSize, processor);
    }
}
