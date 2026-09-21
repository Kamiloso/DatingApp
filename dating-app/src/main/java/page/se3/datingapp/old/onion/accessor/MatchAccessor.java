package page.se3.datingapp.old.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.DivisionUtils;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.infrastructure.exception.reason.DataIntegrityViolation;
import page.se3.datingapp.old.infrastructure.config.AppConfig;
import page.se3.datingapp.old.infrastructure.util.Transaction;
import page.se3.datingapp.old.model.struct.dto.table.Match;
import page.se3.datingapp.old.model.struct.entity.MatchEntity;
import page.se3.datingapp.old.model.mapper.table.MatchMapper;
import page.se3.datingapp.old.model.struct.post.MatchPost;
import page.se3.datingapp.old.onion.repository.MatchRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MatchAccessor {

    private final AppConfig appConfig;
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    public Optional<Match> getById(Long id) {
        return matchRepository.findById(id).map(matchMapper::toDto);
    }

    public Optional<Match> getByIdForUpdate(Long id) {
        return matchRepository.findByIdForUpdate(id).map(matchMapper::toDto);
    }

    public List<Match> getAll() {
        return MapUtils.toList(matchRepository.findAll(), matchMapper::toDto);
    }

    public List<Match> getByIdUser(Long idUser) {
        return MapUtils.toList(matchRepository.findByIdUser(idUser), matchMapper::toDto);
    }

    @Transaction.Mandatory
    public List<Match> tryPostList(List<MatchPost> matchPostList) throws DataIntegrityViolation {
        List<MatchEntity> matchEntityList = MapUtils.toList(matchPostList, matchMapper::toEntity);

        Integer batchSize = appConfig.limits().batchSize();
        Function<List<MatchEntity>, List<MatchEntity>> converter = matchRepository::saveAll;

        try {
            matchEntityList = DivisionUtils.batchConvert(matchEntityList, batchSize, converter);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return MapUtils.toList(matchEntityList, matchMapper::toDto);
    }

    public void setIsAppliedByFirstById(Long id, Boolean isAppliedByFirst) {
        matchRepository.updateIsAppliedByFirstById(id, isAppliedByFirst);
    }

    public void setIsAppliedBySecondById(Long id, Boolean isAppliedBySecond) {
        matchRepository.updateIsAppliedBySecondById(id, isAppliedBySecond);
    }
}
