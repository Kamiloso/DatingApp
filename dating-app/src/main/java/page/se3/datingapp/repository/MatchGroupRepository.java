package page.se3.datingapp.repository;

import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.model.struct.entity.MatchGroupEntity;

public interface MatchGroupRepository extends ListCrudRepository<@NonNull MatchGroupEntity, @NonNull Long> {
}
