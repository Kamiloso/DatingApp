package page.se3.datingapp.infrastructure.jdbc.database.global;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import page.se3.datingapp.application.repository.GlobalRepository;
import page.se3.datingapp.application.annotation.Transaction;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GlobalJdbcAdapter implements GlobalRepository {

    private final GlobalJdbcRepository globalJdbcRepository;

    @Override
    public Optional<String> findByKey(String key) {
        return globalJdbcRepository.findById(key).map(GlobalEntity::colValue);
    }

    @Override
    @Transaction.Mandatory
    public Optional<String> findByKeyForUpdate(String key) {
        return globalJdbcRepository.findByIdForUpdate(key).map(GlobalEntity::colValue);
    }

    @Override
    public void saveOrReplace(String key, String value) {
        globalJdbcRepository.saveOrReplace(key, value);
    }
}
