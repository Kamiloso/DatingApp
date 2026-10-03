package page.se3.datingapp.domain.model.block;

import page.se3.datingapp.domain.core.annotation.MayBeNull;

import java.util.Objects;

public record Block(
        @MayBeNull Long id,
        Long idUserOwner,
        Long idUserBlocked,
        Boolean isSilent
) {

    public static Block getInitial(Long idUserOwner, Long idUserBlocked, Boolean isSilent) {
        return new Block(null, idUserOwner, idUserBlocked, isSilent);
    }

    public boolean containsIdUserAsOwner(Long idUser) {
        return Objects.equals(idUser, idUserOwner);
    }

    public boolean containsIdUserAsBlocked(Long idUser) {
        return Objects.equals(idUser, idUserBlocked);
    }

    public boolean containsIdUser(Long idUser) {
        return containsIdUserAsOwner(idUser) || containsIdUserAsBlocked(idUser);
    }

    public boolean isSelfBlocked() {
        return Objects.equals(idUserOwner, idUserBlocked);
    }
}
