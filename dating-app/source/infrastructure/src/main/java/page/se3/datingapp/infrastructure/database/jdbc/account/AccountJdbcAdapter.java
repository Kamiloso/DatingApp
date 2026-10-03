package page.se3.datingapp.infrastructure.database.jdbc.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import page.se3.datingapp.application.service.auth.repository.AccountRepository;
import page.se3.datingapp.domain.model.account.Account;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AccountJdbcAdapter implements AccountRepository {

    private final AccountJdbcRepository accountJdbcRepository;
    private final AccountMapper accountMapper;

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountJdbcRepository.findById(email).map(accountMapper::toDomain);
    }

    @Override
    public void insertOrReplace(Account account) {
        AccountEntity accountEntity = accountMapper.toEntity(account);
        accountJdbcRepository.insertOrReplace(accountEntity);
    }
}
