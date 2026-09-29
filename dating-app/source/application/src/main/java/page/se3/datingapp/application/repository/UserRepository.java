package page.se3.datingapp.application.repository;

import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.domain.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByIdForUpdate(Long id);

    Optional<User> findByIdWhereIsActive(Long id);

    List<User> findAllWhereReadyForShare(Integer matchingIndex, Integer readyIndex);

    User trySave(User user) throws DataIntegrityViolation;
}
