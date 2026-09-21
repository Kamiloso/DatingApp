package page.se3.datingapp.old.onion.service.matching.tool;

import page.se3.datingapp.old.model.struct.post.MatchPost;

import java.util.Objects;

public record Matching(
        Long idUserFirst,
        Long idUserSecond,
        Integer score,
        Boolean isDoubleMatch
) {

    public Matching(Long idUserMain, Long idUserMatch, Integer score) {
        this(idUserMain, idUserMatch, score, false);
    }

    public Matching normalize() {
        Long idMin = Math.min(idUserFirst, idUserSecond);
        Long idMax = Math.max(idUserFirst, idUserSecond);

        return new Matching(idMin, idMax, score, isDoubleMatch);
    }

    public static Matching merge(Matching m1, Matching m2) {
        m1 = m1.normalize();
        m2 = m2.normalize();

        if (!Objects.equals(m1.idUserFirst, m2.idUserFirst) || !Objects.equals(m1.idUserSecond, m2.idUserSecond)) {
            throw new IllegalArgumentException("Unable to merge matching records");
        }

        Integer mergedScore = Math.max(m1.score, m2.score);

        return new Matching(m1.idUserFirst, m1.idUserSecond, mergedScore, true);
    }

    public MatchPost derivePost(Long idMatchGroup) {
        return new MatchPost(idUserFirst, idUserSecond, score, isDoubleMatch, idMatchGroup);
    }
}
