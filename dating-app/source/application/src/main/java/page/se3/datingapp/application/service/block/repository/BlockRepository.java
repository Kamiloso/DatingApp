package page.se3.datingapp.application.service.block.repository;

import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.domain.exception.DatabaseConflictException;

import java.util.List;
import java.util.Optional;

public interface BlockRepository {

    Optional<Block> findById(Long id);

    List<Block> findAllByIdUserOwner(Long idUserOwner);

    List<Block> findAllByIdUserBlockedWhereNotSilent(Long idUserBlocked);

    List<Block> findAllByIdUserList(List<Long> idUserList);

    Block trySave(Block block) throws DatabaseConflictException;

    void deleteById(Long id);
}
