package page.se3.datingapp.application.service.relation;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.RootService;
import page.se3.datingapp.application.service.relation.assembler.MultiRelationAssembler;
import page.se3.datingapp.application.service.relation.assembler.SingleRelationAssembler;
import page.se3.datingapp.domain.core.util.Streams;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.relation.Relation;
import page.se3.datingapp.domain.model.relation.chat.RelationChat;
import page.se3.datingapp.domain.model.user.AuthKey;

import java.util.List;

@RootService
@RequiredArgsConstructor
public class RelationChatService {

    private final MultiRelationAssembler multiRelationAssembler;
    private final SingleRelationAssembler singleRelationAssembler;

    public RelationChat getChat(AuthKey authKey, Long idMatch) throws RelationNotFound {
        return singleRelationAssembler.getByIdMatch(idMatch)
                .filter(r -> r.match().containsIdUser(authKey.id()))
                .filter(Relation::chatExists)
                .map(RelationChat::getFrom)
                .orElseThrow(RelationNotFound::new);
    }

    public List<RelationChat> getChatList(AuthKey authKey, boolean blocked) {
        return Streams.toList(
                multiRelationAssembler.getByIdUserForApplicationPurposes(authKey.id(), 1, blocked),
                RelationChat::getFrom
        );
    }
}
