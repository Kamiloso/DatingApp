package page.se3.datingapp.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.With;

@With
public record Chat(
        @NotNull
        Long id,

        @NotNull
        Long idUserFirst,

        @NotNull
        Long idUserSecond,

        @NotNull
        Boolean isUpgraded
) {}
