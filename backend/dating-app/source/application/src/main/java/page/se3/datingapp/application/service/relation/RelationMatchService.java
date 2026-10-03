package page.se3.datingapp.application.service.relation;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.annotation.transaction.ReadCommitted;
import page.se3.datingapp.application.service.relation.repository.ChatRepository;
import page.se3.datingapp.application.service.relation.repository.MatchRepository;
import page.se3.datingapp.application.service.relation.assembler.MultiRelationAssembler;
import page.se3.datingapp.application.service.relation.assembler.SingleRelationAssembler;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.relation.match.RelationMatchApplyPatch;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.domain.model.relation.match.RelationMatch;
import page.se3.datingapp.domain.model.user.AuthKey;

import java.time.Instant;
import java.util.List;

@RootService
@RequiredArgsConstructor
public class RelationMatchService {

    private final MultiRelationAssembler multiRelationAssembler;
    private final SingleRelationAssembler singleRelationAssembler;
    private final MatchRepository matchRepository;
    private final ChatRepository chatRepository;

    public RelationMatch getMatch(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return singleRelationAssembler.getByIdMatch(idMatch)
                .filter(r -> r.match().containsIdUser(authKey.id()))
                .map(RelationMatch::getFrom)
                .orElseThrow(RelationNotFound::new);
    }

    public List<RelationMatch> getMatchList(AuthKey authKey, int withChat, boolean blocked) {
        return Streams.toList(
                multiRelationAssembler.getByIdUserForApplicationPurposes(authKey.id(), withChat, blocked),
                RelationMatch::getFrom
        );
    }

    @ReadCommitted
    public RelationMatch setMatchApply(AuthKey authKey, Long idMatch, RelationMatchApplyPatch relationMatchApplyPatch)
            throws RelationNotFound {
        boolean isApplied = relationMatchApplyPatch.isAppliedByYou();

        matchRepository.updateIsAppliedOfIdUserById(idMatch, authKey.id(), isApplied);

        RelationMatch relationMatch = singleRelationAssembler.getByIdMatch(idMatch)
                .map(RelationMatch::getFrom)
                .orElseThrow(RelationNotFound::new);

        if (relationMatch.shouldCreateChat()) {
            Chat chat = Chat.selfDeriveInitial(Instant.now(), idMatch);
            chatRepository.createIfNotExists(chat);
            relationMatch = relationMatch.withChatExists(true);
        }

        return relationMatch;
    }
}
