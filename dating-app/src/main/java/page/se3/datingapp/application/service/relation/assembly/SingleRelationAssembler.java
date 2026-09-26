package page.se3.datingapp.application.service.relation.assembly;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import page.se3.datingapp.application.annotation.SubService;
import page.se3.datingapp.application.repository.ChatRepository;
import page.se3.datingapp.application.repository.MatchGroupRepository;
import page.se3.datingapp.application.repository.MatchRepository;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;
import page.se3.datingapp.domain.model.relation.Relation;

import java.util.Optional;

@Service
@SubService
@RequiredArgsConstructor
public class SingleRelationAssembler {

    private final MatchGroupRepository matchGroupRepository;
    private final MatchRepository matchRepository;
    private final ChatRepository chatRepository;

    public Optional<Relation> getByIdMatch(Long idMatch) {
        Match match = matchRepository.findById(idMatch).orElse(null);
        if (match == null) {
            return Optional.empty();
        }

        MatchGroup matchGroup = matchGroupRepository.findById(match.idMatchGroup()).orElse(null);
        if (matchGroup == null) {
            return Optional.empty();
        }

        Chat chat = chatRepository.findByIdMatch(match.id()).orElse(null);

        return Optional.of(new Relation(matchGroup, match, chat));
    }

    public Optional<Relation> getByIdChat(Long idChat) {
        Chat chat = chatRepository.findById(idChat).orElse(null);
        if (chat == null) {
            return Optional.empty();
        }

        Match match = matchRepository.findById(chat.idMatch()).orElse(null);
        if (match == null) {
            return Optional.empty();
        }

        MatchGroup matchGroup = matchGroupRepository.findById(match.idMatchGroup()).orElse(null);
        if (matchGroup == null) {
            return Optional.empty();
        }

        return Optional.of(new Relation(matchGroup, match, chat));
    }
}
