package page.se3.datingapp.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import page.se3.datingapp.core.type.Weight;
import page.se3.datingapp.infrastructure.abort.reason.ReferencedResourceGone;
import page.se3.datingapp.infrastructure.config.AppConfig;
import page.se3.datingapp.model.struct.dto.MatchGroup;
import page.se3.datingapp.model.struct.dto.User;
import page.se3.datingapp.model.struct.post.MatchPost;
import page.se3.datingapp.service.matching.tool.BlockMap;
import page.se3.datingapp.service.table.BlockService;
import page.se3.datingapp.service.table.MatchGroupService;
import page.se3.datingapp.service.table.MatchService;
import page.se3.datingapp.service.table.UserService;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.PriorityQueue;

public interface MatchingService {
    void runMatchingSchedule() throws ReferencedResourceGone;
}

@Service
@RequiredArgsConstructor
class MatchingServiceImpl implements MatchingService {

    private final AppConfig appConfig;
    private final BlockService blockService;
    private final FilteringService filteringService;
    private final MatchGroupService matchGroupService;
    private final MatchService matchService;
    private final ScoringService scoringService;
    private final UserService userService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void runMatchingSchedule() throws ReferencedResourceGone {
        MatchGroup matchGroup = matchGroupService.prepareAndPost();

        List<User> userList = userService.getWhereReadyToParticipate();
        BlockMap blockMap = new BlockMap(blockService.getAll());

        List<MatchPost> matchPostList = createMatchAddRequestList(matchGroup, userList, blockMap);

        var ignored = matchService.tryPostList(matchPostList);
        userService.resetIsParticipating();
    }

    private List<MatchPost> createMatchAddRequestList(
            MatchGroup matchGroup,
            List<User> userList,
            BlockMap blockMap
    ) {
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
