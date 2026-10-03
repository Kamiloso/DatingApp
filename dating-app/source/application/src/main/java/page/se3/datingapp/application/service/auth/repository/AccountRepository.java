package page.se3.datingapp.application.service.auth.repository;

import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.account.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByEmail(String email);

    Account trySave(Account account) throws DatabaseConflictException;
}
