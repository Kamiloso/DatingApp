package page.se3.datingapp.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.model.struct.dto.Block;
import page.se3.datingapp.model.mapper.BlockMapper;
import page.se3.datingapp.onion.repository.BlockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockAccessor {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    public List<Block> getByIdUserOwner(Long idUserOwner) {
        return StreamMap.toList(blockRepository.findByIdUserOwner(idUserOwner), blockMapper::toDto);
    }

    public List<Block> getAll() {
        return StreamMap.toList(blockRepository.findAll(), blockMapper::toDto);
    }
}
