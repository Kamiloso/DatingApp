package page.se3.datingapp.infrastructure.controller.listener.block.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Represents a blockade from one user to another")
public record BlockDto(
        @NotNull
        Long id,

        @NotNull
        Long idUserOwner,

        @NotNull
        Long idUserBlocked,

        @NotNull
        Boolean isSilent
) {}
