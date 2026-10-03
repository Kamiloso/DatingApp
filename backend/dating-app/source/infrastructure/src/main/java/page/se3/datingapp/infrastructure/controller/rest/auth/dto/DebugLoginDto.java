package page.se3.datingapp.infrastructure.controller.rest.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.Final;

@Schema(description = "Debug login request with just email")
public record DebugLoginDto(
        @NotNull
        @Size(max = Final.LEN_255)
        String email
) {}
