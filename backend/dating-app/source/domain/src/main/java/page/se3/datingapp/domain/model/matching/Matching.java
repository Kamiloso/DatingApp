package page.se3.datingapp.domain.model.matching;

import page.se3.datingapp.domain.model.relation.match.Match;

public record Matching(
        Long idUserFirst,
        Long idUserSecond,
        Integer score,
        Boolean isDoubleMatch
) {

    public Matching {
        long min = Math.min(idUserFirst, idUserSecond);
        long max = Math.max(idUserFirst, idUserSecond);
        idUserFirst = min;
        idUserSecond = max;
    }

    public record Key(
            Long idUserFirst,
            Long idUserSecond
    ) {}

    public static Matching getSingle(Long idUserFirst, Long idUserSecond, Integer score) {
        return new Matching(idUserFirst, idUserSecond, score, false);
    }

    public static Matching getDouble(Long idUserFirst, Long idUserSecond, Integer score) {
        return new Matching(idUserFirst, idUserSecond, score, true);
    }

    public Matching merge(Matching other) {
        return Matching.getDouble(idUserFirst, idUserSecond, Math.max(score, other.score));
    }

    public Key getKey() {
        return new Key(idUserFirst, idUserSecond);
    }

    public Match deriveInitial(Long idMatchGroup) {
        return Match.getInitial(
                idUserFirst, idUserSecond, score, isDoubleMatch,
                false, false,
                idMatchGroup
        );
    }
}
