package page.se3.datingapp.model.struct.dto;

import jakarta.validation.constraints.NotNull;

public record Block(
        @NotNull
        Long idUserOwner,

        @NotNull
        Long idUserBlocked
) {}
