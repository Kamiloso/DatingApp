package page.se3.datingapp.application.repository;

import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.domain.model.relation.match.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {

    Optional<Match> findById(Long id);

    Optional<Match> findByIdUserDirectForApplicationPurposes(Long idUser, Long idUserOther, Boolean blocked);

    List<Match> findAllByIdUserList(List<Long> idUserList);

    List<Match> findAllByIdUserForApplicationPurposes(Long idUser, Boolean blocked);

    List<Match> trySaveAll(List<Match> matchList) throws DataIntegrityViolation;

    void updateIsAppliedByFirstById(Long id, Boolean isAppliedByFirst);

    void updateIsAppliedBySecondById(Long id, Boolean isAppliedBySecond);
}
