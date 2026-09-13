package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.model.struct.dto.Block;
import page.se3.datingapp.model.mapper.BlockMapper;
import page.se3.datingapp.repository.BlockRepository;

import java.util.List;

public interface BlockService {
    List<Block> getByIdUserOwner(Long idUserOwner);
    List<Block> getAll();
}

@Service
@RequiredArgsConstructor
class BlockServiceImpl implements BlockService {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public List<Block> getByIdUserOwner(Long idUserOwner) {
        return blockRepository
                .findByIdUserOwner(idUserOwner)
                .stream()
                .map(blockMapper::toDto)
                .toList();
    }

    public List<Block> getAll() {
        return blockRepository
                .findAll()
                .stream()
                .map(blockMapper::toDto)
                .toList();
    }
}
