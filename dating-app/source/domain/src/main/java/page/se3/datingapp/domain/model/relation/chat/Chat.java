package page.se3.datingapp.domain.model.relation.chat;

import page.se3.datingapp.domain.annotation.MayBeNull;

import java.time.Instant;

public record Chat(
        @MayBeNull Long id,
        Instant beginTime,
        Boolean isUpgraded,
        Long idMatch
) {

    public static Chat getInitial(Instant beginTime, Boolean isUpgraded, Long idMatch) {
        return new Chat(null, beginTime, isUpgraded, idMatch);
    }
}
