package page.se3.datingapp.model.struct.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import page.se3.datingapp.model.DataScheme;
import page.se3.datingapp.model.enums.Gender;
import page.se3.datingapp.model.enums.Relationship;

@Schema(description = "Represents a complete user account entry")
public record User(
        @NotNull
        Long id,

        @NotNull
        @Size(max = DataScheme.LEN_255)
        String email,

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
        String description,

        @NotNull
        Gender targetGender,

        @NotNull
        Relationship targetRelationship,

        @NotNull
        @Size(max = DataScheme.LEN_2047)
        String quizAnswers,

        @NotNull
        Boolean isParticipating,

        @NotNull
        Integer readyIndex
) {}
