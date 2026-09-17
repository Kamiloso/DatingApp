package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.model.struct.dto.Block;
import page.se3.datingapp.model.mapper.BlockMapper;
import page.se3.datingapp.repository.BlockRepository;

import java.util.List;

public interface BlockTableService {
    List<Block> getByIdUserOwner(Long idUserOwner);
    List<Block> getAll();
}

@Service
@RequiredArgsConstructor
class BlockTableServiceImpl implements BlockTableService {

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
