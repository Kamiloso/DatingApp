package page.se3.datingapp.infrastructure.controller.rest.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.Final;

@Schema(description = "Login request with credentials")
public record LoginDto(
        @NotNull
        @Size(max = Final.LEN_255)
        String email,

        @NotNull
        @Size(max = Final.LEN_255)
        String password
) {}
