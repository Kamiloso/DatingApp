package page.se3.datingapp.onion.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
import page.se3.datingapp.model.struct.post.MatchGroupPost;
import page.se3.datingapp.model.struct.post.MatchPost;
import page.se3.datingapp.onion.service.matching.tool.BlockingMap;
import page.se3.datingapp.onion.accessor.BlockAccessor;
import page.se3.datingapp.onion.accessor.MatchGroupAccessor;
import page.se3.datingapp.onion.accessor.MatchAccessor;
import page.se3.datingapp.onion.accessor.UserAccessor;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final AppConfig appConfig;
    private final BlockAccessor blockAccessor;
    private final MatchGroupAccessor matchGroupAccessor;
    private final MatchAccessor matchAccessor;
    private final UserAccessor userAccessor;
    private final FilteringService filteringService;
    private final ScoringService scoringService;

    @Transaction.ReadCommitted
    public void runMatchingSchedule() {
        MatchGroup matchGroup = matchGroupAccessor.post(createMatchGroupPost());
        List<User> userList = userAccessor.getWhereReadyForUpdate();

        BlockingMap blockingMap = new BlockingMap();
        blockingMap.applyList(matchAccessor.getAll(), Match::idUserMain, Match::idUserMatch);
        blockingMap.applyList(blockAccessor.getAll(), Block::idUserOwner, Block::idUserBlocked);

        List<MatchPost> matchPostList = createMatchPostList(matchGroup, userList, blockingMap);
        List<Long> idUserList = StreamMap.fromList(userList, User::id);

        try {
            var ignored = matchAccessor.tryPostList(matchPostList);
        } catch (DataIntegrityViolation e) {
            throw new UnreachableCodeException(e);
        }

        userAccessor.setNotReadyByIdList(idUserList);
    }

    private MatchGroupPost createMatchGroupPost() {
        Instant matchTime = Instant.now();
        Integer maxMatchesPerUser = appConfig.matching().maxMatchesPerUser();
        Integer readyIndex = appConfig.quiz().readyIndex();

        return new MatchGroupPost(matchTime, maxMatchesPerUser, readyIndex);
    }

    private List<MatchPost> createMatchPostList(MatchGroup matchGroup, List<User> userList, BlockingMap blockingMap) {
        List<MatchPost> matchPostList = new ArrayList<>();

        for (User userMain : userList) {
            List<Weight<User>> userMatchList = createUserMatchList(userMain, userList, blockingMap);

            for (Weight<User> weightUserMatch : userMatchList) {
                User userMatch = weightUserMatch.item();
                Integer score = weightUserMatch.weight();

                matchPostList.add(new MatchPost(
                        userMain.id(),
                        userMatch.id(),
                        score,
                        matchGroup.id()
                ));
            }
        }

        return matchPostList;
    }

    private List<Weight<User>> createUserMatchList(User userMain, List<User> userList, BlockingMap blockingMap) {
        AppConfig.Matching matchingConfig = appConfig.matching();
        PriorityQueue<Weight<User>> userTopHeap = new PriorityQueue<>();

        for (User userMatch : userList) {
            if (Objects.equals(userMain, userMatch)
                    || blockingMap.isUserPairBlocked(userMain, userMatch)
                    || !filteringService.meetsMatchRequirements(userMain, userMatch)) {
                continue;
            }

            userTopHeap.offer(new Weight<>(
                    userMatch,
                    scoringService.computeMatchScore(userMain, userMatch)
            ));

            if (userTopHeap.size() > matchingConfig.maxMatchesPerUser()) {
                userTopHeap.poll();
            }
        }

        return userTopHeap.stream().toList();
    }
}
