package page.se3.datingapp.onion.accessor;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.DivisionUtils;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.infrastructure.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.infrastructure.Transaction;
import page.se3.datingapp.model.struct.dto.Match;
import page.se3.datingapp.model.struct.entity.MatchEntity;
import page.se3.datingapp.model.mapper.MatchMapper;
import page.se3.datingapp.model.struct.post.MatchPost;
import page.se3.datingapp.onion.repository.MatchRepository;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MatchAccessor {

    private final AppConfig appConfig;
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    public List<Match> getAll() {
        return StreamMap.fromList(matchRepository.findAll(), matchMapper::toDto);
    }

    @Transaction.Mandatory
    public List<Match> tryPostList(List<MatchPost> matchPostList) throws DataIntegrityViolation {
        List<MatchEntity> matchEntityList = StreamMap.fromList(matchPostList, matchMapper::toEntity);

        Integer batchSize = appConfig.limits().batchSize();
        Function<List<MatchEntity>, List<MatchEntity>> converter = matchRepository::saveAll;

        try {
            matchEntityList = DivisionUtils.batchConvert(matchEntityList, batchSize, converter);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolation();
        }

        return StreamMap.fromList(matchEntityList, matchMapper::toDto);
    }
}
