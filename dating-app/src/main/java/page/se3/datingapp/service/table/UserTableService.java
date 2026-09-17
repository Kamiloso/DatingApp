package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.DivisionUtils;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.infrastructure.abort.reason.UserAlreadyExists;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.struct.entity.UserEntity;
import page.se3.datingapp.model.mapper.UserMapper;
import page.se3.datingapp.model.struct.post.UserPost;
import page.se3.datingapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public interface UserTableService {
    Optional<User> getByEmail(String email);
    List<User> getForUpdateWhereReady();
    List<User> getByIdList(List<Long> idList);

    User tryPost(UserPost userPost) throws UserAlreadyExists;
    void setNotReadyByIdList(List<Long> idList);
}

@Service
@RequiredArgsConstructor
class UserTableServiceImpl implements UserTableService {

    private final AppConfig appConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDto);
    }

    @Override
    public List<User> getForUpdateWhereReady() {
        Integer readyIndex = appConfig.quiz().readyIndex();

        return StreamMap.fromList(
                userRepository.findForUpdateByReadyIndexWhereIsParticipating(readyIndex),
                userMapper::toDto
        );
    }

    @Override
    public List<User> getByIdList(List<Long> idList) {
        Integer batchSize = appConfig.limits().batchSize();
        Function<List<Long>, List<UserEntity>> converter = userRepository::findAllById;

        return StreamMap.fromList(
                DivisionUtils.batchConvert(idList, batchSize, converter),
                userMapper::toDto
        );
    }

    @Override
    public User tryPost(UserPost userPost) throws UserAlreadyExists {
        UserEntity userEntity = userMapper.toEntity(userPost);

        try {
            userEntity = userRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExists();
        }

        return userMapper.toDto(userEntity);
    }

    @Override
    public void setNotReadyByIdList(List<Long> idList) {
        Integer batchSize = appConfig.limits().batchSize();
        Consumer<List<Long>> processor = userRepository::resetIsParticipatingByIdList;

        DivisionUtils.batchProcess(idList, batchSize, processor);
    }
}
