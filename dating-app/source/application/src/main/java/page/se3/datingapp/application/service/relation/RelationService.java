package page.se3.datingapp.application.service.relation;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.annotation.transaction.ExplicitNoTransaction;
import page.se3.datingapp.application.service.relation.assembly.MultiRelationAssembler;
import page.se3.datingapp.application.service.relation.assembly.SingleRelationAssembler;
import page.se3.datingapp.application.service.relation.apply.RelationApplier;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.relation.Relation;
import page.se3.datingapp.domain.model.relation.RelationMatchApplyPatch;
import page.se3.datingapp.domain.model.relation.chat.RelationChat;
import page.se3.datingapp.domain.model.relation.match.RelationMatch;
import page.se3.datingapp.domain.model.user.AuthKey;

import java.util.List;

@RootService
@RequiredArgsConstructor
public class RelationService {

    private final MultiRelationAssembler multiRelationAssembler;
    private final SingleRelationAssembler singleRelationAssembler;
    private final RelationApplier relationApplier;

    public RelationMatch getMatch(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return singleRelationAssembler.getByIdMatch(idMatch)
                .filter(r -> r.match().containsIdUser(authKey.id()))
                .map(RelationMatch::getFrom)
                .orElseThrow(RelationNotFound::new);
    }

    public RelationChat getChat(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return singleRelationAssembler.getByIdMatch(idMatch)
                .filter(r -> r.match().containsIdUser(authKey.id()))
                .filter(Relation::chatExists)
                .map(RelationChat::getFrom)
                .orElseThrow(RelationNotFound::new);
    }

    public List<RelationMatch> getMatchList(AuthKey authKey, int withChat, boolean blocked) {
        return MapUtils.toList(
                multiRelationAssembler.getByIdUserForApplicationPurposes(authKey.id(), withChat, blocked),
                RelationMatch::getFrom
        );
    }

    public List<RelationChat> getChatList(AuthKey authKey, boolean blocked) {
        return MapUtils.toList(
                multiRelationAssembler.getByIdUserForApplicationPurposes(authKey.id(), 1, blocked),
                RelationChat::getFrom
        );
    }

    @ExplicitNoTransaction
    public RelationMatch setMatchApply(AuthKey authKey, Long idMatch, RelationMatchApplyPatch relationMatchApplyPatch)
            throws RelationNotFound {
        boolean isApplied = relationMatchApplyPatch.isAppliedByYou();

        relationApplier.setMatchIsApplied(authKey, idMatch, isApplied);

        RelationMatch relationMatch = singleRelationAssembler.getByIdMatch(idMatch)
                .map(RelationMatch::getFrom)
                .orElseThrow(RelationNotFound::new);

        if (relationMatch.shouldCreateChat()) {
            relationApplier.createChatIfNotExists(idMatch);
            relationMatch = relationMatch.withChatExists(true);
        }

        return relationMatch;
    }
}
