package page.se3.datingapp.old.onion.repository;

import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;
import page.se3.datingapp.old.model.struct.entity.BlockEntity;

import java.util.List;

public interface BlockRepository extends ListCrudRepository<@NonNull BlockEntity, @NonNull Long> {

    List<BlockEntity> findByIdUserOwner(Long idUserOwner);
}
