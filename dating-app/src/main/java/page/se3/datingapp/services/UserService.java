package page.se3.datingapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.models.dto.User;
import page.se3.datingapp.repositories.UserRepository;

public interface UserService {
    void updateIsParticipating(User user, Boolean isParticipating);
}

@Service
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public void updateIsParticipating(User user, Boolean isParticipating) {
        try {
            userRepository.updateIsParticipating(
                    user.id(),
                    isParticipating
            );
        } catch (DataIntegrityViolationException ignored) {}
    }
}
