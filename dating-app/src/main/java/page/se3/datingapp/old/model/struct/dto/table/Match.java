package page.se3.datingapp.old.model.struct.dto.table;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.With;

import java.util.Objects;

@Schema(description = "Represents a complete match entry")
@With(AccessLevel.PRIVATE)
public record Match(
        @NotNull
        Long id,

        @NotNull
        Long idUserFirst,

        @NotNull
        Long idUserSecond,

        @NotNull
        Integer score,

        @NotNull
        Boolean isDoubleMatch,

        @NotNull
        Boolean isAppliedByFirst,

        @NotNull
        Boolean isAppliedBySecond,

        @NotNull
        Long idMatchGroup
) {

    public boolean isAccepted() {
        return isAppliedByFirst && isAppliedBySecond;
    }

    public boolean containsIdUserInFirstSlot(Long idUser) {
        return Objects.equals(idUser, idUserFirst);
    }

    public boolean containsIdUserInSecondSlot(Long idUser) {
        return Objects.equals(idUser, idUserSecond);
    }

    public boolean containsIdUser(Long idUser) {
        return containsIdUserInFirstSlot(idUser) || containsIdUserInSecondSlot(idUser);
    }
}
