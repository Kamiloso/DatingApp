package page.se3.datingapp.infrastructure.controller.rest.user.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.domain.enums.Gender;
import page.se3.datingapp.domain.Final;

@Schema(description = "A request to patch basic user account information")
public record UserAccountBasePatchDto(
        @NotNull
        @Size(max = Final.LEN_255)
        String name,

        @NotNull
        @Size(max = Final.LEN_255)
        String surname,

        @NotNull
        Gender gender,

        @NotNull
        @PositiveOrZero
        Integer age
) {}
