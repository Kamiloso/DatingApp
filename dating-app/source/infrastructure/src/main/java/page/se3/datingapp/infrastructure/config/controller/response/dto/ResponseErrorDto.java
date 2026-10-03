package page.se3.datingapp.infrastructure.config.controller.response.dto;

import jakarta.validation.constraints.NotNull;

public record ResponseErrorDto(
        @NotNull
        String detail,

        @NotNull
        String instance,

        @NotNull
        Integer status,

        @NotNull
        String title,

        String code
) {}
