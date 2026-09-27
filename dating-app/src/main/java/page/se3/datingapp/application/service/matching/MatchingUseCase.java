package page.se3.datingapp.application.service.matching;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.annotation.transaction.ReadCommitted;
import page.se3.datingapp.application.service.matching.index.MatchingIndexer;
import page.se3.datingapp.domain.config.MatchingConfig;
import page.se3.datingapp.domain.config.QuizConfig;
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
import page.se3.datingapp.domain.model.matching.matcher.Matcher;
import page.se3.datingapp.domain.model.matching.matcher.impl.DefaultMatcher;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.domain.model.user.User;

import java.time.Instant;
import java.util.List;

@RootService
@RequiredArgsConstructor
public class MatchingUseCase {

    private final MatchingConfig matchingConfig;
    private final QuizConfig quizConfig;
    private final MatchingIndexer matchingIndexer;
    private final BlockRepository blockRepository;
    private final MatchGroupRepository matchGroupRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    @ReadCommitted
    public void execute() {
        MatchingContext context = buildContext();
        List<Match> matches = generateMatches(context);
        saveMatches(matches);
    }

    private MatchingContext buildContext() {
        int matchingIndex = matchingIndexer.getIncrementedForUpdate();

        MatchGroup matchGroup = matchGroupRepository.save(MatchGroup.getInitial(
                Instant.now(),
                matchingConfig.matchCountLimit(),
                quizConfig.readyIndex()
        ));

        List<User> users = userRepository.findAllWhereReadyForShare(
                matchingIndex,
                quizConfig.readyIndex()
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
        Matcher matcher = new DefaultMatcher(matchingConfig);
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
