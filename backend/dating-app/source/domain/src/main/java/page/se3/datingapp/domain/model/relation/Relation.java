package page.se3.datingapp.domain.model.relation;

import page.se3.datingapp.domain.core.annotation.MayBeNull;
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

    public boolean meetsChatRequirements(int withChat) {
        return (withChat >= 0 && chatExists()) || (withChat <= 0 && !chatExists());
    }
}
