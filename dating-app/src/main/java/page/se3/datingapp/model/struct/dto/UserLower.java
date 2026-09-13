package page.se3.datingapp.model.struct.dto;

import jakarta.validation.constraints.*;
import page.se3.datingapp.model.Scheme;
import page.se3.datingapp.model.enums.Gender;

public record UserLower(
        @NotNull
        Long id,

        @NotNull
        @Size(max = Scheme.LEN_255)
        String name,

        @NotNull
        Gender gender,

        @NotNull
        @PositiveOrZero
        Integer age,

        @NotNull
        @Size(max = Scheme.LEN_255)
        String fieldOfStudy,

        @NotNull
        @Size(max = Scheme.LEN_2047)
        String description
) {}
