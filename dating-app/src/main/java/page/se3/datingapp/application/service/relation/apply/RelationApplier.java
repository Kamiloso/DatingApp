package page.se3.datingapp.application.service.relation.apply;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.SubService;
import page.se3.datingapp.application.repository.ChatRepository;
import page.se3.datingapp.application.repository.MatchRepository;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.domain.model.relation.match.Match;

import java.time.Instant;

@SubService
@RequiredArgsConstructor
public class RelationApplier {

    private final MatchRepository matchRepository;
    private final ChatRepository chatRepository;

    public void setMatchIsApplied(Long idMatch, boolean isFirst, boolean isApplied) {
        if (isFirst) {
            matchRepository.updateIsAppliedByFirstById(idMatch, isApplied);
        } else {
            matchRepository.updateIsAppliedBySecondById(idMatch, isApplied);
        }
    }

    public void createChatIfNotExists(Match match) {
        Chat chat = match.deriveInitial(Instant.now());
        chatRepository.saveIfNotExists(chat);
    }
}
