package page.se3.datingapp.application.service.user.repository;

import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByIdForUpdate(Long id);

    Optional<User> findByIdWhereIsActive(Long id);

    List<User> findAllWhereReady(Integer matchingIndex);

    User trySave(User user) throws DatabaseConflictException;
}
