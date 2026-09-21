package page.se3.datingapp.old.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.model.mapper.table.MatchGroupMapper;
import page.se3.datingapp.old.model.struct.dto.table.MatchGroup;
import page.se3.datingapp.old.model.struct.entity.MatchGroupEntity;
import page.se3.datingapp.old.model.struct.post.MatchGroupPost;
import page.se3.datingapp.old.onion.repository.MatchGroupRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchGroupAccessor {

    private final MatchGroupRepository matchGroupRepository;
    private final MatchGroupMapper matchGroupMapper;

    public Optional<MatchGroup> getById(Long id) {
        return matchGroupRepository.findById(id).map(matchGroupMapper::toDto);
    }

    public MatchGroup post(MatchGroupPost matchGroupPost) {
        MatchGroupEntity matchGroupEntity = matchGroupMapper.toEntity(matchGroupPost);
        matchGroupEntity = matchGroupRepository.save(matchGroupEntity);

        return matchGroupMapper.toDto(matchGroupEntity);
    }
}
