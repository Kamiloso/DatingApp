package page.se3.datingapp.infrastructure.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Stores response error information")
public record ProblemDto(
        @NotNull
        Integer status,

        @NotNull
        String errorType
) {}
