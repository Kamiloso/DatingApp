package page.se3.datingapp.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.model.mapper.MatchGroupMapper;
import page.se3.datingapp.model.struct.dto.MatchGroup;
import page.se3.datingapp.model.struct.entity.MatchGroupEntity;
import page.se3.datingapp.model.struct.post.MatchGroupPost;
import page.se3.datingapp.onion.repository.MatchGroupRepository;

public interface MatchGroupAccessor {
    MatchGroup post(MatchGroupPost matchGroupPost);
}

@Service
@RequiredArgsConstructor
class MatchGroupAccessorImpl implements MatchGroupAccessor {

    private final MatchGroupRepository matchGroupRepository;
    private final MatchGroupMapper matchGroupMapper;

    public MatchGroup post(MatchGroupPost matchGroupPost) {
        MatchGroupEntity matchGroupEntity = matchGroupMapper.toEntity(matchGroupPost);
        matchGroupEntity = matchGroupRepository.save(matchGroupEntity);

        return matchGroupMapper.toDto(matchGroupEntity);
    }
}
