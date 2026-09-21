package page.se3.datingapp.old.onion.service.matching;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.infrastructure.util.Transaction;
import page.se3.datingapp.old.infrastructure.exception.reason.DataIntegrityViolation;
import page.se3.datingapp.old.infrastructure.config.AppConfig;
import page.se3.datingapp.old.infrastructure.exception.UnreachableCodeException;
import page.se3.datingapp.old.model.struct.dto.table.Block;
import page.se3.datingapp.old.model.struct.dto.table.Match;
import page.se3.datingapp.old.model.struct.dto.table.MatchGroup;
import page.se3.datingapp.old.model.struct.dto.table.User;
import page.se3.datingapp.old.model.struct.post.MatchGroupPost;
import page.se3.datingapp.old.onion.service.matching.tool.Matching;
import page.se3.datingapp.old.onion.service.matching.tool.MatchingGroup;
import page.se3.datingapp.old.model.struct.post.MatchPost;
import page.se3.datingapp.old.onion.service.matching.tool.BlockingMap;
import page.se3.datingapp.old.onion.accessor.BlockAccessor;
import page.se3.datingapp.old.onion.accessor.MatchGroupAccessor;
import page.se3.datingapp.old.onion.accessor.MatchAccessor;
import page.se3.datingapp.old.onion.accessor.UserAccessor;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchingService {

    private final AppConfig appConfig;
    private final BlockAccessor blockAccessor;
    private final MatchGroupAccessor matchGroupAccessor;
    private final MatchAccessor matchAccessor;
    private final UserAccessor userAccessor;
    private final MatchingBrainService matchingBrainService;

    @Transaction.ReadCommitted
    public void runMatchingSchedule() {
        MatchGroup matchGroup = provideNewMatchGroup();
        List<User> userList = userAccessor.getWhereReadyForUpdate();
        BlockingMap blockingMap = getBlockingMap();

        List<Matching> matchingList = matchingBrainService.matchUsers(userList, blockingMap);
        List<MatchPost> matchPostList = getMatchPostList(matchGroup, matchingList);

        List<Long> idUserList = MapUtils.toList(userList, User::id);

        try {
            var ignored = matchAccessor.tryPostList(matchPostList);
        } catch (DataIntegrityViolation e) {
            throw new UnreachableCodeException(e);
        }

        userAccessor.setNotReadyByIdList(idUserList);
    }

    private MatchGroup provideNewMatchGroup() {
        MatchGroupPost matchGroupPost = new MatchingGroup(
                appConfig.matching().maxMatchesPerUser(),
                appConfig.quiz().readyIndex()
        ).derivePost();

        return matchGroupAccessor.post(matchGroupPost);
    }

    private BlockingMap getBlockingMap() {
        BlockingMap blockingMap = new BlockingMap();
        blockingMap.applyList(matchAccessor.getAll(), Match::idUserFirst, Match::idUserSecond);
        blockingMap.applyList(blockAccessor.getAll(), Block::idUserOwner, Block::idUserBlocked);

        return blockingMap;
    }

    private static List<MatchPost> getMatchPostList(MatchGroup matchGroup, List<Matching> matchingList) {
        return MapUtils.toList(
                matchingList,
                m -> m.derivePost(matchGroup.id())
        );
    }
}
