package page.se3.datingapp.domain.model.relation.chat;

import page.se3.datingapp.domain.core.annotation.MayBeNull;

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

    public static Chat selfDeriveInitial(Instant beginTime, Long idMatch) {
        return getInitial(beginTime, false, idMatch);
    }
}
