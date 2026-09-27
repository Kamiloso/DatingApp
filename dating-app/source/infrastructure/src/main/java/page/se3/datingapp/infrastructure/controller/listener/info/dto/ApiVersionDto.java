package page.se3.datingapp.infrastructure.controller.listener.info.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Contains API version")
public record ApiVersionDto(
        @NotNull
        String apiVersion
) {}
