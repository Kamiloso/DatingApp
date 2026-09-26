package page.se3.datingapp.domain.model.relation;

import page.se3.datingapp.domain.annotation.MayBeNull;
import page.se3.datingapp.domain.model.relation.chat.Chat;
import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.match.MatchGroup;

public record Relation(
        MatchGroup matchGroup,
        Match match,
        @MayBeNull Chat chat
) {

    public boolean chatExists() {
        return chat != null;
    }
}
