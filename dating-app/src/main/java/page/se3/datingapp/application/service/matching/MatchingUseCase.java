package page.se3.datingapp.application.service.matching;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.RootService;
import page.se3.datingapp.application.annotation.Transaction;
import page.se3.datingapp.application.service.matching.index.MatchingIndexer;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.UnexpectedConflictException;
import page.se3.datingapp.domain.exception.abort.reason.DataIntegrityViolation;
import page.se3.datingapp.application.repository.BlockRepository;
import page.se3.datingapp.application.repository.MatchGroupRepository;
import page.se3.datingapp.application.repository.MatchRepository;
import page.se3.datingapp.application.repository.UserRepository;
import page.se3.datingapp.domain.model.block.BlockMap;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.domain.model.matching.MatchingContext;
import page.se3.datingapp.domain.model.matching.matcher.impl.DefaultMatcher;
import page.se3.datingapp.domain.model.matching.matcher.Matcher;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.domain.model.user.User;
import page.se3.datingapp.domain.config.AppConfig;

import java.time.Instant;
import java.util.List;

@Service
@RootService
@RequiredArgsConstructor
public class MatchingUseCase {

    private final AppConfig appConfig;
    private final MatchingIndexer matchingIndexer;
    private final BlockRepository blockRepository;
    private final MatchGroupRepository matchGroupRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    private volatile Matcher matcher;

    @PostConstruct
    private void init() {
        this.matcher = new DefaultMatcher(appConfig);
    }

    @Transaction.ReadCommitted
    public void execute() {
        MatchingContext context = buildContext();
        List<Match> matches = generateMatches(context);
        saveMatches(matches);
    }

    private MatchingContext buildContext() {
        int matchingIndex = matchingIndexer.getIncrementedForUpdate();

        MatchGroup matchGroup = matchGroupRepository.save(MatchGroup.getInitial(
                Instant.now(),
                appConfig.matching().matchCountLimit(),
                appConfig.quiz().readyIndex()
        ));

        List<User> users = userRepository.findAllWhereReadyForShare(
                matchingIndex,
                appConfig.quiz().readyIndex()
        );

        List<Long> idUserList = MapUtils.toList(users, User::id);

        BlockMap blockMap = BlockMap.builder()
                .withBlocking(
                        matchRepository.findAllByIdUserList(idUserList),
                        Match::idUserFirst,
                        Match::idUserSecond
                )
                .withBlocking(
                        blockRepository.findAllByIdUserList(idUserList),
                        Block::idUserOwner,
                        Block::idUserBlocked
                )
                .build();

        return new MatchingContext(matchGroup, users, blockMap);
    }

    private List<Match> generateMatches(MatchingContext ctx) {
        return MapUtils.toList(ctx.getProcess(matcher).run(), ctx::getMatch);
    }

    private void saveMatches(List<Match> matches) {
        try {
            var ignored = matchRepository.trySaveAll(matches);
        } catch (DataIntegrityViolation e) {
            throw new UnexpectedConflictException(e);
        }
    }
}
