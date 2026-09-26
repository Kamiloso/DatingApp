package page.se3.datingapp.infrastructure.database.jdbc.block;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import page.se3.datingapp.application.repository.BlockRepository;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.infrastructure.database.service.BatchProcessor;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BlockJdbcAdapter implements BlockRepository {

    private final BatchProcessor batchProcessor;
    private final BlockJdbcRepository blockJdbcRepository;
    private final BlockMapper blockMapper;

    @Override
    public Optional<Block> findById(Long id) {
        return blockJdbcRepository.findById(id).map(blockMapper::toDomain);
    }

    @Override
    public List<Block> findAllByIdUserOwner(Long idUserOwner) {
        return MapUtils.toList(blockJdbcRepository.findAllByIdUserOwner(idUserOwner), blockMapper::toDomain);
    }

    @Override
    public List<Block> findAllByIdUserBlockedWhereNotSilent(Long idUserBlocked) {
        return MapUtils.toList(
                blockJdbcRepository.findAllByIdUserBlockedWhereNotSilent(idUserBlocked),
                blockMapper::toDomain
        );
    }

    @Override
    public List<Block> findAllByIdUserList(List<Long> idUserList) {
        return MapUtils.toList(
                batchProcessor.convert(idUserList, blockJdbcRepository::findAllByIdUserList),
                blockMapper::toDomain
        );
    }

    @Override
    public Block trySave(Block block) throws DataIntegrityViolation {
        BlockEntity blockEntity = blockMapper.toEntity(block);

        try {
            blockEntity = blockJdbcRepository.save(blockEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return blockMapper.toDomain(blockEntity);
    }

    @Override
    public void deleteById(Long id) {
        blockJdbcRepository.deleteById(id);
    }
}
