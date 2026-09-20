package page.se3.datingapp.onion.service.matching.tool;

import java.util.Objects;

public record Matching(
        Long idUserMain,
        Long idUserMatch,
        Integer score,
        Boolean isDoubleMatch
) {

    public Matching(Long idUserMain, Long idUserMatch, Integer score) {
        this(idUserMain, idUserMatch, score, false);
    }

    public Matching normalize() {
        Long idMin = Math.min(idUserMain, idUserMatch);
        Long idMax = Math.max(idUserMain, idUserMatch);

        return new Matching(idMin, idMax, score, isDoubleMatch);
    }

    public static Matching merge(Matching m1, Matching m2) {
        m1 = m1.normalize();
        m2 = m2.normalize();

        if (!Objects.equals(m1.idUserMain, m2.idUserMain) || !Objects.equals(m1.idUserMatch, m2.idUserMatch)) {
            throw new IllegalArgumentException("Unable to merge matching records");
        }

        Integer mergedScore = Math.max(m1.score, m2.score);

        return new Matching(m1.idUserMain, m1.idUserMatch, mergedScore, true);
    }
}
