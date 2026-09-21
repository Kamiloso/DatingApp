package page.se3.datingapp.old.model.struct.dto.table;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Schema(description = "Represents a complete block entry")
public record Block(
        @NotNull
        Long idUserOwner,

        @NotNull
        Long idUserBlocked
) {

    public boolean containsAsOwnerIdUser(Long idUser) {
        return Objects.equals(idUser, idUserOwner);
    }
}
