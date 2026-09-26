package page.se3.datingapp.infrastructure.rest.controller.block.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "A request to block user")
public record BlockAddRequestDto(
        @NotNull
        Long idUserBlocked,

        @NotNull
        Boolean isSilent
) {}
