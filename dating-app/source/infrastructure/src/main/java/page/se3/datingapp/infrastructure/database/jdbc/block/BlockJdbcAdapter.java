package page.se3.datingapp.infrastructure.database.jdbc.block;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import page.se3.datingapp.application.service.block.repository.BlockRepository;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
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
        return Streams.toList(blockJdbcRepository.findAllByIdUserOwner(idUserOwner), blockMapper::toDomain);
    }

    @Override
    public List<Block> findAllByIdUserBlockedWhereNotSilent(Long idUserBlocked) {
        return Streams.toList(
                blockJdbcRepository.findAllByIdUserBlockedWhereNotSilent(idUserBlocked),
                blockMapper::toDomain
        );
    }

    @Override
    public List<Block> findAllByIdUserList(List<Long> idUserList) {
        return Streams.toList(
                batchProcessor.convert(idUserList, blockJdbcRepository::findAllByIdUserList),
                blockMapper::toDomain
        );
    }

    @Override
    public Block trySave(Block block) throws DatabaseConflictException {
        BlockEntity blockEntity = blockMapper.toEntity(block);

        try {
            blockEntity = blockJdbcRepository.save(blockEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseConflictException(e);
        }

        return blockMapper.toDomain(blockEntity);
    }

    @Override
    public void deleteById(Long id) {
        blockJdbcRepository.deleteById(id);
    }
}
