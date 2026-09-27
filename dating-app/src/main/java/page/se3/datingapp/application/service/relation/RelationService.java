package page.se3.datingapp.application.service.relation;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.annotation.transaction.ReadCommitted;
import page.se3.datingapp.application.service.relation.assembly.MultiRelationAssembler;
import page.se3.datingapp.application.service.relation.assembly.SingleRelationAssembler;
import page.se3.datingapp.application.service.relation.apply.RelationApplier;
import page.se3.datingapp.domain.core.util.MapUtils;
import page.se3.datingapp.domain.exception.abort.reason.RelationNotFound;
import page.se3.datingapp.domain.model.relation.Relation;
import page.se3.datingapp.domain.model.relation.apply.RelationApply;
import page.se3.datingapp.application.service.relation.struct.RelationApplyPatch;
import page.se3.datingapp.domain.model.relation.chat.RelationChat;
import page.se3.datingapp.domain.model.relation.match.RelationMatch;
import page.se3.datingapp.domain.model.user.UserKey;

import java.util.List;

@RootService
@RequiredArgsConstructor
public class RelationService {

    private final MultiRelationAssembler multiRelationAssembler;
    private final SingleRelationAssembler singleRelationAssembler;
    private final RelationApplier relationApplier;

    public List<RelationMatch> getMatchList(UserKey userKey) {
        return MapUtils.toList(
                multiRelationAssembler.getByIdUserForApplicationPurposes(userKey.id(), 0, false),
                RelationMatch::getFrom
        );
    }

    public List<RelationChat> getChatList(UserKey userKey) {
        return MapUtils.toList(
                multiRelationAssembler.getByIdUserForApplicationPurposes(userKey.id(), 1, false),
                RelationChat::getFrom
        );
    }

    public RelationApply getApply(UserKey userKey, Long idMatch) throws RelationNotFound {
        Relation relation = singleRelationAssembler.getByIdMatch(idMatch)
                .orElseThrow(RelationNotFound::new);

        return RelationApply.getFrom(relation, userKey.id());
    }

    @ReadCommitted
    public RelationApply setApply(UserKey userKey, Long idMatch, RelationApplyPatch relationApplyPatch)
            throws RelationNotFound {
        Relation relation = singleRelationAssembler.getByIdMatch(idMatch)
                .orElseThrow(RelationNotFound::new);

        boolean isFirst = relation.match().containsIdUserAsFirst(userKey.id());
        boolean isApplied = relationApplyPatch.isAppliedByYou();

        relationApplier.setMatchIsApplied(idMatch, isFirst, isApplied);

        relation = singleRelationAssembler.getByIdMatch(idMatch)
                .orElseThrow(RelationNotFound::new);

        RelationApply relationApply = RelationApply.getFrom(relation, userKey.id());

        if (relationApply.shouldCreateChat()) {
            relationApplier.createChatIfNotExists(relation.match());
            relationApply = relationApply.withChatExists(true);
        }

        return relationApply;
    }
}
