package page.se3.datingapp.old.model.struct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import page.se3.datingapp.old.infrastructure.util.Final;
import page.se3.datingapp.old.model.enums.Gender;

@Schema(description = "Represents a brief user entry")
public record UserBrief(
        @NotNull
        Long id,

        @NotNull
        @Size(max = Final.LEN_255)
        String name,

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
