package page.se3.datingapp.model.struct.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public record Chat(
        @NotNull
        Long id,

        @NotNull
        Long idUserFirst,

        @NotNull
        Long idUserSecond,

        @NotNull
        Boolean isUpgraded
) {

    public boolean containsIdUser(Long idUser) {
        return Objects.equals(idUser, idUserFirst) || Objects.equals(idUser, idUserSecond);
    }
}
