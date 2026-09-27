package page.se3.datingapp.infrastructure.controller.listener.block.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "A request to block user")
public record BlockAddDto(
        @NotNull
        Long idUserBlocked,

        @NotNull
        Boolean isSilent
) {}
