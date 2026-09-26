package page.se3.datingapp.infrastructure.jdbc.database.matchgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.application.repository.MatchGroupRepository;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.infrastructure.jdbc.BatchProcessor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchGroupJdbcAdapter implements MatchGroupRepository {

    private final BatchProcessor batchProcessor;
    private final MatchGroupJdbcRepository matchGroupJdbcRepository;
    private final MatchGroupMapper matchGroupMapper;

    @Override
    public Optional<MatchGroup> findById(Long id) {
        return matchGroupJdbcRepository.findById(id).map(matchGroupMapper::toDomain);
    }

    @Override
    public List<MatchGroup> findAllByIdList(List<Long> idList) {
        return MapUtils.toList(
                batchProcessor.convert(idList, matchGroupJdbcRepository::findAllById),
                matchGroupMapper::toDomain
        );
    }

    @Override
    public MatchGroup save(MatchGroup matchGroup) {
        MatchGroupEntity matchGroupEntity = matchGroupMapper.toEntity(matchGroup);
        matchGroupEntity = matchGroupJdbcRepository.save(matchGroupEntity);

        return matchGroupMapper.toDomain(matchGroupEntity);
    }
}
