package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.model.mapper.MatchGroupMapper;
import page.se3.datingapp.model.struct.dto.MatchGroup;
import page.se3.datingapp.model.struct.entity.MatchGroupEntity;
import page.se3.datingapp.model.struct.post.MatchGroupPost;
import page.se3.datingapp.repository.MatchGroupRepository;

public interface MatchGroupTableService {
    MatchGroup post(MatchGroupPost matchGroupPost);
}

@Service
@RequiredArgsConstructor
class MatchGroupTableServiceImpl implements MatchGroupTableService {

    private final MatchGroupRepository matchGroupRepository;
    private final MatchGroupMapper matchGroupMapper;

    public MatchGroup post(MatchGroupPost matchGroupPost) {
        MatchGroupEntity matchGroupEntity = matchGroupMapper.toEntity(matchGroupPost);
        matchGroupEntity = matchGroupRepository.save(matchGroupEntity);

        return matchGroupMapper.toDto(matchGroupEntity);
    }
}
