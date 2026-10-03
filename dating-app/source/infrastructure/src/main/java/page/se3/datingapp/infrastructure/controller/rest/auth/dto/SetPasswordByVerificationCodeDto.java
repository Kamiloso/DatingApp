package page.se3.datingapp.infrastructure.controller.rest.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.Final;

@Schema(description = "Set password request with verification code")
public record SetPasswordByVerificationCodeDto(
        @NotNull
        @Size(max = Final.LEN_255)
        String verificationCode,

        @NotNull
        @Size(max = Final.LEN_255)
        String newPassword
) {}
