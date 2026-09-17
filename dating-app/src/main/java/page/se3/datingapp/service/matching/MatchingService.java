package page.se3.datingapp.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import page.se3.datingapp.core.struct.Weight;
import page.se3.datingapp.core.util.StreamMap;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.infrastructure.exception.UnreachableException;
import page.se3.datingapp.model.struct.dto.MatchGroup;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.struct.post.MatchGroupPost;
import page.se3.datingapp.model.struct.post.MatchPost;
import page.se3.datingapp.service.matching.tool.BlockMap;
import page.se3.datingapp.service.table.BlockTableService;
import page.se3.datingapp.service.table.MatchGroupTableService;
import page.se3.datingapp.service.table.MatchTableService;
import page.se3.datingapp.service.table.UserTableService;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

public interface MatchingService {
    void runMatchingSchedule();
}

@Service
@RequiredArgsConstructor
class MatchingServiceImpl implements MatchingService {

    private final AppConfig appConfig;
    private final BlockTableService blockTableService;
    private final MatchGroupTableService matchGroupTableService;
    private final MatchTableService matchTableService;
    private final UserTableService userTableService;
    private final FilteringService filteringService;
    private final ScoringService scoringService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void runMatchingSchedule() {
        MatchGroup matchGroup = matchGroupTableService.post(createMatchGroupPost());

        List<User> userReadyList = userTableService.getForUpdateWhereReady();
        BlockMap blockMap = new BlockMap(blockTableService.getAll());

        List<MatchPost> matchPostList = createMatchPostList(matchGroup, userReadyList, blockMap);
        List<Long> idUserReadyList = StreamMap.fromList(userReadyList, User::id);

        try {
            var ignored = matchTableService.tryPostList(matchPostList);
        } catch (ReferencedResourceGone e) {
            throw new UnreachableException(e);
        }

        userTableService.setNotReadyByIdList(idUserReadyList);
    }

    private MatchGroupPost createMatchGroupPost() {
        Instant matchTime = Instant.now();
        Integer maxMatchesPerUser = appConfig.matching().maxMatchesPerUser();
        Integer readyIndex = appConfig.quiz().readyIndex();

        return new MatchGroupPost(matchTime, maxMatchesPerUser, readyIndex);
    }

    private List<MatchPost> createMatchPostList(MatchGroup matchGroup, List<User> userList, BlockMap blockMap) {
        List<MatchPost> matchPostList = new ArrayList<>();

        for (User userMain : userList) {
            List<Weight<User>> userMatchList = createUserMatchList(userMain, userList, blockMap);

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

    private List<Weight<User>> createUserMatchList(User userMain, List<User> userList, BlockMap blockMap) {
        AppConfig.Matching matchingConfig = appConfig.matching();
        PriorityQueue<Weight<User>> userTopHeap = new PriorityQueue<>();

        for (User userMatch : userList) {
            if (Objects.equals(userMain, userMatch)
                    || blockMap.isBlockedPair(userMain, userMatch)
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
