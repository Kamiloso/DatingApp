package page.se3.datingapp.service.table;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.model.struct.dto.Match;
import page.se3.datingapp.model.struct.entity.MatchEntity;
import page.se3.datingapp.model.mapper.MatchMapper;
import page.se3.datingapp.model.struct.post.MatchPost;
import page.se3.datingapp.repository.MatchRepository;

import java.util.List;

public interface MatchService {
    List<Match> tryPostList(List<MatchPost> matchPostList) throws ReferencedResourceGone;
}

@Service
@RequiredArgsConstructor
class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchMapper matchMapper;

    @Override
    public List<Match> tryPostList(List<MatchPost> matchPostList) throws ReferencedResourceGone {
        List<MatchEntity> matchEntityList = matchPostList
                .stream()
                .map(matchMapper::toEntity)
                .toList();

        try {
            matchEntityList = matchRepository.saveAll(matchEntityList);
        } catch (DataIntegrityViolationException e) {
            throw new ReferencedResourceGone();
        }

        return matchEntityList
                .stream()
                .map(matchMapper::toDto)
                .toList();
    }
}
