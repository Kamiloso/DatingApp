package page.se3.datingapp.application.service.relation.apply;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.SubService;
import page.se3.datingapp.application.repository.ChatRepository;
import page.se3.datingapp.application.repository.MatchRepository;
import page.se3.datingapp.application.service.relation.assembly.SingleRelationAssembler;
import page.se3.datingapp.domain.exception.abort.reason.relation.RelationNotFound;
import page.se3.datingapp.domain.model.relation.Relation;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.domain.model.user.AuthKey;

import java.time.Instant;

@SubService
@RequiredArgsConstructor
public class RelationApplier {

    private final SingleRelationAssembler singleRelationAssembler;
    private final MatchRepository matchRepository;
    private final ChatRepository chatRepository;

    public void setMatchIsApplied(AuthKey authKey, Long idMatch, boolean isApplied) throws RelationNotFound {
        Relation relation = singleRelationAssembler.getByIdMatch(idMatch)
                .orElseThrow(RelationNotFound::new);

        boolean isFirst = relation.match().containsIdUserAsFirst(authKey.id());

        if (isFirst) {
            matchRepository.updateIsAppliedByFirstById(idMatch, isApplied);
        } else {
            matchRepository.updateIsAppliedBySecondById(idMatch, isApplied);
        }
    }

    public void createChatIfNotExists(Long idMatch) {
        Chat chat = Chat.getBeginnerInitial(Instant.now(), idMatch);
        chatRepository.saveIfNotExists(chat);
    }
}
