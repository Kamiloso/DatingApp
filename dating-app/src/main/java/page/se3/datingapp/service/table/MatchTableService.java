package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.util.DivisionUtils;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.struct.dto.Match;
import page.se3.datingapp.model.struct.entity.MatchEntity;
import page.se3.datingapp.model.mapper.MatchMapper;
import page.se3.datingapp.model.struct.post.MatchPost;
import page.se3.datingapp.repository.MatchRepository;

import java.util.List;
import java.util.function.Function;

public interface MatchTableService {
    List<Match> tryPostList(List<MatchPost> matchPostList) throws ReferencedResourceGone;
}

@Service
@RequiredArgsConstructor
class MatchTableServiceImpl implements MatchTableService {

    private final AppConfig appConfig;
    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Override
    public List<Match> tryPostList(List<MatchPost> matchPostList) throws ReferencedResourceGone {
        List<MatchEntity> matchEntityList = StreamMap.fromList(matchPostList, matchMapper::toEntity);

        Integer batchSize = appConfig.limits().batchSize();
        Function<List<MatchEntity>, List<MatchEntity>> converter = matchRepository::saveAll;

        try {
            matchEntityList = DivisionUtils.batchConvert(matchEntityList, batchSize, converter);
        } catch (DataIntegrityViolationException e) {
            throw new ReferencedResourceGone();
        }

        return StreamMap.fromList(matchEntityList, matchMapper::toDto);
    }
}
