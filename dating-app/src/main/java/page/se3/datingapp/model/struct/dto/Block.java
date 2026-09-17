package page.se3.datingapp.model.struct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Represents a complete block entry")
public record Block(
        @NotNull
        Long idUserOwner,

        @NotNull
        Long idUserBlocked
) {}
