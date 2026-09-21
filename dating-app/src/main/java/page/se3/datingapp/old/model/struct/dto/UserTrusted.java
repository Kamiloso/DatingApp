package page.se3.datingapp.old.model.struct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import page.se3.datingapp.old.infrastructure.util.Final;
import page.se3.datingapp.old.model.enums.Gender;

@Schema(description = "Represents a trusted user entry")
public record UserTrusted(
        @NotNull
        Long id,

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
        Integer age,

        @NotNull
        @Size(max = Final.LEN_255)
        String fieldOfStudy,

        @NotNull
        @Size(max = Final.LEN_2047)
        String description
) {}
