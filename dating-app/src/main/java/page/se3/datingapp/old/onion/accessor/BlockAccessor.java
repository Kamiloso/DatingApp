package page.se3.datingapp.old.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.infrastructure.exception.reason.DataIntegrityViolation;
import page.se3.datingapp.old.model.struct.dto.table.Block;
import page.se3.datingapp.old.model.mapper.table.BlockMapper;
import page.se3.datingapp.old.model.struct.entity.BlockEntity;
import page.se3.datingapp.old.model.struct.post.BlockPost;
import page.se3.datingapp.old.onion.repository.BlockRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlockAccessor {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public Optional<Block> getById(Long id) {
        return blockRepository.findById(id).map(blockMapper::toDto);
    }

    public List<Block> getAll() {
        return MapUtils.toList(blockRepository.findAll(), blockMapper::toDto);
    }

    public List<Block> getByIdUserOwner(Long idUserOwner) {
        return MapUtils.toList(blockRepository.findByIdUserOwner(idUserOwner), blockMapper::toDto);
    }

    public Block tryPost(BlockPost blockPost) throws DataIntegrityViolation {
        BlockEntity blockEntity = blockMapper.toEntity(blockPost);

        try {
            blockEntity = blockRepository.save(blockEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return blockMapper.toDto(blockEntity);
    }

    public void removeById(Long id) {
        blockRepository.deleteById(id);
    }
}
