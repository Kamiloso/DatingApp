package page.se3.datingapp.infrastructure.database.jdbc.account;

import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;

public interface AccountJdbcRepository extends ListCrudRepository<@NonNull AccountEntity, @NonNull String> {
}
