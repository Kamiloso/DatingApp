package page.se3.datingapp.old.infrastructure.struct;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Contains API version")
public record ApiVersionInfo(
        @NotNull
        String apiVersion
) {}
