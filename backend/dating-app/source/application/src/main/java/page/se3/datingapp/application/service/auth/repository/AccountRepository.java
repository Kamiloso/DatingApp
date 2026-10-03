package page.se3.datingapp.application.service.auth.repository;

import page.se3.datingapp.domain.model.account.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> findByEmail(String email);

    void insertOrReplace(Account account);
}
