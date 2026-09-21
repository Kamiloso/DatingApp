package page.se3.datingapp.old.onion.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.struct.Pair;
import page.se3.datingapp.old.core.struct.Weight;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.infrastructure.config.AppConfig;
import page.se3.datingapp.old.model.struct.dto.table.User;
import page.se3.datingapp.old.onion.service.matching.tool.BlockingMap;
import page.se3.datingapp.old.onion.service.matching.tool.Matching;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchingBrainService {

    private final AppConfig appConfig;
    private final FilteringService filteringService;
    private final ScoringService scoringService;

    public List<Matching> matchUsers(List<User> userList, BlockingMap blockingMap) {
        Map<Pair<Long, Long>, Matching> matchingMap = new HashMap<>();

        for (User userMain : userList) {
            List<Matching> userMatchingList = getUserMatches(userMain, userList, blockingMap);

            for (Matching matching : userMatchingList) {
                Pair<Long, Long> key = new Pair<>(matching.idUserFirst(), matching.idUserSecond());
                matchingMap.merge(key, matching, Matching::merge);
            }
        }

        return new ArrayList<>(matchingMap.values());
    }

    private List<Matching> getUserMatches(User userMain, List<User> userList, BlockingMap blockingMap) {
        PriorityQueue<Weight<User>> userTopHeap = new PriorityQueue<>();

        for (User userMatch : userList) {
            if (Objects.equals(userMain, userMatch)
                    || blockingMap.isUserPairBlocked(userMain, userMatch)
                    || !filteringService.meetsMatchRequirements(userMain, userMatch)
            ) continue;

            int score = scoringService.computeMatchScore(userMain, userMatch);
            userTopHeap.offer(new Weight<>(userMatch, score));

            if (userTopHeap.size() > appConfig.matching().maxMatchesPerUser()) {
                userTopHeap.poll();
            }
        }

        return topHeapToMatchingList(userMain, userTopHeap);
    }

    private List<Matching> topHeapToMatchingList(User userMain, PriorityQueue<Weight<User>> userTopHeap) {
        return MapUtils.toList(
                userTopHeap,
                weightUserMatch -> new Matching(
                        userMain.id(),
                        weightUserMatch.item().id(),
                        weightUserMatch.weight()
                ).normalize()
        );
    }
}
