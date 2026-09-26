package page.se3.datingapp.domain.model.matching;

import page.se3.datingapp.domain.model.block.BlockMap;
import page.se3.datingapp.domain.model.matching.matcher.Matcher;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.domain.model.user.User;

import java.util.List;

public record MatchingContext(
    MatchGroup matchGroup,
    List<User> readyUsers,
    BlockMap blockMap
) {

    public MatchingContext {
        readyUsers = List.copyOf(readyUsers);
    }

    public MatchingProcess getProcess(Matcher matcher) {
        return new MatchingProcess(readyUsers, blockMap, matcher);
    }

    public Match getMatch(Matching matching) {
        return matching.deriveInitial(matchGroup.id());
    }
}
