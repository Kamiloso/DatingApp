package page.se3.datingapp.infrastructure.controller.rest.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Struct that stores a single JWT token")
public record JwtTokenDto(
        @NotNull
        String jwtToken
) {}
