package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.mapper.MatchGroupMapper;
import page.se3.datingapp.model.struct.dto.MatchGroup;
import page.se3.datingapp.model.struct.entity.MatchGroupEntity;
import page.se3.datingapp.model.struct.post.MatchGroupPost;
import page.se3.datingapp.repository.MatchGroupRepository;

import java.time.Instant;

public interface MatchGroupService {
    MatchGroup prepareAndPost();
}

@Service
@RequiredArgsConstructor
class MatchGroupServiceImpl implements MatchGroupService {

    private final AppConfig appConfig;
    private final MatchGroupRepository matchGroupRepository;
    private final MatchGroupMapper matchGroupMapper;

    public MatchGroup prepareAndPost() {
        AppConfig.Matching matchingConfig = appConfig.matching();
        AppConfig.Quiz quizConfig = appConfig.quiz();

        MatchGroupPost matchGroupPost = new MatchGroupPost(
                Instant.now(),
                matchingConfig.maxMatchesPerUser(),
                quizConfig.readyIndex()
        );

        MatchGroupEntity matchGroupEntity = matchGroupMapper.toEntity(matchGroupPost);
        matchGroupEntity = matchGroupRepository.save(matchGroupEntity);

        return matchGroupMapper.toDto(matchGroupEntity);
    }
}
