package page.se3.datingapp.domain.model.relation.apply;

import page.se3.datingapp.domain.model.relation.match.Match;
import page.se3.datingapp.domain.model.relation.Relation;

public record RelationApply(
        Boolean isAppliedByYou,
        Boolean isAppliedByOther,
        Boolean chatExists
) {

    public static RelationApply getFrom(Relation relation, Long idUser) {
        Match match = relation.match();

        if (!match.containsIdUser(idUser)) {
            throw new IllegalArgumentException("Match doesn't contain provided idUser");
        }

        boolean isFirst = match.containsIdUserAsFirst(idUser);

        return new RelationApply(
                isFirst ? match.isAppliedByFirst() : match.isAppliedBySecond(),
                isFirst ? match.isAppliedBySecond() : match.isAppliedByFirst(),
                relation.chatExists()
        );
    }

    public RelationApply withChatExists(Boolean chatExists) {
        return new RelationApply(isAppliedByYou, isAppliedByOther, chatExists);
    }

    public boolean isFullyApplied() {
        return isAppliedByYou && isAppliedByOther;
    }

    public boolean shouldCreateChat() {
        return isFullyApplied() && !chatExists;
    }
}
