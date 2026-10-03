package page.se3.datingapp.domain.model.matching;

import page.se3.datingapp.domain.core.struct.Weight;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.model.block.BlockMap;
import page.se3.datingapp.domain.model.matching.matcher.Matcher;
import page.se3.datingapp.domain.model.user.User;

import java.util.*;

public record MatchingProcess(
        List<User> readyUsers,
        BlockMap blockMap,
        Matcher matcher
) {

    public MatchingProcess {
        readyUsers = List.copyOf(readyUsers);
    }

    public List<Matching> run() {
        Map<Matching.Key, Matching> matchingMap = new HashMap<>();

        for (User user : readyUsers) {
            List<Matching> topUserMatches = findTopUserMatches(user);

            for (Matching matching : topUserMatches) {
                var key = matching.getKey();
                matchingMap.merge(key, matching, Matching::merge);
            }
        }

        return new ArrayList<>(matchingMap.values());
    }

    private List<Matching> findTopUserMatches(User userMain) {
        PriorityQueue<Weight<User>> topMatches = new PriorityQueue<>();

        for (User userMatch : readyUsers) {
            if (!isMatchingAllowed(userMain, userMatch)) {
                continue;
            }

            int score = matcher.computeScore(userMain, userMatch);
            topMatches.offer(new Weight<>(userMatch, score));

            if (topMatches.size() > matcher.matchCountLimit()) {
                topMatches.poll();
            }
        }

        return Streams.toList(topMatches, userMatchWeight -> Matching.getSingle(
                userMain.id(),
                userMatchWeight.item().id(),
                userMatchWeight.weight()
        ));
    }

    private boolean isMatchingAllowed(User userMain, User userMatch) {
        boolean isSameUser = Objects.equals(userMain.id(), userMatch.id());
        boolean isBlocked = blockMap.isPairBlocked(userMain.id(), userMatch.id());
        boolean meetsRequirements = matcher.meetsRequirements(userMain, userMatch);

        return !isSameUser && !isBlocked && meetsRequirements;
    }
}
