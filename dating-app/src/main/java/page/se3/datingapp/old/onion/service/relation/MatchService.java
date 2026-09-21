package page.se3.datingapp.old.onion.service.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.old.core.util.MapUtils;
import page.se3.datingapp.old.infrastructure.exception.reason.*;
import page.se3.datingapp.old.infrastructure.exception.UnreachableCodeException;
import page.se3.datingapp.old.infrastructure.exception.reason.*;
import page.se3.datingapp.old.infrastructure.util.Transaction;
import page.se3.datingapp.old.model.struct.AuthKey;
import page.se3.datingapp.old.model.struct.dto.MatchReport;
import page.se3.datingapp.old.model.struct.dto.table.Block;
import page.se3.datingapp.old.model.struct.dto.table.Chat;
import page.se3.datingapp.old.model.struct.dto.table.Match;
import page.se3.datingapp.old.model.struct.dto.table.MatchGroup;
import page.se3.datingapp.old.model.struct.post.ChatPost;
import page.se3.datingapp.old.onion.accessor.BlockAccessor;
import page.se3.datingapp.old.onion.accessor.ChatAccessor;
import page.se3.datingapp.old.onion.accessor.MatchAccessor;
import page.se3.datingapp.old.onion.accessor.MatchGroupAccessor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final BlockAccessor blockAccessor;
    private final ChatAccessor chatAccessor;
    private final MatchAccessor matchAccessor;
    private final MatchGroupAccessor matchGroupAccessor;

    public List<Match> getList(AuthKey authKey, boolean blocked, boolean accepted) {
        Set<Long> blockSet = MapUtils.toSet(
                blockAccessor.getByIdUserOwner(authKey.id()),
                Block::idUserBlocked
        );

        return matchAccessor.getByIdUser(authKey.id()).stream()
                .filter(c -> blocked || !(blockSet.contains(c.idUserFirst()) || blockSet.contains(c.idUserSecond())))
                .filter(c -> accepted || !c.isAccepted())
                .toList();
    }

    @Transaction.ReadCommitted
    public MatchReport setIsApplied(AuthKey authKey, Long idMatch, Boolean isApplied)
            throws MatchNotFound, MatchAccessDenied, MatchAlreadyAccepted, ChatCreationFailed {
        Match match = processMatch(authKey, idMatch, isApplied);

        return new MatchReport(
                match,
                processMatchGroup(match),
                processChat(match).orElse(null)
        );
    }

    private Match processMatch(AuthKey authKey, Long idMatch, Boolean isApplied)
            throws MatchNotFound, MatchAccessDenied, MatchAlreadyAccepted {
        Match match = matchAccessor.getByIdForUpdate(idMatch)
                .orElseThrow(MatchNotFound::new);

        if (!match.containsIdUser(authKey.id())) {
            throw new MatchAccessDenied();
        }

        if (match.isAccepted()) {
            throw new MatchAlreadyAccepted();
        }

        if (match.containsIdUserInFirstSlot(authKey.id())) {
            matchAccessor.setIsAppliedByFirstById(match.id(), isApplied);
        } else {
            matchAccessor.setIsAppliedBySecondById(match.id(), isApplied);
        }

        match = matchAccessor.getById(match.id())
                .orElseThrow(UnreachableCodeException::new);

        return match;
    }

    private MatchGroup processMatchGroup(Match match) {
        return matchGroupAccessor.getById(match.idMatchGroup())
                .orElseThrow(UnreachableCodeException::new);
    }

    private Optional<Chat> processChat(Match match) throws ChatCreationFailed {
        Chat chat = chatAccessor.getByIdUserPair(match.idUserFirst(), match.idUserSecond())
                .orElse(null);

        if (chat == null && match.isAccepted()) {

            // TODO: such creation with the constructor shall not be here (wait for refactoring to improve)
            ChatPost chatPost = new ChatPost(
                    match.idUserFirst(),
                    match.idUserSecond(),
                    Instant.now(),
                    false
            );

            try {
                chat = chatAccessor.tryPost(chatPost);
            } catch (DataIntegrityViolation e) {
                throw new ChatCreationFailed();
            }
        }

        return Optional.ofNullable(chat);
    }
}
