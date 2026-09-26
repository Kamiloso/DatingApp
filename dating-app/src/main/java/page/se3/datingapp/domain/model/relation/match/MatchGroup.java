package page.se3.datingapp.domain.model.relation.match;

import page.se3.datingapp.domain.annotation.MayBeNull;

import java.time.Instant;

public record MatchGroup(
        @MayBeNull Long id,
        Instant matchTime,
        Integer matchCountLimit,
        Integer readyIndex
) {

    public static MatchGroup getInitial(Instant matchTime, Integer matchCountLimit, Integer readyIndex) {
        return new MatchGroup(null, matchTime, matchCountLimit, readyIndex);
    }
}
