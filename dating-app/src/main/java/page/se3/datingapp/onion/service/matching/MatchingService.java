package page.se3.datingapp.onion.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.core.struct.Pair;
import page.se3.datingapp.core.struct.Weight;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.infrastructure.Transaction;
import page.se3.datingapp.infrastructure.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.infrastructure.exception.UnreachableCodeException;
import page.se3.datingapp.model.struct.dto.Block;
import page.se3.datingapp.model.struct.dto.Match;
import page.se3.datingapp.model.struct.dto.MatchGroup;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.struct.post.MatchPost;
import page.se3.datingapp.onion.service.PostGetService;
import page.se3.datingapp.onion.service.matching.tool.BlockingMap;
import page.se3.datingapp.onion.accessor.BlockAccessor;
import page.se3.datingapp.onion.accessor.MatchGroupAccessor;
import page.se3.datingapp.onion.accessor.MatchAccessor;
import page.se3.datingapp.onion.accessor.UserAccessor;
import page.se3.datingapp.onion.service.matching.tool.Matching;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final AppConfig appConfig;
    private final BlockAccessor blockAccessor;
    private final MatchGroupAccessor matchGroupAccessor;
    private final MatchAccessor matchAccessor;
    private final UserAccessor userAccessor;
    private final FilteringService filteringService;
    private final PostGetService postGetService;
    private final ScoringService scoringService;

    @Transaction.ReadCommitted
    public void runMatchingSchedule() {
        MatchGroup matchGroup = matchGroupAccessor.post(postGetService.getMatchGroup());
        List<User> userList = userAccessor.getWhereReadyForUpdate();

        BlockingMap blockingMap = new BlockingMap();
        blockingMap.applyList(matchAccessor.getAll(), Match::idUserFirst, Match::idUserSecond);
        blockingMap.applyList(blockAccessor.getAll(), Block::idUserOwner, Block::idUserBlocked);

        List<Matching> matchingList = createMatchingList(userList, blockingMap);
        List<MatchPost> matchPostList = StreamMap.toList(
                matchingList,
                m -> postGetService.getMatch(m, matchGroup.id())
        );

        List<Long> idUserList = StreamMap.toList(userList, User::id);

        try {
            var ignored = matchAccessor.tryPostList(matchPostList);
        } catch (DataIntegrityViolation e) {
            throw new UnreachableCodeException(e);
        }

        userAccessor.setNotReadyByIdList(idUserList);
    }

    private List<Matching> createMatchingList(List<User> userList, BlockingMap blockingMap) {
        Map<Pair<Long, Long>, Matching> matchingMap = new HashMap<>();

        for (User userMain : userList) {
            List<Weight<User>> userMatchList = createUserMatchList(userMain, userList, blockingMap);

            for (Weight<User> weightUserMatch : userMatchList) {
                Matching matching = new Matching(
                        userMain.id(),
                        weightUserMatch.item().id(),
                        weightUserMatch.weight()
                ).normalize();

                Pair<Long, Long> key = new Pair<>(matching.idUserMain(), matching.idUserMatch());
                matchingMap.merge(key, matching, Matching::merge);
            }
        }

        return new ArrayList<>(matchingMap.values());
    }

    private List<Weight<User>> createUserMatchList(User userMain, List<User> userList, BlockingMap blockingMap) {
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

        return userTopHeap.stream().toList();
    }
}
