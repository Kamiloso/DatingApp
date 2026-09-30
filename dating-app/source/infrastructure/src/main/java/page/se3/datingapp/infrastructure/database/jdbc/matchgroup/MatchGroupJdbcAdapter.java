package page.se3.datingapp.infrastructure.database.jdbc.matchgroup;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.application.repository.MatchGroupRepository;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.infrastructure.database.service.BatchProcessor;

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
    public MatchGroup trySave(MatchGroup matchGroup) throws DatabaseConflictException {
        MatchGroupEntity matchGroupEntity = matchGroupMapper.toEntity(matchGroup);

        try {
            matchGroupEntity = matchGroupJdbcRepository.save(matchGroupEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseConflictException(e);
        }

        return matchGroupMapper.toDomain(matchGroupEntity);
    }
}
