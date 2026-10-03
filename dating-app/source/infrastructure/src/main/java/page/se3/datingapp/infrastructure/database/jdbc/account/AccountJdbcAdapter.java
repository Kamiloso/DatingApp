package page.se3.datingapp.infrastructure.database.jdbc.account;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import page.se3.datingapp.application.service.auth.repository.AccountRepository;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
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
    public Account trySave(Account account) throws DatabaseConflictException {
        AccountEntity accountEntity = accountMapper.toEntity(account);

        try {
            accountEntity = accountJdbcRepository.save(accountEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseConflictException(e);
        }

        return accountMapper.toDomain(accountEntity);
    }
}
