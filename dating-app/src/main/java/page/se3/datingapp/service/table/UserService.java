package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.abort.reason.UserAlreadyExists;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.struct.entity.UserEntity;
import page.se3.datingapp.model.mapper.UserMapper;
import page.se3.datingapp.model.struct.post.UserPost;
import page.se3.datingapp.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getByEmail(String email);
    List<User> getWhereReadyToParticipate();

    void updateIsParticipatingById(Long id, Boolean isParticipating);
    void resetIsParticipating();

    User tryPost(UserPost userPost) throws UserAlreadyExists;
}

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final AppConfig appConfig;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(userMapper::toDto);
    }

    @Override
    public List<User> getWhereReadyToParticipate() {
        AppConfig.Quiz quizConfig = appConfig.quiz();

        return userRepository
                .findWhereIsParticipatingAndMatchesReadyIndex(quizConfig.readyIndex())
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public void updateIsParticipatingById(Long id, Boolean isParticipating) {
        userRepository.updateIsParticipatingById(id, isParticipating);
    }

    @Override
    public void resetIsParticipating() {
        userRepository.updateIsParticipating(false);
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
}
