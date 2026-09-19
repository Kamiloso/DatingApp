package page.se3.datingapp.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.model.struct.dto.Block;
import page.se3.datingapp.model.mapper.BlockMapper;
import page.se3.datingapp.onion.repository.BlockRepository;

import java.util.List;

public interface BlockAccessor {
    List<Block> getByIdUserOwner(Long idUserOwner);
    List<Block> getAll();
}

@Service
@RequiredArgsConstructor
class BlockAccessorImpl implements BlockAccessor {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public List<Block> getByIdUserOwner(Long idUserOwner) {
        return StreamMap.fromList(
                blockRepository.findByIdUserOwner(idUserOwner),
                blockMapper::toDto
        );
    }

    public List<Block> getAll() {
        return StreamMap.fromList(
                blockRepository.findAll(),
                blockMapper::toDto
        );
    }
}
