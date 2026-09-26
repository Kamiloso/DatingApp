package page.se3.datingapp.domain.model.relation.match;

import page.se3.datingapp.domain.model.relation.Relation;

import java.time.Instant;

public record RelationMatch(
        Long idMatch,
        Long idUserFirst,
        Long idUserSecond,
        Instant matchTime,
        Integer score,
        Boolean isDoubleMatch,
        Boolean isAppliedByFirst,
        Boolean isAppliedBySecond,
        Boolean chatExists
) {

    public static RelationMatch getFrom(Relation relation) {
        return new RelationMatch(
                relation.match().id(),
                relation.match().idUserFirst(),
                relation.match().idUserSecond(),
                relation.matchGroup().matchTime(),
                relation.match().score(),
                relation.match().isDoubleMatch(),
                relation.match().isAppliedByFirst(),
                relation.match().isAppliedBySecond(),
                relation.chatExists()
        );
    }
}
