package page.se3.datingapp.application.service.matching;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.annotation.transaction.ReadCommitted;
import page.se3.datingapp.application.service.matching.indexer.MatchingIndexer;
import page.se3.datingapp.domain.config.app.matching.MatchingConfig;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.UnexpectedRuntimeException;
import page.se3.datingapp.domain.exception.DatabaseConflictException;
import page.se3.datingapp.application.service.block.repository.BlockRepository;
import page.se3.datingapp.application.service.relation.repository.MatchGroupRepository;
import page.se3.datingapp.application.service.relation.repository.MatchRepository;
import page.se3.datingapp.application.service.user.repository.UserRepository;
import page.se3.datingapp.domain.model.block.BlockMap;
import page.se3.datingapp.domain.model.block.Block;
import page.se3.datingapp.domain.model.matching.MatchingContext;
import page.se3.datingapp.domain.model.matching.MatchingProcess;
import page.se3.datingapp.domain.service.matcher.UserMatcher;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.domain.model.user.User;

import java.time.Instant;
import java.util.List;

@RootService
@RequiredArgsConstructor
public class MatchingUseCase {

    private final UserMatcher userMatcher;
    private final MatchingConfig matchingConfig;
    private final MatchingIndexer matchingIndexer;
    private final BlockRepository blockRepository;
    private final MatchGroupRepository matchGroupRepository;
    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    @ReadCommitted
    public void execute() {
        MatchingContext ctx = buildContext();
        MatchingProcess matchingProcess = ctx.getProcess(userMatcher);

        List<Match> matches = Streams.toList(matchingProcess.run(), ctx::getMatch);

        try {
            var ignored = matchRepository.trySaveAll(matches);
        } catch (DatabaseConflictException e) {
            throw new UnexpectedRuntimeException(e);
        }
    }

    private MatchingContext buildContext() {
        int matchingIndex = matchingIndexer.getIncrementedForUpdate();

        MatchGroup matchGroup = MatchGroup.selfDeriveInitial(Instant.now(), matchingConfig.matchCountLimit());

        try {
            matchGroup = matchGroupRepository.trySave(matchGroup);
        } catch (DatabaseConflictException e) {
            throw new UnexpectedRuntimeException(e);
        }

        List<User> users = userRepository.findAllWhereReady(matchingIndex);
        List<Long> idUserList = Streams.toList(users, User::id);

        BlockMap blockMap = BlockMap.builder()
                .withBlocking(matchRepository.findAllByIdUserList(idUserList),
                        Match::idUserFirst, Match::idUserSecond
                )
                .withBlocking(blockRepository.findAllByIdUserList(idUserList),
                        Block::idUserOwner, Block::idUserBlocked
                )
                .build();

        return new MatchingContext(matchGroup, users, blockMap);
    }
}
