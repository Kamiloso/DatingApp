package page.se3.datingapp.onion.repository;

import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.model.struct.entity.MatchEntity;

public interface MatchRepository extends ListCrudRepository<@NonNull MatchEntity, @NonNull Long> {
}
