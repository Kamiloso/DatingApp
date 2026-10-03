package page.se3.datingapp.application.service.relation.assembler;

import lombok.RequiredArgsConstructor;
import page.se3.datingapp.application.annotation.service.SubService;
import page.se3.datingapp.application.service.relation.repository.ChatRepository;
import page.se3.datingapp.application.service.relation.repository.MatchGroupRepository;
import page.se3.datingapp.application.service.relation.repository.MatchRepository;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.Relation;

import java.util.Optional;

@SubService
@RequiredArgsConstructor
public class SingleRelationAssembler {

    private final MatchGroupRepository matchGroupRepository;
    private final MatchRepository matchRepository;
    private final ChatRepository chatRepository;

    public Optional<Relation> getByIdMatch(Long idMatch) {
        return matchRepository.findById(idMatch).flatMap(this::assemblyFromMatch);
    }

    public Optional<Relation> getByIdChat(Long idChat) {
        return chatRepository.findById(idChat).flatMap(this::assemblyFromChat);
    }

    public Optional<Relation> getByIdUserDirectForApplicationPurposes(
            Long idUser, Long idUserOther, int withChat, boolean blocked
    ) {
        return matchRepository.findByIdUserDirectForApplicationPurposes(idUser, idUserOther, blocked)
                .flatMap(this::assemblyFromMatch)
                .filter(r -> r.meetsChatRequirements(withChat));
    }

    private Optional<Relation> assemblyFromMatch(Match match) {
        return matchGroupRepository.findById(match.idMatchGroup())
                .map(matchGroup -> new Relation(
                        matchGroup,
                        match,
                        chatRepository.findByIdMatch(match.id()).orElse(null)
                ));
    }

    private Optional<Relation> assemblyFromChat(Chat chat) {
        return matchRepository.findById(chat.idMatch())
                .flatMap(match -> matchGroupRepository.findById(match.idMatchGroup())
                        .map(matchGroup -> new Relation(matchGroup, match, chat))
                );
    }
}
