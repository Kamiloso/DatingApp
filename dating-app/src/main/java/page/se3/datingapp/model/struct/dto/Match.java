package page.se3.datingapp.model.struct.dto;

import jakarta.validation.constraints.NotNull;

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
