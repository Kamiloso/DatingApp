package page.se3.datingapp.infrastructure.database.jdbc.matchgroup;

import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;

public interface MatchGroupJdbcRepository extends ListCrudRepository<@NonNull MatchGroupEntity, @NonNull Long> {
}
