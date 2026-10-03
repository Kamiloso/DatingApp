package page.se3.datingapp.infrastructure.controller.rest.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.Final;

@Schema(description = "Struct that stores a single verification code")
public record SendVerificationCodeDto(
        @NotNull
        @Size(max = Final.LEN_255)
        String email
) {}
