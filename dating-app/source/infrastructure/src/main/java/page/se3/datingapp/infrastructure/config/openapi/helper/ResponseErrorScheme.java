package page.se3.datingapp.infrastructure.config.openapi.helper;

import jakarta.validation.constraints.NotNull;

public record ResponseErrorScheme(
        @NotNull String detail,
        @NotNull String instance,
        @NotNull Integer status,
        @NotNull String title,
        String code
) {}
