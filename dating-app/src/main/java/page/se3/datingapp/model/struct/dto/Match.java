package page.se3.datingapp.model.struct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Represents a complete match entry")
public record Match(
        @NotNull
        Long id,

        @NotNull
        Long idUserMain,

        @NotNull
        Long idUserMatch,

        @NotNull
        Integer score,

        @NotNull
        Long idMatchGroup
) {}
