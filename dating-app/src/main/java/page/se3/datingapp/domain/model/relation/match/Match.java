package page.se3.datingapp.domain.model.relation.match;

import page.se3.datingapp.domain.annotation.MayBeNull;
import page.se3.datingapp.domain.model.relation.chat.Chat;

import java.time.Instant;
import java.util.Objects;

public record Match(
        @MayBeNull Long id,
        Long idUserFirst,
        Long idUserSecond,
        Integer score,
        Boolean isDoubleMatch,
        Boolean isAppliedByFirst,
        Boolean isAppliedBySecond,
        Long idMatchGroup
) {

    public static Match getInitial(
            Long idUserFirst, Long idUserSecond, Integer score, Boolean isDoubleMatch,
            Boolean isAppliedByFirst, Boolean isAppliedBySecond, Long idMatchGroup
    ) {
        return new Match(
                null, idUserFirst, idUserSecond, score, isDoubleMatch,
                isAppliedByFirst, isAppliedBySecond, idMatchGroup
        );
    }

    public Chat deriveInitial(Instant beginTime) {
        return Chat.getInitial(beginTime, false, id);
    }

    public boolean isFullyApplied() {
        return isAppliedByFirst && isAppliedBySecond;
    }

    public boolean containsIdUserAsFirst(Long idUser) {
        return Objects.equals(idUser, idUserFirst);
    }

    public boolean containsIdUserAsSecond(Long idUser) {
        return Objects.equals(idUser, idUserSecond);
    }

    public boolean containsIdUser(Long idUser) {
        return containsIdUserAsFirst(idUser) || containsIdUserAsSecond(idUser);
    }
}
