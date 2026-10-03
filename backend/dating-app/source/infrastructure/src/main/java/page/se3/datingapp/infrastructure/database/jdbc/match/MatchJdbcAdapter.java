package page.se3.datingapp.infrastructure.database.jdbc.match;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.transaction.RequiresTransaction;
import page.se3.datingapp.application.service.relation.repository.MatchRepository;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.infrastructure.database.service.BatchProcessor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchJdbcAdapter implements MatchRepository {

    private final BatchProcessor batchProcessor;
    private final MatchJdbcRepository matchJdbcRepository;
    private final MatchMapper matchMapper;

    @Override
    public Optional<Match> findById(Long id) {
        return matchJdbcRepository.findById(id).map(matchMapper::toDomain);
    }

    @Override
    public Optional<Match> findByIdUserDirectForApplicationPurposes(Long idUser, Long idUserOther, Boolean blocked) {
        return matchJdbcRepository.findByIdUserDirectForApplicationPurposes(idUser, idUserOther, blocked)
                .map(matchMapper::toDomain);
    }

    @Override
    public List<Match> findAllByIdUserList(List<Long> idUserList) {
        return Streams.toList(
                batchProcessor.convert(idUserList, matchJdbcRepository::findAllByIdUserList),
                matchMapper::toDomain
        );
    }

    @Override
    public List<Match> findAllByIdUserForApplicationPurposes(Long idUser, Boolean blocked) {
        return Streams.toList(
                matchJdbcRepository.findAllByIdUserForApplicationPurposes(idUser, blocked),
                matchMapper::toDomain
        );
    }

    @Override
    @RequiresTransaction
    public List<Match> trySaveAll(List<Match> matchList) throws DatabaseConflictException {
        List<MatchEntity> matchEntityList = Streams.toList(matchList, matchMapper::toEntity);

        try {
            matchEntityList = batchProcessor.convert(matchEntityList, matchJdbcRepository::saveAll);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseConflictException(e);
        }

        return Streams.toList(matchEntityList, matchMapper::toDomain);
    }

    @Override
    public void updateIsAppliedOfIdUserById(Long id, Long idUser, Boolean isApplied) {
        matchJdbcRepository.updateIsAppliedOfIdUserById(id, idUser, isApplied);
    }
}
