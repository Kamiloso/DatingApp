package page.se3.datingapp.model.struct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import page.se3.datingapp.model.DataScheme;
import page.se3.datingapp.model.enums.Gender;

@Schema(description = "Represents a higher user entry")
public record UserHigher(
        @NotNull
        Long id,

        @NotNull
        @Size(max = DataScheme.LEN_255)
        String name,

        @NotNull
        @Size(max = DataScheme.LEN_255)
        String surname,

        @NotNull
        Gender gender,

        @NotNull
        @PositiveOrZero
        Integer age,

        @NotNull
        @Size(max = DataScheme.LEN_255)
        String fieldOfStudy,

        @NotNull
        @Size(max = DataScheme.LEN_2047)
        String description
) {}
