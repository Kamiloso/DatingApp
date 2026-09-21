package page.se3.datingapp.old.model.struct.dto.table;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Objects;

@Schema(description = "Represents a complete chat entry")
public record Chat(
        @NotNull
        Long id,

        @NotNull
        Long idUserFirst,

        @NotNull
        Long idUserSecond,

        @NotNull
        Instant beginTime,

        @NotNull
        Boolean isUpgraded
) {

    public boolean containsIdUser(Long idUser) {
        return Objects.equals(idUser, idUserFirst) || Objects.equals(idUser, idUserSecond);
    }
}
