package page.se3.datingapp.domain.model.relation.chat;

import page.se3.datingapp.domain.model.relation.Relation;

import java.time.Instant;

public record RelationChat(
        Long idMatch,
        Long idUserFirst,
        Long idUserSecond,
        Instant matchTime,
        Integer score,
        Boolean isDoubleMatch,
        Instant beginTime,
        Boolean isUpgraded
) {

    public static RelationChat getFrom(Relation relation) {
        return new RelationChat(
                relation.match().id(),
                relation.match().idUserFirst(),
                relation.match().idUserSecond(),
                relation.matchGroup().matchTime(),
                relation.match().score(),
                relation.match().isDoubleMatch(),
                relation.chat().beginTime(),
                relation.chat().isUpgraded()
        );
    }
}
