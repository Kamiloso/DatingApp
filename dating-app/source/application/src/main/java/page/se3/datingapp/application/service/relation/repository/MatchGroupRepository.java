package page.se3.datingapp.application.service.relation.repository;

import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;

import java.util.List;
import java.util.Optional;

public interface MatchGroupRepository {

    Optional<MatchGroup> findById(Long id);

    List<MatchGroup> findAllByIdList(List<Long> idList);

    MatchGroup trySave(MatchGroup matchGroup) throws DatabaseConflictException;
}
